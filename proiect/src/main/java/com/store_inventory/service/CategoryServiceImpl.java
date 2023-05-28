package com.store_inventory.service;

import com.store_inventory.exceptions.CategoryNotFound;
import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.model.Category;
import com.store_inventory.repository.CategoryRepository;
import com.store_inventory.repository.CategoryRepositoryImpl;

import java.sql.SQLException;
import java.util.*;

public final class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.getAll();
    }

    @Override
    public Optional<Category> getCategoryById(UUID id) {
        try {
            return categoryRepository.getObjectById(id);
        } catch (SQLException | ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Category> getCategoryByName(String categoryName) throws CategoryNotFound {
        Optional<Category> cat = null;
        try {
            cat = categoryRepository.getObjectByName(categoryName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!cat.isPresent()){
            throw new CategoryNotFound(categoryName);
        }
        return cat;
    }

    @Override
    public List<Category> filterByParentCategoryName(String parentCategoryName) {
        return categoryRepository.getAll().stream().filter(c -> c.getCategoryParent() != null && getCategoryById(c.getCategoryParent()).get().getName().equals(parentCategoryName)).toList();

    }

    public void sortByParentCategoryId(){
        Collections.sort(categoryRepository.getAll());
    }
    @Override
    public void addCategory(Category c) {
        try {
            categoryRepository.addNewObject(c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCategoryById(UUID id, Category c) {
        this.removeCategoryById(id);
        this.addCategory(c);
    }

    @Override
    public void removeCategoryById(UUID id) {
        categoryRepository.deleteObjectById(id);
    }

    @Override
    public void printAllCategories() {

        sortByParentCategoryId();

        for(Category c: categoryRepository.getAll()){
            if (c.getCategoryParent() == null) { // root category
                System.out.println(c.getName() + ":");
                for (Category subcat: filterByParentCategoryName(c.getName())) {
                    System.out.println("-> " + subcat.getName());
                }
            }
        }
    }

    @Override
    public Optional<Category> getCatgoryWithLogestName(){
        Category maxCateg = null;
        int maxNum = Integer.MIN_VALUE;

        List<Category> categoryList = getAllCategories();
        Iterator<Category> categIterator = categoryList.iterator();
        while (categIterator.hasNext()) {
            Category category = categIterator.next();

            int namelength = category.getName().length();

            if (namelength > maxNum) {
                maxNum = namelength;
                maxCateg = category;
            }
        }

        return Optional.ofNullable(maxCateg);
    }
}
