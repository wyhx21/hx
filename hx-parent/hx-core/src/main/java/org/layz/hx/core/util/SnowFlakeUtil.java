package org.layz.hx.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SnowFlakeUtil {
    private static SnowFlakeUtil snowFlake;
    private static final Logger LOOGER = LoggerFactory.getLogger(SnowFlakeUtil.class);

    private final long workerId;
    private final long epoch = 1590325575378L; // 时间起始标记点，作为基准，一般取系统的最近时间
    private final long workerIdBits = 10L; // 机器标识位数
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;// 机器ID最大值:
    // 1023
    private long sequence = 0L; // 0，并发控制
    private final long sequenceBits = 12L; // 毫秒内自增位

    private final long workerIdShift = this.sequenceBits; // 12
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;// 22
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits; // 4095,111111111111,12位
    private long lastTimestamp = -1L;

    private SnowFlakeUtil() {
        long workerId = 156;
        try {

            InetAddress ia = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(ia);

            byte[] mac = ni.getHardwareAddress();
            String s = Integer.toHexString(mac[mac.length - 1] & 0xFF);
            int macWorker = Integer.parseInt(s, 16);
            if (macWorker < 1030) {
                workerId = macWorker;
            }
        } catch (SocketException e) {
            LOOGER.error("soket exception : ", e);
        } catch (UnknownHostException e) {
            LOOGER.error("UnknownHostException : ", e);
        }

        if (workerId > this.maxWorkerId || workerId < 0) {
            LOOGER.error("!!!!!!!!!!!workerId : {} --maxWorkerId:{} !!!!!!!!! ", workerId, this.maxWorkerId);
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    public static SnowFlakeUtil getSnowFlake(){
        if (snowFlake == null) {
            snowFlake = new SnowFlakeUtil();
        }
        return snowFlake;
    }

    public synchronized long nextId() {
        long timestamp = SnowFlakeUtil.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("!!!!!!!!!!Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环);
        if (this.lastTimestamp == timestamp) {
            // 对新的timestamp，sequence从0开始
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
            }
        } else {
            this.sequence = 0;
        }

        this.lastTimestamp = timestamp;
        return timestamp - this.epoch << this.timestampLeftShift | this.workerId << this.workerIdShift | this.sequence;
    }

    /**
     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = SnowFlakeUtil.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = SnowFlakeUtil.timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
