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
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getExpirationDate().isBefore(LocalDate.now())){
                products.get(i).setExpirationStatus("Expired");
            }
        }
    }
}
