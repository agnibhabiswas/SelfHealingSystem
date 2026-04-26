package com.example.demo;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Perform health check logic here
        boolean isHealthy = checkApplicationHealth();
        if (isHealthy) {
            return Health.up().withDetail("status", "Healthy").build();
        }
        return Health.down().withDetail("status", "Unhealthy").build();
    }

    private boolean checkApplicationHealth() {
        // Implement your health check logic
        // Example: Check if a service is reachable or a database connection is alive
        return true; // Replace with actual health check logic
    }
}
