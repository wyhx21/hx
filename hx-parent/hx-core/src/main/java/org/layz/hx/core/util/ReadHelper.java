package org.layz.hx.core.util;

import org.layz.hx.base.util.Assert;
import org.layz.hx.core.enums.ReadResponseEnum;
import org.layz.hx.core.inte.ReadDealer;
import org.layz.hx.core.inte.ReadRender;

/**
 * 读数据（文件）的帮助类，主要包含一些固有的循环逻辑，<br/>
 * 不能异步执行，但根据 ReadRender的实现情况，可能  <br/>
 * 可以多次执行 processRead的动作，从而可以读取多个数据（文件）<br/>
 */
public class ReadHelper {
    /** 读出的数据处理类，读的时候会把读出的数据作为参数传入其方法中 */
	private ReadRender render;
	/**
	 * 构造方法
	 * @param render 不能为空
	 */
	public ReadHelper(ReadRender render){
        Assert.isNotNull(render, ReadResponseEnum.READ_RENDER_ISNULL);
	    this.render = render;
	}

	/**
     * 实施读数据（文件）逻辑
     * @param readDealer 读数据的数据处理类，不能为空
	 * @throws Exception
	 */
        public void processRead(ReadDealer readDealer) throws Exception {
	    Assert.isNotNull(readDealer,ReadResponseEnum.READ_DEALER_ISNULL);
        synchronized (render) {
            readDealer.beginRead();
            try {
                readDealer.check(render);
                render.processBefore();
                try {
                    while (readDealer.hasNextRow()) {
                        try {
                            render.onRowRead(readDealer.readNextRow(), readDealer.getRowIndex());
                        } catch (Exception e) {
                            render.exceptionHandler(e, readDealer.getRowIndex());
                        }
                    }
                    render.processAfter();
                } finally {
                    try {
                        render.close();
                    } catch (Exception e) {
                    }
                }
            } finally {
                try {
                    readDealer.close();
                } catch (Exception e) {
                }
            }
        }
	}
}