package com.sitrack.exercises.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitrack.exercises.entity.Report;
import com.sitrack.exercises.service.SendReportOfPosition;
import com.sitrack.exercises.service.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationImpl implements Validation {

    private final SendReportOfPosition sendReportOfPosition;

    @Override
    public void send() {
        LocalDateTime off = LocalDateTime.now().plusMinutes(10);

        while (LocalDateTime.now().isBefore(off)) {

            String report = createReport();
            sendFrame(report);
        }
    }

    private String createReport() {
        Report report = new Report();
        report.setLoginCode("98173");
        report.setReportDate(String.valueOf(Instant.now().truncatedTo(ChronoUnit.SECONDS)));
        report.setReportType("2");
        report.setLatitude((Math.round(Math.random() * 1000.0) / 1000.0) + -32.935482);
        report.setLongitude((Math.round(Math.random() * 1000.0) / 1000.0) + 20.815751);
        report.setGpsDop((double) Math.round(Math.random() * 20));
        report.setHealding(Math.toIntExact((long) (Math.random() * 360)));
        report.setSpeed(Math.floor(Math.random() * 10000) / 100);
        report.setSpeedLabel("GPS");
        report.setGpsSatellites(Math.toIntExact((long) (Math.random() * 10)));
        report.setText("Nicolas Zarandon");
        report.setTextLabel("TAG");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;

        {
            try {
                jsonString = mapper.writeValueAsString(report);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonString;
    }

    private void sendFrame(String report) {

        ResponseEntity<String> result = sendReport(report);
        HttpStatusCode code = result.getStatusCode();
        if (code.equals(HttpStatusCode.valueOf(200))) {
            waitForSecond(60);
            log.info("Send: {}", code);
            return;
        } else if (code.equals(HttpStatusCode.valueOf(429)) || code.value() >= 500) {
            boolean exit = Boolean.TRUE;
            do {
                log.info("Error: {}", code);
                waitForSecond(10);
                ResponseEntity<String> resultError = sendReport(report);// envio el mismo reporte
                if (resultError.equals(HttpStatusCode.valueOf(200))) {
                    log.info("Send: {}", code);
                    exit = Boolean.FALSE;
                }

            } while (exit);

        }
        return;
    }

    private ResponseEntity<String> sendReport(String report) {

        return sendReportOfPosition.sendToClient(report);
    }

    private void waitForSecond(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}