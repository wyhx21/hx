package org.layz.hx.core.util;

import java.net.InetAddress;

public class Constant {
    private Constant() {
    }
    static {
        try {
            SETVER_IP = InetAddress.getLocalHost().getHostAddress();
        } catch (Throwable e) {
            SETVER_IP = "";
        }
    }
    /**IP**/
    public static String SETVER_IP;
}
