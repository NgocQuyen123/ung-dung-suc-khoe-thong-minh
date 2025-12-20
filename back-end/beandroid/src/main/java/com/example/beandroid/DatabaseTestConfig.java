package com.example.beandroid;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseTestConfig {

    @Bean
    CommandLineRunner testDb(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("✅ KẾT NỐI DATABASE THÀNH CÔNG!");
                System.out.println("DB URL: " + conn.getMetaData().getURL());
            } catch (Exception e) {
                System.out.println("❌ KẾT NỐI DATABASE THẤT BẠI!");
                e.printStackTrace();
            }
        };
    }
}

