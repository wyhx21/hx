package org.layz.hx.core.inte;

import org.layz.hx.base.exception.HxRuntimeException;

public interface ReadDealer extends AutoCloseable{
    /**
     * 读操作的状态
     */
    enum ReadState {
        /** 初始化完毕 */
        INITED(1),
        /** 已经开始 */
        BEGAN(2),
        /** 已经检测 */
        CHECKED(3),
        /** 读取操作中 */
        READING(4),
        /** 已结束 */
        ENDED(5);
        private final int value;

        private ReadState(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    /**
     * 开始读的时候调用的方法，用于做一些准备工作，<br/>
     * 比如，打开数据流，初始化数据，或者跳过几行等
     * @throws HxRuntimeException
     */
    void beginRead() throws HxRuntimeException;

    /**
     * 开始读之后，正式读之前，可能检测一下是否符合要求，比如读取的数据总条/列数超出范围
     * 如果不符合，则请抛出AbortException异常
     * @param render
     * @throws HxRuntimeException
     */
    void check(ReadRender render) throws HxRuntimeException;

    /**
     * 是否有下一行数据，是的话，会继续读取，否则则读取结束
     * @return boolean
     * @throws HxRuntimeException
     */
    boolean hasNextRow() throws HxRuntimeException;

    /**
     * 读取下一行数据，返回为List对象
     * @throws HxRuntimeException
     */
    Object[] readNextRow() throws HxRuntimeException;
    /**
     * 获取当前行
     * @return
     */
    int getRowIndex();
}
