package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    public static class HealthController {
        @GetMapping("/health")
        public ResponseEntity<String> health() {
            return ResponseEntity.ok("Healthy");
        }

        public static void main(String[] args) {
            SpringApplication.run(DemoApplication.class, args);

            // Call the Grafana API automation
            GrafanaApiAutomation.main(args); // Call the main method of GrafanaApiAutomation
        }
    }
}

