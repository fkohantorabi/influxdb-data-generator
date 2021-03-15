package com.showcase.influxdb.data.generator;

import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

public class BatchOperation implements Closeable {

    private final InfluxDB influxDB;
    private final int batchSize;
    private final String db;
    private BatchPoints batchPoints;


    public BatchOperation(InfluxDB influxDB, String db, int batchSize) {
        this.influxDB = influxDB;
        this.db = db;
        this.batchSize = batchSize;
    }

    public void write(Point point) {
        BatchPoints points = batchPoints();
        points.point(point);

        if (points.getPoints().size() == batchSize) {
            flush();
        }
    }

    private void flush() {
        influxDB.write(batchPoints);
        batchPoints = null;
    }

    @Override
    public void close() {
        if (batchPoints != null) {
            flush();
        }
    }

    private BatchPoints batchPoints() {
        if (batchPoints == null) {
            batchPoints = BatchPoints.database(db).precision(TimeUnit.MILLISECONDS).build();
        }

        return batchPoints;
    }
}
