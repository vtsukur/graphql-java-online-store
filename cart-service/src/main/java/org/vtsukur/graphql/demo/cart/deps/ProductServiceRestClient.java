package org.vtsukur.graphql.demo.cart.deps;

import org.springframework.web.client.RestTemplate;
import org.vtsukur.graphql.demo.product.api.Product;

import java.net.URI;

public class ProductServiceRestClient {

    private final RestTemplate http;

    private final String baseUrl;

    public ProductServiceRestClient(RestTemplate http, String baseUrl) {
        this.http = http;
        this.baseUrl = baseUrl;
    }

    public Product fetchProduct(String productId) {
        return http.getForObject(productUrl(productId), Product.class);
    }

    public URI productUrl(String productId) {
        return URI.create(String.format("%s/products/%s", baseUrl, productId));
    }

}
