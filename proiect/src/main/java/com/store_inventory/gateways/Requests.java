package com.store_inventory.gateways;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public class Requests {
    private static HttpClient httpClient = HttpClient.newHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();

}
