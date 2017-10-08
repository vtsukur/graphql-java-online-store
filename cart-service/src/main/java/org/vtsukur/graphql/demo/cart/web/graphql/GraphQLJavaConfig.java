package org.vtsukur.graphql.demo.cart.web.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.vtsukur.graphql.demo.cart.domain.Cart;
import org.vtsukur.graphql.demo.cart.domain.CartItem;
import org.vtsukur.graphql.demo.cart.domain.CartService;
import org.vtsukur.graphql.demo.product.api.Product;

import java.util.List;

@Configuration
public class GraphQLJavaConfig {

    private final CartService cartService;

    private final RestTemplate http;

    @Autowired
    public GraphQLJavaConfig(CartService cartService, RestTemplate http) {
        this.cartService = cartService;
        this.http = http;
    }

    @Bean
    public GraphQLQueryResolver query() {
        return new GraphQLQueryResolver() {
            public String hello() { return "Hello, Unicorns!"; }
            public Cart cart(Long id) { return cartService.findCart(id); }
        };
    }

    @Bean
    public GraphQLResolver<CartItem> cartItemResolver() {
        return new GraphQLResolver<CartItem>() {
            public Product product(CartItem cartItem) {
                return http.getForObject("http://localhost:9090/products/{id}",
                        Product.class,
                        cartItem.getProductId());
            }
        };
    }

    @Bean
    public GraphQLResolver<Product> productResolver() {
        return new GraphQLResolver<Product>() {
            public List<String> images(Product product, int limit) {
                return product.getImages().subList(
                        0, limit > 0 ? limit : product.getImages().size());
            }
        };
    }

}
