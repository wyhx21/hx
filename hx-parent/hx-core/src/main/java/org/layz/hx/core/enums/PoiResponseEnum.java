package org.layz.hx.core.enums;

import org.layz.hx.base.inte.ResponseEnum;

public enum PoiResponseEnum implements ResponseEnum {
    POI_INFO_ISNULL("300001","please set poiFilenfo",FAIL),
    WRITE_STATE_NOT_MATCH("300002","the state not match",FAIL);
    private String code;
    private String msg;
    private Integer success;
    PoiResponseEnum(String code, String msg, Integer success){
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
