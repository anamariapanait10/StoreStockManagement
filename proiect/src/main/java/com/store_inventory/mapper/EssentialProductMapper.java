package com.store_inventory.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store_inventory.model.EssentialProduct;

import java.util.ArrayList;
import java.util.List;

public class EssentialProductMapper {
    public List<EssentialProduct> essentialProductListMapper(ObjectMapper objectMapper, String body) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(body);
        List<EssentialProduct> essentialBooks = new ArrayList<>();

        JsonNode itemsNode = jsonNode.get("items");
        if (itemsNode != null && itemsNode.isArray()) {
            for (JsonNode itemNode : itemsNode) {
                EssentialProduct essentialBook = new EssentialProduct();

                JsonNode volumeInfoNode = itemNode.get("volumeInfo");
                if (volumeInfoNode != null) {
                    JsonNode titleNode = volumeInfoNode.get("title");
                    if (titleNode != null && titleNode.isTextual()) {
                        essentialBook.setTitle(titleNode.asText());
                    }

                    JsonNode authorsNode = volumeInfoNode.get("authors");
                    if (authorsNode != null && authorsNode.isArray()) {
                        List<String> authors = new ArrayList<>();
                        for (JsonNode authorNode : authorsNode) {
                            if (authorNode.isTextual()) {
                                authors.add(authorNode.asText());
                            }
                        }
                        essentialBook.setAuthors(authors);
                    }

                    JsonNode publisherNode = volumeInfoNode.get("publisher");
                    if (publisherNode != null && publisherNode.isTextual()) {
                        essentialBook.setPublisher(publisherNode.asText());
                    }

                    JsonNode publishedDateNode = volumeInfoNode.get("publishedDate");
                    if (publishedDateNode != null && publishedDateNode.isTextual()) {
                        essentialBook.setPublishedDate(publishedDateNode.asText());
                    }

                    essentialBooks.add(essentialBook);
                }
            }
        }

        return essentialBooks;
    }
}
