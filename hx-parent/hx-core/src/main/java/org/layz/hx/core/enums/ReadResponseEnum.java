package org.layz.hx.core.enums;

import org.layz.hx.base.inte.ResponseEnum;

public enum ReadResponseEnum implements ResponseEnum {
    READ_RENDER_ISNULL("200001","read render can not be null",FAIL),
    READ_DEALER_ISNULL("200002","read dealer can not be null",FAIL),
    STATE_NOT_MATCH("200003","read state not match",FAIL),
    READ_STATE_ILLEGAL("200004","The state of the read dealer is illegal",FAIL);
    private String code;
    private String msg;
    private Integer success;
    ReadResponseEnum(String code, String msg, Integer success){
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