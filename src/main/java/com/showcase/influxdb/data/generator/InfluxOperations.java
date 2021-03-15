package com.showcase.influxdb.data.generator;

import org.influxdb.InfluxDB;
import org.springframework.stereotype.Component;

@Component
public class InfluxOperations {

    private final InfluxDB influxDB;


    public InfluxOperations(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }

    public BatchOperation buildBatch(String db, int batchSize) {
        return new BatchOperation(influxDB, db, batchSize);
    }
}
