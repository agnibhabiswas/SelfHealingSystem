groups:
        - name: example
rules:
        - alert: AppDown
expr: up == 0
        for: 1m
labels:
severity: critical
annotations:
summary: "App is down"
