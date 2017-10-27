package org.vtsukur.graphql.demo.cart.web.graphql.spqr;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vtsukur.graphql.demo.cart.domain.Cart;
import org.vtsukur.graphql.demo.cart.domain.CartService;

@Component
@Slf4j
public class CartGraph {

    private final CartService cartService;

    @Autowired
    public CartGraph(CartService cartService) {
        this.cartService = cartService;
    }

    @GraphQLQuery(name = "cart")
    public Cart cart(@GraphQLArgument(name = "id") Long id) {
        log.info("fetching cart with id={}", id);
        return cartService.findCart(id);
    }

    @GraphQLMutation(name = "addProductToCart")
    public Cart addProductToCart(@GraphQLArgument(name = "cartId") Long cartId,
                                 @GraphQLArgument(name = "productId") String productId,
                                 @GraphQLArgument(name = "quantity", defaultValue = "1") int quantity) {
        log.info("adding {} product(s) with id={} to cart with id={}", quantity, productId, cartId);
        return cartService.addProductToCart(cartId, productId, quantity);
    }

}
