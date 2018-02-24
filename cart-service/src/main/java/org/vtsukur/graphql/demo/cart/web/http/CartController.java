package org.vtsukur.graphql.demo.cart.web.http;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vtsukur.graphql.demo.cart.deps.ProductServiceRestClient;
import org.vtsukur.graphql.demo.cart.domain.Cart;
import org.vtsukur.graphql.demo.cart.domain.Item;
import org.vtsukur.graphql.demo.cart.domain.CartService;
import org.vtsukur.graphql.demo.product.api.Product;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CartController {

    private final CartService cartService;

    private final ProductServiceRestClient productServiceRestClient;

    @Autowired
    public CartController(CartService cartService, ProductServiceRestClient productServiceRestClient) {
        this.cartService = cartService;
        this.productServiceRestClient = productServiceRestClient;
    }

    @RequestMapping("/carts/{id}")
    @ResponseBody
    public Object get(@PathVariable Long id,
                      @RequestParam(value = "projection", required = false) String projection) {
        val cart = cartService.findCart(id);
        if ("with-products".equals(projection)) {
            return getProjectionWithProducts(cart);
        }
        return cart;
    }

    private CartWithProductsProjection getProjectionWithProducts(Cart source) {
        val cart = new CartWithProductsProjection();
        cart.setId(source.getId());
        cart.setItems(toItemsWithProducts(source.getItems()));
        cart.setSubTotal(source.getSubTotal());
        return cart;
    }

    private List<CartWithProductsProjection.Item> toItemsWithProducts(List<Item> source) {
        return source.stream().map(sourceItem -> {
            val item = new CartWithProductsProjection.Item();
            item.setProduct(toProductProjection(productServiceRestClient.fetchProduct(sourceItem.getProductId())));
            item.setQuantity(sourceItem.getQuantity());
            item.setTotal(sourceItem.getTotal());
            return item;
        }).collect(Collectors.toList());
    }

    private CartWithProductsProjection.Item.ProductProjection toProductProjection(Product source) {
        val product = new CartWithProductsProjection.Item.ProductProjection();
        product.setId(source.getId());
        product.setTitle(source.getTitle());
        product.setPrice(source.getPrice());
        product.setSku(source.getSku());
        product.setImage(source.getImages().get(0));
        return product;
    }

}
