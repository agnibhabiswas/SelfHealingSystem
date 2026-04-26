import logging
import docker
from flask import Flask, request, jsonify

logging.basicConfig(level=logging.INFO, format="%(asctime)s %(levelname)s %(message)s")
log = logging.getLogger(__name__)

app = Flask(__name__)
docker_client = docker.from_env()


@app.route("/health")
def health():
    return jsonify({"status": "up"})


@app.route("/alert", methods=["POST"])
def handle_alert():
    data = request.get_json(force=True)
    alerts = data.get("alerts", [])
    for alert in alerts:
        name = alert.get("labels", {}).get("alertname", "")
        status = alert.get("status", "")
        log.info("Received alert: %s [%s]", name, status)
        if status == "firing" and name == "AppDown":
            _restart_app()
    return jsonify({"status": "ok"})


def _restart_app():
    containers = docker_client.containers.list(
        filters={"label": "com.docker.compose.service=app"}
    )
    if not containers:
        log.warning("No container with service label 'app' found — skipping restart")
        return
    for c in containers:
        log.info("Restarting container %s (%s) ...", c.name, c.short_id)
        c.restart(timeout=10)
        log.info("Container %s restarted successfully", c.name)


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
