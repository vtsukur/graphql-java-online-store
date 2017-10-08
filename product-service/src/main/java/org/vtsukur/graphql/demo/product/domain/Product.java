package org.vtsukur.graphql.demo.product.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@Document
public class Product {

    private final String id;

    private final String title;

    private final BigDecimal price;

    private final String description;

    private final String sku;

    private final List<String> images;

}
