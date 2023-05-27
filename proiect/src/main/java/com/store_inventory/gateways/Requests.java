package com.store_inventory.gateways;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store_inventory.application.csv.CsvWriter;
import com.store_inventory.mapper.EssentialProductMapper;
import com.store_inventory.model.EssentialProduct;
import com.store_inventory.service.LogServiceImpl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Requests {
    private static HttpClient httpClient = HttpClient.newHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static EssentialProductMapper essentialProductMapper = new EssentialProductMapper();

    public void saveRequestInfo() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://www.googleapis.com/books/v1/volumes?q=gardening+manuals&fields=items(volumeInfo/title,volumeInfo/authors,volumeInfo/publisher,volumeInfo/publishedDate)"))
                    .GET()
                    .build();

            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<EssentialProduct> essentialProducts = essentialProductMapper.essentialProductListMapper(objectMapper, httpResponse.body());
            writeBooksToCSV(essentialProducts);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeBooksToCSV(List<EssentialProduct> books) {
        List<String[]> stringList = new ArrayList<>();
        stringList.add(new String[] {"Title" , "Authors", "Publisher", "Published Date"});

        String title = "";
        String authors = "";
        String publisher = "";
        String publishedDate = "";

        for (EssentialProduct book : books) {
            if (book.getTitle() != null) {
                title = book.getTitle().replaceAll(",", ";");
            }

            if (book.getAuthors() != null) {
                authors = String.join(", ", book.getAuthors()).replaceAll(",", ";");
            }

            if (book.getPublisher() != null) {
                publisher = book.getPublisher().replaceAll(",", ";");
            }

            if (book.getPublishedDate() != null) {
                publishedDate = book.getPublishedDate().replaceAll(",", ";");
            }

            stringList.add(new String[] {title, authors, publisher, publishedDate});
        }

        try {
            CsvWriter.getInstance().writeLineByLine(stringList, Path.of("essential_products.csv"));
        } catch (Exception e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

}
