package org.layz.hx.core.inte;

/**
 * 读取数据的处理接口，也包含一些校验信息，读数据的时候会调用此对象的方法  <br/>
 * 是一个单线程的边读，边调用此对象处理数据的一个过程 <br/>
 */
public interface ReadRender {
	/**
	 * 限制有多少个字段，或者列，读取的时候可能会检测一下
	 * @return int 返回列数或者字段数，大于0表示有限制，否则无限制，无限制，则读取该行所有非空字段
	 */
	default int getColumnCountLimit(){
		return 0;
	}
	/**
	 * 限制有多少行，或者多少条记录，读取的时候可能会检测一下
	 * @return int 返回行数或者记录数，大于0表示有限制，否则无限制，
	 * 有限制的情况下，超出限制的处理不定，会随着ReadDealer的实现不同而不同
	 */
	default int getRowCountLimit() {
		return 0;
	}

	/**
	 * 当某一行的数据读取出来后的处理方法
	 * @param row 读取的行数据
	 * @param rowIndex 行序号
	 */
	void onRowRead(Object[] row, int rowIndex) throws Throwable;
    /**
     * 开始读文件之后，正式读之前会调用此方法，用于做一些准备工作，<br/>
     * 如：初始化对象，或者重置缓存数据之类的
     */
	default void processBefore() {

	}
    /**
     * 读文件过程完毕，正式结束之前会调用此方法，用于做一些总结、清理工作，<br/>
     * 如：统计读取了多少行，数据清除之类 <br/>
     * 也可能是读文件产生异常后的调用
     */
	default void processAfter() {
	}

	/**
	 * 异常处理方式
	 * @param e
	 * @throws Throwable
	 */
	default void exceptionHandler(Throwable e, int rowIndex) throws Throwable {
		throw e;
	}
}