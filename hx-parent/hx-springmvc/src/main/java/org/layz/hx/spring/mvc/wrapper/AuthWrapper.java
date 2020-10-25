package org.layz.hx.spring.mvc.wrapper;

import java.util.List;

public interface AuthWrapper {
    /**
     * 权限url
     * @return
     */
    default List<String> authUrl() {
        return null;
    }

    /**
     * 权限代码
     * @return
     */
    default List<String> authCode() {
        return null;
    }
}
