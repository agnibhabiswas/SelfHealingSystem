package com.example.demo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class GrafanaApiAutomation {

    private static final String GRAFANA_URL = "http://localhost:3000";
    private static final String USERNAME = "admin"; // Your Grafana username
    private static final String PASSWORD = "admin"; // Your Grafana password

    public static void main(String[] args) {
        String token = login();
        if (token != null) {
            getLiveWs(token);
        }
    }

    private static String login() {
        String url = GRAFANA_URL + "/api/auth/login";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("user", USERNAME);
            json.put("password", PASSWORD);
            post.setEntity(new StringEntity(json.toString()));

            try (CloseableHttpResponse response = client.execute(post)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JSONObject responseJson = new JSONObject(jsonResponse);

                if (response.getStatusLine().getStatusCode() == 200) {
                    String token = responseJson.getJSONObject("session").getString("token");
                    System.out.println("Login successful! Token obtained: " + token);
                    return token;
                } else {
                    System.out.println("Failed to obtain token: " + jsonResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void getLiveWs(String token) {
        String url = GRAFANA_URL + "/api/live/ws";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + token);

            try (CloseableHttpResponse response = client.execute(get)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                if (response.getStatusLine().getStatusCode() == 200) {
                    System.out.println("Live WS data: " + jsonResponse);
                } else {
                    System.out.println("Failed to access live WS: " + jsonResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
