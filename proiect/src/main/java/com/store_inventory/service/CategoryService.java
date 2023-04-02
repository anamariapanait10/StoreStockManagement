package com.store_inventory.service;

import com.store_inventory.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAllCategories();
    Optional<Category> getCategoryById(UUID id);
    Optional<Category> getCategoryByName(String categoryName);

    List<Category> filterByParentCategoryName(String parentCategoryName);
    void addCategory(Category c);
    void updateCategoryById(UUID id, Category c);
    void removeCategoryById(UUID id);

    void printAllCategories();

}
