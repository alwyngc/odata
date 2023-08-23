package com.example.odata.olingo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/heartbeat")
public class HeartBeatController {

    @GetMapping
    public String healthCheck() {
        return "Service is up and running";
    }

}