package com.bms.common.config.flake;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("flake")
public class FlakeIdProperties {
    private long epoch = 1480550400000L;
    private int datacenter = 1;
    private int worker = 1;

    public FlakeIdProperties() {
    }

    public long getEpoch() {
        return epoch;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    public int getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(int datacenter) {
        this.datacenter = datacenter;
    }

    public int getWorker() {
        return worker;
    }

    public void setWorker(int worker) {
        this.worker = worker;
    }
}
