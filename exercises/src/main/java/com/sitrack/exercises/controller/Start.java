package com.sitrack.exercises.controller;

import com.sitrack.exercises.service.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class Start {
    private final Validation validation;

    @GetMapping
    public void start(){
        validation.send();
    }
}
