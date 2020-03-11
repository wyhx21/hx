package org.layz.hx.base.inte;

public interface ResponseEnum {
    Integer SUCC = 1;
    Integer FAIL = 0;
    /**
     * 获取错误码
     * @return
     */
    String getCode();

    /**
     * 后去错误信息
     * @return
     */
    String getMsg();

    /**
     * 成功 1 失败 0
     * @return
     */
    Integer getSuccess();
}
