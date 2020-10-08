package org.layz.hx.poi.pojo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.layz.hx.base.exception.HxRuntimeException;
import org.layz.hx.base.util.Assert;
import org.layz.hx.base.util.StringUtil;
import org.layz.hx.core.enums.PoiResponseEnum;
import org.layz.hx.core.pojo.info.PoiColumnInfo;
import org.layz.hx.core.pojo.info.PoiFileInfo;
import org.layz.hx.core.support.HxPoiSupport;
import org.layz.hx.core.util.DateUtil;
import org.layz.hx.core.util.factory.PoiFormaterFactory;
import org.layz.hx.poi.enums.IoState;
import org.layz.hx.poi.factory.ContentCellStyleFactory;
import org.layz.hx.poi.factory.TitleCellStyleFactory;
import org.layz.hx.poi.style.content.ContentCellStyle;
import org.layz.hx.poi.style.title.TitleCellStyle;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Excel2007生成工具（支持大数据）
 * @author 847792
 */
public class Excel2007Writer implements Closeable {
	/**当前Workbook*/
	private SXSSFWorkbook workbook;
	/**当前Sheet*/
	private Sheet sheet;
	/**当前Sheet的使用到的行号*/
	private int rowIndex;
	/**当前行对象*/
	private Row currRow;
	/**当前列*/
	private int currColumn;
	/**输出流*/
	private OutputStream os;
	/**当前状态*/
	private IoState state;
	/**Sheet容纳的最多行数*/
	private static final int LIMIT_SHEET_ROWS=50000;
	/**对象信息*/
	private PoiFileInfo poiFileInfo;
	/**表头样式*/
	private TitleCellStyle titleCellStyle;
	/**内容样式*/
	private Map<Integer, ContentCellStyle> cellStyleMap = new HashMap<>();
	/**
	 * 构造函数
	 * @param path 结果文件名（全路径）
	 * @param clazz
	 * @throws IOException
	 */
	public Excel2007Writer(Class clazz,String path) throws IOException{
		this.poiFileInfo = HxPoiSupport.getPoiFileInfo(clazz);
		Assert.isNotNull(poiFileInfo,PoiResponseEnum.POI_INFO_ISNULL);
		File resultFile=new File(path);
		if(!resultFile.exists()){
			File dir=resultFile.getParentFile();
			if(!dir.exists()){
				dir.mkdirs();
			}
			resultFile.createNewFile();
		}
		this.os=new FileOutputStream(resultFile);
		setState(IoState.CHECKED);
		this.beginWrite();
	}

	/**
	 * 构造函数
	 * @param outputStream 输出流
	 * @param clazz
	 * @throws IOException
	 */
	public Excel2007Writer(Class clazz, OutputStream outputStream) throws IOException{
		PoiFileInfo poiFilenfo = HxPoiSupport.getPoiFileInfo(clazz);
		Assert.isNotNull(poiFilenfo,PoiResponseEnum.POI_INFO_ISNULL);
		this.os = outputStream;
		this.poiFileInfo = poiFilenfo;
		setState(IoState.CHECKED);
		this.beginWrite();
	}

	/**
	 * 开始
	 * @throws IOException
	 */
	public void beginWrite() throws IOException{
		checkState(IoState.CHECKED);
		this.workbook = new SXSSFWorkbook(500);
		if(StringUtil.isNotBlank(poiFileInfo.getSheetName())){
			this.sheet = workbook.createSheet(poiFileInfo.getSheetName());
		}else{
			this.sheet = workbook.createSheet();
		}
		initColumnWidth();
		int cellStyle = poiFileInfo.getPoiFile().cellStyle();
		this.titleCellStyle = TitleCellStyleFactory.getTitleCellStyle(cellStyle);
		this.titleCellStyle.setWookBook(this.workbook);
		setState(IoState.BEGAN);
		this.createTable();
	}

	/***
	 * <pre>
	 * 创建列一个表数据
	 * getParam(tableCode.count)获取表格总记录数;
	 * getParam(tableCode.字段)获取该字段的总和
	 * </pre>
	 * @throws IOException
	 */
	public void createTable() throws IOException{
		checkState(IoState.BEGAN);
		Assert.isNotNull(poiFileInfo,PoiResponseEnum.POI_INFO_ISNULL);
		this.writeHeader(true);
		setState(IoState.READING);
	}
	/***
	 * <pre>
	 * 向表格追加数据
	 * </pre>
	 * @return
	 */
	public void appendTable(List<? extends Object> list) {
		checkState(IoState.READING);
		if(null == list || list.isEmpty()) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			Object rowData = list.get(i);
			appendRow(rowData);
		}
	}
	/***
	 * <pre>
	 * 向表格追加数据
	 * </pre>
	 * @return
	 */
	public void appendRow(Object rowData) {
		checkState(IoState.READING);
		if(null == rowData) {
			return;
		}
		List<PoiColumnInfo> infos = poiFileInfo.getColumnInfos();
		this.newRow();
		for (int j = 0; j < infos.size(); j++) {
			PoiColumnInfo columnInfo = infos.get(j);
			int cellType = columnInfo.getPoiColumn().cellType();
			int format = columnInfo.getPoiColumn().format();
			Object value = PoiFormaterFactory.getFormater(format).format(rowData, columnInfo);
			CellStyle cellStyle = getContentStyle(cellType).getCellStyle(this.rowIndex, rowData, columnInfo);
			this.appendCell(columnInfo.getCols(),true,value,cellStyle);
		}
	}
	/**
	 * 关闭资源
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		setState(IoState.ENDED);
		this.poiFileInfo = null;
		this.cellStyleMap = null;
		this.titleCellStyle = null;
		if(workbook!=null&&os!=null){
			workbook.write(os);
			os.flush();
			os.close();
		}
	}

	/**
	 * 切换新的类
	 * @param clazz
	 */
	public void setClass(Class clazz,Boolean writeHeader,Boolean withTitle){
		this.poiFileInfo = HxPoiSupport.getPoiFileInfo(clazz);
		if(writeHeader) {
			writeHeader(withTitle);
		}
	}

	/**
	 * 写标题
	 */
	private void writeHeader(boolean withTitle){
		if(withTitle) {
			String titleName = poiFileInfo.getTitleName();
			if(StringUtil.isNotBlank(titleName)) {
				this.newRow().appendCell(poiFileInfo.getTotalCol(),true,titleName,titleCellStyle.getTitleStyle());
			}
		}
		List<PoiColumnInfo> infos = poiFileInfo.getColumnInfos();
		if(null != infos && !infos.isEmpty()) {
			this.newRow();
			for (int i = 0; i < infos.size(); i++) {
				PoiColumnInfo info = infos.get(i);
				this.appendCell(info.getCols(),true,info.getTitle(),titleCellStyle.getHeadleStyle(info));
			}
		}
	}
	/**获取当前Sheet的使用到的行号*/
	public int getRowIndex() {
		return rowIndex;
	}
	/***
	 * 设置当前状态
	 * @param state
	 */
	private void setState(IoState state) {
		this.state = state;
	}
	/***
	 * 检查当前状态是否为指定状态
	 * @param state
	 */
	private void checkState(IoState state)throws HxRuntimeException {
		Assert.isTrue(this.state == state,PoiResponseEnum.WRITE_STATE_NOT_MATCH);
	}
	/**
	 * 初始化sheet的列宽
	 */
	private void initColumnWidth(){
		Assert.isNotNull(poiFileInfo, PoiResponseEnum.POI_INFO_ISNULL);
		List<PoiColumnInfo> infos = poiFileInfo.getColumnInfos();
		int col = 0;
		for (int i = 0; i < infos.size(); i++) {
			PoiColumnInfo columnInfo = infos.get(i);
			for (int j = 0; j < columnInfo.getCols(); j++) {
				this.sheet.setColumnWidth(col, columnInfo.getWidth());
				col++;
			}
		}
	}

	/**
	 * @param cellType
	 * @return
	 */
	private ContentCellStyle getContentStyle(int cellType) {
		ContentCellStyle contentCellStyle = cellStyleMap.get(cellType);
		if(null != contentCellStyle) {
			return contentCellStyle;
		}
		contentCellStyle = ContentCellStyleFactory.getContentCellStyle(cellType);
		contentCellStyle.setWorkBook(this.workbook);
		cellStyleMap.put(cellType,contentCellStyle);
		return contentCellStyle;
	}

	/**
	 * @return
	 */
	private Excel2007Writer newRow(){
		// checkState(IoState.READING);
		currColumn = 0;
		if(rowIndex > LIMIT_SHEET_ROWS + 1){
			int sheetIndex = workbook.getSheetIndex(this.sheet);
			this.sheet = workbook.createSheet(this.poiFileInfo.getSheetName()+"(续"+(sheetIndex+1)+")");
			rowIndex=0;
			writeHeader(true);
			initColumnWidth();
			currColumn = 0;
		}
		currRow=sheet.createRow(rowIndex++);
		return this;
	}
	/**
	 * 添加单元格，指定内容,样式
	 * @param cols 添加几列
	 * @param merge 是否合并单元格
	 * @param style 单元格样式
	 * @param content 单元格内容
	 */
	private void appendCell(int cols,boolean merge,Object content,CellStyle style){
		if(cols>1){
			if(merge){
				CellRangeAddress cra=new CellRangeAddress(rowIndex-1, rowIndex-1, currColumn,currColumn+cols-1);
				sheet.addMergedRegion(cra);
			}
			for(int i=0;i<cols;i++){
				if(merge){
					if(i==0){
						//合并的第一列写样式和内容
						appendCell(content,style);
					}else{
						//合并的其他列只写样式
						Cell cell=currRow.createCell(currColumn++);
						cell.setCellStyle(style);
					}
				}else{
					//如果不合并，这连续写几列（样式+内容）
					appendCell(content,style);
				}
			}
		}else{
			//如果cols<=1则认为是1
			appendCell(content,style);
		}
	}
	/**
	 * 添加单元格，指定内容
	 * @param content 内容
	 * @param style 指定样式
	 */
	private void appendCell(Object content,CellStyle style){
		Cell cell=currRow.createCell(currColumn++);
		if(style != null){
			cell.setCellStyle(style);
		}
		if(content!=null){
			if(Date.class.isInstance(content)){
				String temp= DateUtil.format(content, DateUtil.TIMESTAMP);
				cell.setCellValue(temp);
			}else if(Double.class.isInstance(content)){
				Double temp=(Double)content;
				cell.setCellValue(temp.doubleValue());
			}else if(Long.class.isInstance(content)){
				Long temp=(Long)content;
				cell.setCellValue(temp.longValue());
			}else if(Integer.class.isInstance(content)){
				Integer temp=(Integer)content;
				cell.setCellValue(temp.intValue());
			}else{
				cell.setCellValue(content.toString());
			}
		}
	}
}
