package com.store_inventory.mapper;

import com.store_inventory.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SupplierMapper {
    private static final SupplierMapper INSTANCE = new SupplierMapper();

    SupplierMapper(){
    }
    public static SupplierMapper getInstance(){
        return INSTANCE;
    }

    public Optional<Supplier> mapToSupplier(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Supplier obj = Supplier.builder()
                    .supplierName(resultSet.getString("name"))
                    .supplierAddress(resultSet.getString("address"))
                    .contactNumber(resultSet.getString("contact_number"))
                    .build();
            obj.setId(UUID.fromString(resultSet.getString("id")));
            return Optional.of(obj);
        } else {
            return Optional.empty();
        }
    }

    public List<Supplier> mapToSupplierList(ResultSet resultSet) throws SQLException {
        List<Supplier> SupplierList = new ArrayList<>();

        while(resultSet.next()){
            SupplierList.add(mapToSupplier(resultSet).get());
        }

        return SupplierList;
    }
}