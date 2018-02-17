package org.vtsukur.graphql.demo.cart.conf;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vtsukur.graphql.demo.cart.deps.ProductServiceRestClient;
import org.vtsukur.graphql.demo.cart.domain.Cart;
import org.vtsukur.graphql.demo.cart.domain.CartRepository;
import org.vtsukur.graphql.demo.product.api.Product;

@Configuration
public class RepositoriesConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(CartRepository cartRepository,
                                               ProductServiceRestClient productServiceRestClient) {
        return ($) -> insertInitialData(cartRepository, productServiceRestClient);
    }

    private static void insertInitialData(CartRepository cartRepository,
                                          ProductServiceRestClient productServiceRestClient) {
        Product product1 = productServiceRestClient.fetchProduct("59eb83c0040fa80b29938e3f");
        Product product2 = productServiceRestClient.fetchProduct("59eb83c0040fa80b29938e40");
        Product product3 = productServiceRestClient.fetchProduct("59eb88bd040fa8125aa9c400");

        Cart cart = new Cart();
        cart.addProduct(product1.getId(), product1.getPrice(), 1);
        cart.addProduct(product2.getId(), product2.getPrice(), 2);
        cart.addProduct(product3.getId(), product3.getPrice(), 1);
        cartRepository.save(cart);
    }

}
