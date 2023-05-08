package com.sitrack.exercises.service;

import org.springframework.http.ResponseEntity;

public interface SendReportOfPosition {
    ResponseEntity<String> sendToClient(String report);
}
