package com.store_inventory.threads;

import com.store_inventory.model.Product;

import java.time.LocalDate;
import java.util.List;
public class ProductExpirationThread implements Runnable {
    private List<Product> products;

    public ProductExpirationThread(List<Product> products){
        this.products = products;
    }

    @Override
    public void run(){
        for (int step = 0; step < 5; step++){
            for(int i = 0; i < products.size(); i++){
                if(products.get(i).getExpirationDate().isBefore(LocalDate.now())){
                    products.get(i).setExpirationStatus("Expired");
                    System.out.println("Product " + products.get(i).getName() + " was set as expired.");
                } else {
                    products.get(i).setExpirationStatus("Not expired");
                    System.out.println("Product " + products.get(i).getName() + " was set as not expired.");
                }
            }
            System.out.println();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
