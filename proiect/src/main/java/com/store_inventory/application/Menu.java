package com.store_inventory.application;

import com.store_inventory.exceptions.CategoryNotFound;
import com.store_inventory.exceptions.LocationNotFound;
import com.store_inventory.exceptions.ProductNotFound;
import com.store_inventory.exceptions.StockNotFound;
import com.store_inventory.model.*;
import com.store_inventory.model.abstracts.Transaction;
import com.store_inventory.model.enums.LocationType;
import com.store_inventory.model.enums.ProductType;
import com.store_inventory.service.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {
    private static Logger logger = Logger.getLogger(Menu.class.getName());;
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
        UUID fructeId, legumeId;
        try {
            fructeId = categoryService.getCategoryByName("Fruits").get().getId();
        } catch (CategoryNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            legumeId = categoryService.getCategoryByName("Vegetables").orElseThrow().getId();
        } catch (CategoryNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }

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
        Product potato, apple, pear, banana;
        try {
            potato = productService.getProductByName("Potato").get();
        } catch (ProductNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        Stock s1 = Stock.builder().product(potato).productQuantity(5).build();
        Stock s2 = Stock.builder().product(potato).productQuantity(2).build();
        try {
            apple = productService.getProductByName("Apple").get();
        } catch (ProductNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        Stock s3 = Stock.builder().product(apple).productQuantity(25).build();
        Stock s4 =Stock.builder().product(apple).productQuantity(50).build();
        try {
            pear = productService.getProductByName("Pear").get();
        } catch (ProductNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        Stock s5 = Stock.builder().product(pear).productQuantity(100).build();
        Stock s6 = Stock.builder().product(pear).productQuantity(500).build();
        try {
            banana = productService.getProductByName("Banana").get();
        } catch (ProductNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        Stock s7 = Stock.builder().product(banana).productQuantity(200).build();
        Stock s8 = Stock.builder().product(banana).productQuantity(250).build();

        locationService.addLocation(Location.builder().name("Emag").address("Leon Street").locationType(LocationType.SHOP).maxStockCapacity(1000).build());
        locationService.addLocation(Location.builder().name("Target").address("Mayflower Street").locationType(LocationType.SHOP).maxStockCapacity(500).build());
        locationService.addLocation(Location.builder().name("Mobexpert").address("Wakley Stree").locationType(LocationType.DEPOSIT).maxStockCapacity(2500).build());

        UUID emag, target, mobexpert;
        try {
            emag = locationService.getLocationByName("Emag").get().getId();
        } catch (LocationNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            target = locationService.getLocationByName("Target").get().getId();
        } catch (LocationNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            mobexpert = locationService.getLocationByName("Mobexpert").get().getId();
        } catch (LocationNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
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
        Optional<Stock> s;
        try {
            s = locationService.getStockFromLocation(emag, s1.getId());
        } catch (StockNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
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

        Optional<Supplier> s1, s2, s3;
        try {
            s1 = supplierService.getSupplierByName("The Local Supply Depot");
        } catch (LocationNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            s2 = supplierService.getSupplierByName("The Supply Corner");
        } catch (LocationNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            s3 = supplierService.getSupplierByName("Totally Stocked");
        } catch (LocationNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }

        Optional<Location> l1, l2, l3;
        try {
            l1 = locationService.getLocationByName("Emag");
        } catch (LocationNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            l2 = locationService.getLocationByName("Target");
        } catch (LocationNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            l3 = locationService.getLocationByName("Mobexpert");
        } catch (LocationNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        Optional<Product> pear, potato, apple, banana;

        try {
            potato = productService.getProductByName("Potato");
        } catch (ProductNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            apple = productService.getProductByName("Apple");
        } catch (ProductNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            pear = productService.getProductByName("Pear");
        } catch (ProductNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
        try {
            banana = productService.getProductByName("Banana");
        } catch (ProductNotFound e){
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }

        Order o1 = Order.builder().supplier(s1.get()).orderLocation(l1.get()).totalPrice(40).orderedProducts(new HashMap<Product, Integer>(){{
            put(pear.get(), 10);
            put(potato.get(), 50);
        }}).build();
        Order o2 = Order.builder().supplier(s2.get()).orderLocation(l2.get()).totalPrice(100).orderedProducts(new HashMap<Product, Integer>(){{
            put(apple.get(), 100);
            put(banana.get(), 50);
            put(potato.get(), 50);
        }}).build();
        Order o3 = Order.builder().supplier(s3.get()).orderLocation(l3.get()).totalPrice(80).orderedProducts(new HashMap<Product, Integer>(){{
            put(pear.get(), 20);
            put(potato.get(), 100);
        }}).build();

        Transaction t1 = CashTransaction.builder().amount(40.f).build();
        Transaction t2 = CardTransaction.builder().amount(100.f).cardNumber("1234567890123456").cardExpirationDate(LocalDate.now().plusDays(10)).cardHolderName("Person 1").build();
        Transaction t3 = CardTransaction.builder().amount(80.f).cardNumber("1234567890123456").cardExpirationDate(LocalDate.now().plusDays(20)).cardHolderName("Person 2").build();

        orderService.addOrder(o1);
        orderService.updateOrderTransaction(o1.getId(), t1);
        orderService.addOrder(o2);
        orderService.updateOrderTransaction(o2.getId(), t2);
        orderService.addOrder(o3);
        orderService.updateOrderTransaction(o3.getId(), t3);

        orderService.printAllOrders();

        System.out.println("-------------------- Transactions -----------------------");
        orderService.printAllTransactions();
    }

}