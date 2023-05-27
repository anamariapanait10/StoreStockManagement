package com.store_inventory.repository;

import com.store_inventory.model.Stock;
public sealed interface StockRepository extends Repository<Stock> permits StockRepositoryImpl {
}
