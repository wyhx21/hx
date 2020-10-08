package org.layz.hx.core.visitor;

public interface Visitor {
    /**
     * @param data
     * @return
     */
    Object accept(Object data);

    /**
     * @param data
     * @return
     */
    default Object begin(Object data) {
        return null;
    }
}
