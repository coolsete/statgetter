package com.hello.devtest.controllers;

import com.hello.devtest.services.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class StatisticsController {
    private final StatService statService;

    @PostMapping("/event")
    public ResponseEntity<Void> loadStatistics(@RequestBody String data) {
        statService.loadStats(data);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getStats() {
        return new ResponseEntity(statService.getStats(), HttpStatus.OK);
    }
}
