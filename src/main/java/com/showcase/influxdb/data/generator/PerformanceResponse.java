package com.showcase.influxdb.data.generator;

public class PerformanceResponse {
    long count;
    int tps;

    public PerformanceResponse(long count, int tps) {
        this.count = count;
        this.tps = tps;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getTps() {
        return tps;
    }

    public void setTps(int tps) {
        this.tps = tps;
    }
}
