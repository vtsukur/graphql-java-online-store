package org.vtsukur.graphql.demo.product.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Products {

    private List<Product> products;

}
