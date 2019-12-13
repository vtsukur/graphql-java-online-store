package org.vtsukur.graphql.demo.cart.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vtsukur.graphql.demo.cart.deps.ProductServiceRestClient;
import org.vtsukur.graphql.demo.product.api.Product;

@Component
public class CartService {

    private final CartRepository cartRepository;

    private final ProductServiceRestClient productServiceRestClient;

    @Autowired
    public CartService(CartRepository cartRepository, ProductServiceRestClient productServiceRestClient) {
        this.cartRepository = cartRepository;
        this.productServiceRestClient = productServiceRestClient;
    }

    public Cart findCart(Long cartId) {
        return cartRepository.findById(cartId).get();
    }

    public Cart addProductToCart(Long cartId, String productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).get();
        Product product = productServiceRestClient.fetchProduct(productId);
        cart.addProduct(product.getId(), product.getPrice(), quantity);
        return cartRepository.save(cart);
    }

}
