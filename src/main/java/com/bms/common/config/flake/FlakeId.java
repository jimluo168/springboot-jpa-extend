package com.bms.common.config.flake;

/**
 * ID生产工具类.
 *
 * @author luojimeng
 * @date 2020/3/09
 */
public class FlakeId {
    /**
     * 序列号占用的位数.
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数.
     */
    private final static long MACHINE_BIT = 5;
    /**
     * 数据中心占用的位数.
     */
    private final static long DATACENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = 1 << DATACENTER_BIT;
    private final static long MAX_MACHINE_NUM = 1 << MACHINE_BIT;
    private final static long MAX_SEQUENCE = 1 << SEQUENCE_BIT;

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;


    /**
     * 起始的时间戳.
     */
    private long epoch = 1480550400000L;
    /**
     * 数据中心.
     */
    private long dataCenterId = 1L;
    /**
     * 机器标识.
     */
    private long machineId;
    /**
     * 序列号.
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳.
     */
    private long lastTimestamp = -1L;

    public FlakeId(long epoch, long dataCenterId, long machineId) {
        if (epoch < 0 || epoch > (long) Integer.MAX_VALUE * 1000) {
            throw new IllegalArgumentException("epoch can't be greater than " + (long) Integer.MAX_VALUE * 1000 + " or less than 0");
        }
        if (dataCenterId > MAX_DATACENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than " + MAX_DATACENTER_NUM + " or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than " + MAX_MACHINE_NUM + " or less than 0");
        }
        this.epoch = epoch;
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID.
     *
     * @return long
     */
    public synchronized long next() {
        long currStmp = timeGen();
        if (currStmp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastTimestamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMillis();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = currStmp;

        // 时间戳部分 + 数据中心部分 + 机器标识部分 + 序列号部分
        return (currStmp - epoch) << TIMESTMP_LEFT
                | dataCenterId << DATACENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMillis() {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
