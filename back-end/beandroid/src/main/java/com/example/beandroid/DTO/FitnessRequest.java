package com.example.beandroid.DTO;

import lombok.Data;

@Data
public class FitnessRequest {
    private String accessToken;
    private long startTime;
    private long endTime;
}

