package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import com.store_inventory.service.CategoryService;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Category extends AbstractEntity implements Comparable<Category> {
    private String name;
    private UUID categoryParent;

    public Category(String name) {
        this.name = name;
        this.categoryParent = null;
    }

    @Override
    public int compareTo(Category c){
        if (this.getCategoryParent() == null){
            if(c.getCategoryParent() == null){
                return 0;
            }
            return -1;
        } else if(c.getCategoryParent() == null) {
            return 1;
        }
        return this.getCategoryParent().compareTo(c.getCategoryParent());
    }
}