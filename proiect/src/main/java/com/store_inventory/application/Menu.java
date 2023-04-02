package com.store_inventory.application;

import com.store_inventory.model.Category;
import com.store_inventory.model.Product;
import com.store_inventory.model.enums.ProductType;
import com.store_inventory.service.*;
import com.store_inventory.service.impl.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

public class Menu {

    private static Menu INSTANCE;
    private final ProductService productService = new ProductServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final LocationService locationService = new LocationServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final SupplierService supplierService = new SupplierServiceImpl();


    public static Menu getInstance() {
        return (INSTANCE == null ? new Menu() : INSTANCE);
    }

    public void showCategories(){
        System.out.println("-------------------- Categories -----------------------");
        categoryService.addCategory(new Category("Food"));
        UUID foodId = categoryService.getCategoryByName("Food").orElseThrow().getId();
        categoryService.addCategory(new Category("Fruits", foodId));
        categoryService.addCategory(new Category("Vegetables", foodId));

        categoryService.addCategory(new Category("Furniture"));
        UUID furnitureId = categoryService.getCategoryByName("Furniture").orElseThrow().getId();
        categoryService.addCategory(new Category("Tables", furnitureId));
        categoryService.addCategory(new Category("Wardrobe", furnitureId));


        categoryService.printAllCategories();
    }
    public void showProducts() {
        showCategories();
        UUID fructeId = categoryService.getCategoryByName("Fruits").orElseThrow().getId();
        UUID legumeId = categoryService.getCategoryByName("Vegetables").orElseThrow().getId();

        System.out.println("-------------------- Products -----------------------");
        productService.addProduct(Product.builder().name("Mar").categoryId(fructeId).expirationDate(LocalDate.now().plusDays(2))
                .price(5.0f).productType(ProductType.PERISHABLE).properties(new HashMap<String, String>(){{
                    put("Culoare", "Rosu");
                    put("Tara de origine", "Romania");
                }}).build());

        productService.addProduct(Product.builder().name("Cartof").categoryId(legumeId).expirationDate(LocalDate.now().plusDays(10))
                .price(2.5f).productType(ProductType.PERISHABLE).properties(new HashMap<String, String>(){{
                    put("Tara de origine", "Irlanda");
                    put("Calitatea", "Intai");
                }}).build());

        productService.printAllProducts();
    }
}