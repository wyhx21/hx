package org.layz.hx.base.inte;

public interface Complete {
    /**
     * 支持的类型
     * @param obj
     * @return
     */
    boolean support(Object obj);

    /**
     * 执行操作
     * @param obj
     */
    void complete(Object obj);
}
