package org.layz.hx.base.type;

import org.layz.hx.base.inte.TypeEnum;

public enum JobStatusEnum implements TypeEnum {
    WAITE_START(0,"待启动任务"),
    WAITE_HANDLE(1,"待处理"),
    HANDING(2,"处理中"),
    HANDLE_FAIL(3,"处理失败"),
    HANDLE_SUCCESS(4,"处理成功");

    private Integer value;
    private String name;

    JobStatusEnum(Integer value,String name) {
        this.value = value;
        this.name = name;
    }
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equalValue(Integer value) {
        return this.value.equals(value);
    }
}
