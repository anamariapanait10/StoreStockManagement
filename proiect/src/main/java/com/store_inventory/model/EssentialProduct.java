package com.store_inventory.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EssentialProduct {
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;

    @Override
    public String toString() {
        StringBuilder authorsString = new StringBuilder();

        for (String author : authors) {
            authorsString.append(author).append(", ");
        }

        if (authorsString.length() > 0) {
            authorsString.delete(authorsString.length() - 2, authorsString.length());
        }

        return "EssentialProduct{" +
                "title='" + title + '\'' +
                ", authors=" + authorsString +
                ", publisher='" + publisher + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                '}';
    }
}
