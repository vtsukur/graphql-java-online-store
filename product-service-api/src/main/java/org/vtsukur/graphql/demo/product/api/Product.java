package org.vtsukur.graphql.demo.product.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    private String id;

    private String title;

    private BigDecimal price;

    private String description;

    private String sku;

    private List<String> images;

}
