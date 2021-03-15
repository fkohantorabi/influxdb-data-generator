package com.showcase.influxdb.data.generator;

import org.influxdb.dto.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class PerformanceController {

    private final Random random = new Random();
    private final InfluxOperations influxOperations;


    public PerformanceController(InfluxOperations influxOperations) {
        this.influxOperations = influxOperations;
    }

    @PostMapping("/performance/measure")
    public ResponseEntity<PerformanceResponse> measure(@RequestBody PerformanceRequest request) {
        StopWatch stopWatch = new StopWatch();
        long count = 0;

        try (BatchOperation batchOperation = influxOperations.buildBatch("mydb", request.getBatchSize())) {
            long stopTime = System.currentTimeMillis() + toMillis(request.getDuration());
            stopWatch.start();

            while (System.currentTimeMillis() <= stopTime) {
                long responseTime = random.nextInt(5000);

                Point point = Point.measurement("participant.response.time")
                        .tag("participant", "TD")
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField("rt", responseTime)
                        .build();

                batchOperation.write(point);
                count++;
            }
        }

        stopWatch.stop();
        int tps = (int) (count / stopWatch.getTotalTimeSeconds());

        return ResponseEntity.ok(new PerformanceResponse(count, tps));
    }

    private long toMillis(int duration) {
        return duration * 60_000L;
    }

}
