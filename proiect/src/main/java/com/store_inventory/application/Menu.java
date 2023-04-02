package com.store_inventory.application;

import com.store_inventory.model.*;
import com.store_inventory.model.enums.LocationType;
import com.store_inventory.model.enums.ProductType;
import com.store_inventory.service.*;
import com.store_inventory.service.impl.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class Menu {

    private static Menu INSTANCE;
    private final ProductService productService = new ProductServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final LocationService locationService = new LocationServiceImpl();

    private final SupplierService supplierService = new SupplierServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();


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
        productService.addProduct(Product.builder().name("Apple").categoryId(fructeId).expirationDate(LocalDate.now().plusDays(2))
                .price(5.0f).productType(ProductType.PERISHABLE).properties(new HashMap<String, String>(){{
                    put("Color", "Red");
                    put("Tara de origine", "Romania");
                }}).build());

        productService.addProduct(Product.builder().name("Pear").categoryId(fructeId).expirationDate(LocalDate.now().plusDays(2))
                .price(5.0f).productType(ProductType.PERISHABLE).properties(new HashMap<String, String>(){{
                    put("Culoare", "Yellow");
                }}).build());

        productService.addProduct(Product.builder().name("Banana").categoryId(legumeId).expirationDate(LocalDate.now().plusDays(10))
                .price(2.5f).productType(ProductType.PERISHABLE).properties(new HashMap<String, String>(){{
                    put("Type", "Bio");
                }}).build());

        productService.addProduct(Product.builder().name("Potato").categoryId(legumeId).expirationDate(LocalDate.now().plusDays(10))
                .price(2.5f).productType(ProductType.PERISHABLE).properties(new HashMap<String, String>(){{
                    put("Country of origin", "Ireland");
                    put("Quality", "First");
                }}).build());

        productService.addProduct(Product.builder().name("Carrot").categoryId(legumeId).expirationDate(LocalDate.now().plusDays(10))
                .price(2.5f).productType(ProductType.PERISHABLE).properties(new HashMap<String, String>(){{
                    put("Country of origin", "Romania");
                }}).build());

        productService.removeProductById(productService.getProductByName("Carrot").get().getId());

        productService.sortByProductName();
        productService.printAllProducts();
    }

    public void showLocations() {
        System.out.println("-------------------- Locations -----------------------");
        Product potato = productService.getProductByName("Potato").get();
        Stock s1 = Stock.builder().product(potato).productQuantity(5).build();
        Stock s2 = Stock.builder().product(potato).productQuantity(2).build();
        Product apple = productService.getProductByName("Apple").get();
        Stock s3 = Stock.builder().product(apple).productQuantity(25).build();
        Stock s4 =Stock.builder().product(apple).productQuantity(50).build();
        Product pear = productService.getProductByName("Pear").get();
        Stock s5 = Stock.builder().product(pear).productQuantity(100).build();
        Stock s6 = Stock.builder().product(pear).productQuantity(500).build();
        Product banana = productService.getProductByName("Banana").get();
        Stock s7 = Stock.builder().product(banana).productQuantity(200).build();
        Stock s8 = Stock.builder().product(banana).productQuantity(250).build();

        locationService.addLocation(Location.builder().name("Emag").address("Leon Street").locationType(LocationType.SHOP).maxStockCapacity(1000).build());
        locationService.addLocation(Location.builder().name("Target").address("Mayflower Street").locationType(LocationType.SHOP).maxStockCapacity(500).build());
        locationService.addLocation(Location.builder().name("Mobexpert").address("Wakley Stree").locationType(LocationType.DEPOSIT).maxStockCapacity(2500).build());

        UUID emag = locationService.getLocationByName("Emag").get().getId();
        UUID target = locationService.getLocationByName("Target").get().getId();
        UUID mobexpert = locationService.getLocationByName("Mobexpert").get().getId();
        locationService.addStocksToLocation(emag, s1);
        locationService.addStocksToLocation(emag, s3);
        locationService.addStocksToLocation(emag, s5);
        locationService.addStocksToLocation(target, s2);
        locationService.addStocksToLocation(target, s4);
        locationService.addStocksToLocation(target, s7);
        locationService.addStocksToLocation(mobexpert, s4);
        locationService.addStocksToLocation(mobexpert, s6);
        locationService.addStocksToLocation(mobexpert, s8);
        locationService.printAllStocks();

        System.out.println();
        Optional<Stock> s = locationService.getStockFromLocation(emag, s1.getId());
        System.out.println("Emag has potatos in stock:");
        System.out.println(s.get().getProduct().getName() + ": " + s.get().getProductQuantity());
    }

    public void showSuppliers(){
        System.out.println("-------------------- Suppliers -----------------------");
        supplierService.addSupplier(Supplier.builder().supplierName("The Local Supply Depot").supplierAddress("Sunrise Street").contactNumber("0712312312").build());
        supplierService.addSupplier(Supplier.builder().supplierName("The Supply Corner").supplierAddress("Occasion Street").contactNumber("0712315412").build());
        supplierService.addSupplier(Supplier.builder().supplierName("Totally Stocked").supplierAddress("Global Street").contactNumber("0712312300").build());
        supplierService.printAllSuppliers();
    }
    public void showOrders() {
        showSuppliers();
        System.out.println("-------------------- Orders -----------------------");

        Optional<Supplier> s1 = supplierService.getSupplierByName("The Local Supply Depot");
        Optional<Supplier> s2 = supplierService.getSupplierByName("The Supply Corner");
        Optional<Supplier> s3 = supplierService.getSupplierByName("Totally Stocked");

        Optional<Location> l1 = locationService.getLocationByName("Emag");
        Optional<Location> l2 = locationService.getLocationByName("Target");
        Optional<Location> l3 = locationService.getLocationByName("Mobexpert");

        Optional<Product> pear = productService.getProductByName("Pear");
        Optional<Product> potato = productService.getProductByName("Potato");
        Optional<Product> apple = productService.getProductByName("Apple");
        Optional<Product> banana = productService.getProductByName("Banana");
        orderService.addOrder(Order.builder().supplier(s1.get()).orderLocation(l1.get()).totalPrice(40).orderedProducts(new HashMap<Product, Integer>(){{
            put(pear.get(), 10);
            put(potato.get(), 50);
        }}).build());

        orderService.addOrder(Order.builder().supplier(s2.get()).orderLocation(l2.get()).totalPrice(100).orderedProducts(new HashMap<Product, Integer>(){{
            put(apple.get(), 100);
            put(banana.get(), 50);
            put(potato.get(), 50);
        }}).build());

        orderService.addOrder(Order.builder().supplier(s3.get()).orderLocation(l3.get()).totalPrice(80).orderedProducts(new HashMap<Product, Integer>(){{
            put(pear.get(), 20);
            put(potato.get(), 100);
        }}).build());

        orderService.printAllOrders();
    }

}