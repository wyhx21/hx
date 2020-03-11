package org.layz.hx.poi.pojo;

import org.apache.poi.ss.usermodel.*;
import org.layz.hx.base.exception.HxRuntimeException;
import org.layz.hx.base.util.Assert;
import org.layz.hx.core.enums.ReadResponseEnum;
import org.layz.hx.core.inte.ReadDealer;
import org.layz.hx.core.inte.ReadRender;

import java.io.*;

/**
 * 默认的Excel格式的读数据（文件）的处理实现类 <br/>
 * 使用poi包的HSSFWorkbook直接读取Excel，如果有跨行跨列的，<br/>
 * 读取出来的都会是最小行最小列的值，其它行都会为空<br/>
 * poi包的HSSFWorkbook是一次性读取完毕，然后取数据的模式
 */
public class DefaultExcelReadDealer implements ReadDealer {

    private InputStream inputStream;
    private Workbook workbook;
    private Sheet sheet;
    private ReadState state;
    private int columnCountLimit = 0;
    private int rowLimit = Integer.MAX_VALUE;
    private int rowIndex;
    private int sheetIndex;

    /**
     * 构造函数，指定读取的文件路径，字符集
     * @param filePath 文件路径
     * @throws FileNotFoundException
     */
    public DefaultExcelReadDealer(String filePath) throws FileNotFoundException{
        this(filePath, 0);
    }
    
    /**
     * 构造函数，指定读取的文件路径，字符集
     * @param filePath 读取的文件路径
     * @param startRow 开始行
     */
	public DefaultExcelReadDealer(String filePath, int startRow) throws FileNotFoundException{
	    this(filePath,startRow,0);
	}

    /**
     * 构造函数，指定读取的文件路径，字符集
     * @param filePath 文件路径
     * @param startRow 开始行
     * @param sheetIndex 表单序号
     * @throws FileNotFoundException
     */
    public DefaultExcelReadDealer(String filePath, int startRow, int sheetIndex) throws FileNotFoundException{
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }
        inputStream = new FileInputStream(file);
        this.sheetIndex = sheetIndex;
        this.rowIndex = startRow;
        setState(ReadState.INITED);
    }

	public DefaultExcelReadDealer(InputStream stream){
	    this(stream, 0);
	}
	
    /**
     * 构造函数，指定输入流，字符集
     * @param stream 输入流
     * @param startRow 开始行
     */
	public DefaultExcelReadDealer(InputStream stream, int startRow){
        this(stream,startRow,0);
	}
	
	/**
	 * 构造函数，指定输入流，表单序号，开始行号
	 * @param stream
	 * @param startRow 开始行
     * @param sheetIndex 表单序号
	 */
	public DefaultExcelReadDealer(InputStream stream, int startRow, int sheetIndex){
        inputStream = stream;
        this.sheetIndex = sheetIndex;
	    this.rowIndex = startRow;
	    setState(ReadState.INITED);
	}
    /**
     * @throws HxRuntimeException
     */
    @Override
    public void beginRead() throws HxRuntimeException{
        checkState(ReadState.INITED);
        try {
            if(!inputStream.markSupported()) {
                inputStream = new PushbackInputStream(inputStream,8);
            }
            workbook = WorkbookFactory.create(inputStream);
            sheet = workbook.getSheetAt(sheetIndex);
            rowIndex += sheet.getFirstRowNum();
        } catch (IOException e) {
            throw new HxRuntimeException(e);
        }
        setState(ReadState.BEGAN);
    }

    /**
     * @throws HxRuntimeException
     */
    @Override
    public void check(ReadRender render) throws HxRuntimeException{
        checkState(ReadState.BEGAN);
        int renderRowCountLimit = render.getRowCountLimit() + rowIndex - 1;
        int sheetRowLimit = sheet.getLastRowNum();
        if(renderRowCountLimit < rowIndex) {
            rowLimit = sheetRowLimit;
        } else {
            rowLimit = Math.min(renderRowCountLimit,sheetRowLimit);
        }
        columnCountLimit = render.getColumnCountLimit();
        setState(ReadState.CHECKED);
    }

    /**
	 * @throws HxRuntimeException
	 */
    @Override
	public boolean hasNextRow() throws HxRuntimeException {
        if(state != ReadState.READING){
            checkState(ReadState.CHECKED);
            setState(ReadState.READING);
        }
        if(rowIndex > rowLimit) {
            return false;
        }
        Row row = sheet.getRow(rowIndex);
        if(null == row) {
            rowIndex++;
            return hasNextRow();
        }
        return true;
	}

    /**
     * @throws HxRuntimeException 
     */
    @Override
    public Object[] readNextRow() throws HxRuntimeException{
        if(state != ReadState.READING){
            checkState(ReadState.CHECKED);
            setState(ReadState.READING);
        }
        Row sheetRowTmp = sheet.getRow(rowIndex++);
        int colun = sheetRowTmp.getLastCellNum();
        if(columnCountLimit > 0) {
            colun = Math.min(colun,columnCountLimit);
        }
        Cell sheetCellTmp;
        Object cellValueTmp;
        Object[] rowData = new Object[colun];
        for (int j = 0; j < colun; j++) {
            sheetCellTmp = sheetRowTmp.getCell(j);
            if (sheetCellTmp == null) {
                continue;
            }
            switch (sheetCellTmp.getCellType()) {
                case STRING:
                    cellValueTmp = sheetCellTmp.getStringCellValue();
                    break;
                case BLANK:
                    cellValueTmp = sheetCellTmp.getStringCellValue();
                    break;
                case NUMERIC:
                    cellValueTmp = sheetCellTmp.getNumericCellValue();
                    break;
                case BOOLEAN:
                    cellValueTmp = sheetCellTmp.getBooleanCellValue();
                    break;
                default:
                    cellValueTmp = null;
            }
            rowData[j] = cellValueTmp;
        }
        return rowData;
    }

    @Override
    public void close() throws Exception {
        Assert.isTrue(state.value() < ReadState.BEGAN.value(),ReadResponseEnum.READ_STATE_ILLEGAL);
        sheet = null;
        workbook = null;
        try {
            inputStream.close();
        } catch (IOException e) {
        }
        setState(ReadState.ENDED);
    }

    @Override
    public int getRowIndex() {
        return rowIndex;
    }

    private void setState(ReadState state) {
        this.state = state;
    }

    private void checkState(ReadState stateShouldBe) {
        Assert.isTrue(state == stateShouldBe, ReadResponseEnum.STATE_NOT_MATCH);
    }
}