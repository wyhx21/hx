package org.layz.hx.core.enums;

import org.layz.hx.base.inte.ResponseEnum;

public enum  HxResponseEnum implements ResponseEnum {
    SUCCESS_RESP("000000","success",SUCC),
    UNKNOW_ERROR("100000","system unknow error",FAIL),
    RPC_ERROR("100001","rpc connect error",FAIL);
    private String code;
    private String msg;
    private Integer success;
    HxResponseEnum(String code, String msg, Integer success){
        this.code = code;
        this.msg = msg;
        this.success = success;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public Integer getSuccess(){
        return success;
    }
}
