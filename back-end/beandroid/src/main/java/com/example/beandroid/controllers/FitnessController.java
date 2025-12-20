package com.example.beandroid.controllers;

import com.example.beandroid.DTO.FitnessRequest;
import com.example.beandroid.services.FitnessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fitness")
public class FitnessController {

    private final FitnessService fitnessService;

    public FitnessController(FitnessService fitnessService) {
        this.fitnessService = fitnessService;
    }

    @PostMapping("/steps")
    public ResponseEntity<?> getSteps(@RequestBody FitnessRequest request) {
        try {
            String result = fitnessService.getStepCount(
                    request.getAccessToken(),
                    request.getStartTime(),
                    request.getEndTime()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching Google Fit data: " + e.getMessage());
        }
    }

}
