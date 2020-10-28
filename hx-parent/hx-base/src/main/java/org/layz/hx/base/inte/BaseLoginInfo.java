package org.layz.hx.base.inte;

import java.io.Serializable;

public interface BaseLoginInfo extends Serializable {
    /**
     * 获取登录信息的key
     * @return
     */
    String getKey();
}
