package org.layz.hx.core.inte;

import java.lang.reflect.Field;

public interface FieldFilter {
    /**
     * @param field
     * @return
     */
    boolean filter(Field field);
}
