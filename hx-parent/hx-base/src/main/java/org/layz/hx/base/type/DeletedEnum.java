package org.layz.hx.base.type;

import org.layz.hx.base.inte.TypeEnum;

public enum DeletedEnum implements TypeEnum {
    DISABLE(0,"无效"),
    ENABLE(1,"有效");

    private Integer value;
    private String name;

    DeletedEnum(Integer value, String name) {
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
