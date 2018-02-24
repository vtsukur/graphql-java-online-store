package org.vtsukur.graphql.demo.cart.web.graphql.spqr;

import graphql.execution.batched.Batched;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.vtsukur.graphql.demo.cart.domain.Item;
import org.vtsukur.graphql.demo.product.api.Product;
import org.vtsukur.graphql.demo.product.api.Products;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;

@Component
@Slf4j
public class ProductGraph {

    private final RestTemplate http;

    @Autowired
    public ProductGraph(RestTemplate http) {
        this.http = http;
    }

    @GraphQLQuery(name = "product")
    @Batched
    public List<Product> products(@GraphQLContext List<Item> items,
                                  @GraphQLEnvironment Set<String> subFields) {
        return http.getForObject(
                "http://localhost:9090/products?ids={id}",
                Products.class,
                items.stream().map(Item::getProductId).collect(joining(",")),
                String.join(",", subFields)
        ).getProducts();
    }

    @GraphQLQuery(name = "images")
    public List<String> images(@GraphQLContext Product product,
                               @GraphQLArgument(name = "limit", defaultValue = "0") int limit) {
        return product.getImages().subList(
                0, limit > 0 ? limit : product.getImages().size());
    }

}
