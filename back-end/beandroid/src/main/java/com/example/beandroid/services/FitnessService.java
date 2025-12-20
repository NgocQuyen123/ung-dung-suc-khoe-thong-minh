package com.example.beandroid.services;

import okhttp3.*;
import org.springframework.stereotype.Service;

@Service
public class FitnessService {

    private static final String GOOGLE_FIT_URL =
            "https://www.googleapis.com/fitness/v1/users/me/dataset:aggregate";

    public String getStepCount(String accessToken, long startTime, long endTime) {

        OkHttpClient client = new OkHttpClient();

        String jsonBody = """
        {
          "aggregateBy": [{
            "dataTypeName": "com.google.step_count.delta"
          }],
          "bucketByTime": { "durationMillis": 86400000 },
          "startTimeMillis": %d,
          "endTimeMillis": %d
        }
        """.formatted(startTime, endTime);

        Request request = new Request.Builder()
                .url(GOOGLE_FIT_URL)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(
                        jsonBody,
                        MediaType.get("application/json")
                ))
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("Google Fit API error", e);
        }
    }
}
