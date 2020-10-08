package org.layz.hx.base.enums;

import java.util.List;

public enum Expression {
    eq("="),
    ne("!="),
    lt("<"),
    gt(">"),
    goe(">="),
    loe("<="),
    like("") {
        @Override
        public String sql(Object value) {
            return " like CONCAT('%' ,? ,'%') ";
        }
    },
    in(" in (") {
        @Override
        public String sql(Object value) {
            if(value instanceof List) {
                List list = (List) value;
                if(list.isEmpty()) {
                    return "";
                }
                StringBuilder str = new StringBuilder(super.val);
                for (int i = 0; i < list.size(); i++) {
                    if(i == 0) {
                        str.append(" ?");
                    }else {
                        str.append(", ?");
                    }
                }
                return str.append(") ").toString();
            }
            return "";
        }

        @Override
        public void args(List<Object> list, Object value) {
            if(value instanceof List) {
                List listo = (List) value;
                for (Object o : listo) {
                    list.add(o);
                }
            }
        }
    };

    private String val;
    Expression(String val) {
        this.val = val;
    }

    public String sql(Object value){
        return " " + this.val + " ? ";
    }

    public void args(List<Object> list, Object value){
        list.add(value);
    }
}
