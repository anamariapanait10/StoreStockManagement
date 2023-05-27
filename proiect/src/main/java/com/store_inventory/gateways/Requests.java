package com.store_inventory.gateways;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Requests {
    private static HttpClient httpClient = HttpClient.newHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();

//    private static EssentialProductMapper essentialProductMapper = new EssentialProductMapper();
//
//    public void saveRequestInfo() {
//        try {
//            HttpRequest httpRequest = HttpRequest.newBuilder()
//                    .uri(new URI("https://serpapi.com/search?engine=google_product&product_id={product_id}"))
//                    .GET()
//                    .build();
//
//            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//            List<EssentialProductMapper> essentialProducts = essentialProductMapper.essentialProductListMapper(objectMapper, httpResponse.body());
//            writeBooksToCSV(essentialProducts);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
