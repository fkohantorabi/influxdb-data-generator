package com.showcase.influxdb.data.generator;

import org.influxdb.dto.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class DataPointController {

    private final List<String> participants = List.of("TD", "CIBC", "Scotiabank", "RBC");
    private final Random random = new Random();
    private final InfluxOperations influxOperations;

    public DataPointController(InfluxOperations influxOperations) {
        this.influxOperations = influxOperations;
    }

    @PostMapping("/datapoint/random")
    public ResponseEntity<Void> generateRandomDataPoints(@RequestBody RandomRequest request) throws InterruptedException {
        try (BatchOperation batchOperation = influxOperations.buildBatch("mydb", 100)) {
            for (int i = 1; i <= request.count; i++) {
                String participant = participants.get(i % participants.size());
                long responseTime = random.nextInt(5000);

                Point point = Point.measurement("participant.response.time")
                        .tag("participant", participant)
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField("rt", responseTime)
                        .build();

                batchOperation.write(point);
                Thread.sleep(100);
            }
        }

        return ResponseEntity.ok().build();
    }

}
