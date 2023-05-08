package com.sitrack.exercises.service.impl;

import com.sitrack.exercises.config.Authorization;
import com.sitrack.exercises.service.SendReportOfPosition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendReportOfPositionImpl implements SendReportOfPosition {
    private final RestTemplate restTemplate;
    @Value("${app.external.sitrack.url}")

    private String url;

    @Override
    public ResponseEntity<String> sendToClient(String report) {

        String auth = Authorization.getAuthorization();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        HttpEntity<Object> entity = new HttpEntity<Object>(report, headers);

        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        return result;

    }
}