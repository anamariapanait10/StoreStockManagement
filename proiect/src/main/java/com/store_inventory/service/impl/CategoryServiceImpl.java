package com.store_inventory.service.impl;

import com.store_inventory.model.Category;
import com.store_inventory.service.CategoryService;

import java.util.*;

public class CategoryServiceImpl implements CategoryService {

    private static List<Category> categoryList = new ArrayList<>();

    @Override
    public List<Category> getAllCategories() {
        return categoryList;
    }

    @Override
    public Optional<Category> getCategoryById(UUID id) {
        return categoryList.stream().filter(c -> c.getId() == id).findAny();
    }

    @Override
    public Optional<Category> getCategoryByName(String categoryName) {
        return categoryList.stream().filter(c -> Objects.equals(c.getName(), categoryName)).findAny();
    }

    @Override
    public List<Category> filterByParentCategoryName(String parentCategoryName) {
        return categoryList.stream().filter(c -> c.getCategoryParent() != null && getCategoryById(c.getCategoryParent()).get().getName() == parentCategoryName).toList();
    }

    public void sortByParentCategoryId(){
        Collections.sort(categoryList);
    }
    @Override
    public void addCategory(Category c) {
        categoryList.add(c);
    }

    @Override
    public void updateCategoryById(UUID id, Category c) {
        this.removeCategoryById(id);
        this.addCategory(c);
    }

    @Override
    public void removeCategoryById(UUID id) {
        categoryList.removeIf(c -> c.getId() == id);
    }

    @Override
    public void printAllCategories() {

        sortByParentCategoryId();

        for(Category c: categoryList){
            if (c.getCategoryParent() == null) { // root category
                System.out.println(c.getName() + ":");
                for (Category subcat: filterByParentCategoryName(c.getName())) {
                    System.out.println("-> " + subcat.getName());
                }
            } else {
               break;
            }
        }
    }
}
