package com.store_inventory.model;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.store_inventory.model.abstracts.AbstractEntity;
import com.store_inventory.model.enums.ProductType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public class Product extends AbstractEntity implements Comparable<Product>{
    private String name;
    private UUID categoryId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate expirationDate;
    private float price;
    private ProductType productType;
    private HashMap<String, String> properties;
    private String expirationStatus;
    @Override
    public int compareTo(Product c){
        return this.getName().compareTo(c.getName());
    }
}

class LocalDateTimeSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        arg1.writeString(arg0.format(DateTimeFormatter.ISO_DATE));
    }
}

class LocalDateTimeDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
        return LocalDate.parse(arg0.getText(), DateTimeFormatter.ISO_DATE);
    }
}
