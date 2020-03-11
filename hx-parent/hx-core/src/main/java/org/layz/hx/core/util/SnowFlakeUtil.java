package org.layz.hx.core.util;

import org.layz.hx.core.pojo.common.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class SnowFlakeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SnowFlakeUtil.class);
    private static SnowFlake snowFlake;

    public static SnowFlake getSnowFlake(){
        if(null != snowFlake) {
            return snowFlake;
        }
        init();
        return snowFlake;
    }

    private static synchronized void init() {
        if(null != snowFlake) {
            return;
        }
        int machineId = 1;
        try {
            InetAddress ia = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ia);
            byte[] mac = networkInterface.getHardwareAddress();
            String str = Integer.toHexString(mac[mac.length -1] & 255);
            int parseInt = Integer.parseInt(str, 16);
            if(parseInt <1030) {
                machineId = parseInt;
            }
        } catch (Throwable e) {
            LOGGER.error("", e);
        }
        snowFlake = new SnowFlake(machineId);
    }
}
