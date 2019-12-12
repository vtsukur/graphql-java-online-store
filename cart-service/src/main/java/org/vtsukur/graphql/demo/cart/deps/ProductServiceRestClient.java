package org.vtsukur.graphql.demo.cart.deps;

import org.springframework.web.client.RestTemplate;
import org.vtsukur.graphql.demo.cart.domain.Item;
import org.vtsukur.graphql.demo.product.api.Product;
import org.vtsukur.graphql.demo.product.api.Products;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Set;

public class ProductServiceRestClient {

    private final RestTemplate http;

    private final String productsUrl;

    public ProductServiceRestClient(RestTemplate http, String baseUrl) {
        this.http = http;
        productsUrl = baseUrl + "/products";
    }

    public Product fetchProduct(String productId) {
        return http.getForObject(productsUrl + '/' + productId, Product.class);
    }

    public List<Product> fetchProducts(List<Item> items, Set<String> subFields) {
        return http
            .getForObject(productsUrl + "?ids={id}&include={fields}",
                Products.class,
                items.stream().map(Item::getProductId).collect(joining(",")),
                String.join(",", subFields))
            .getProducts();
    }

}
