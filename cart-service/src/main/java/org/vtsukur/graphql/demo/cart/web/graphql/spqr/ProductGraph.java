package org.vtsukur.graphql.demo.cart.web.graphql.spqr;

import graphql.execution.batched.Batched;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vtsukur.graphql.demo.cart.deps.ProductServiceRestClient;
import org.vtsukur.graphql.demo.cart.domain.Item;
import org.vtsukur.graphql.demo.product.api.Product;

import java.util.List;
import java.util.Set;

@Component
public class ProductGraph {

    private final ProductServiceRestClient client;

    @Autowired
    public ProductGraph(ProductServiceRestClient client) {
        this.client = client;
    }

    @GraphQLQuery(name = "product")
    @Batched
    public List<Product> products(@GraphQLContext List<Item> items,
                                  @GraphQLEnvironment Set<String> subFields) {
        return client.fetchProducts(items, subFields);
    }

    @GraphQLQuery(name = "images")
    public List<String> images(@GraphQLContext Product product,
                               @GraphQLArgument(name = "limit", defaultValue = "0") int limit) {
        return product.getImages().subList(
                0, limit > 0 ? limit : product.getImages().size());
    }

}
