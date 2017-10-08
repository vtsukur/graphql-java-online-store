package org.vtsukur.graphql.demo.cart.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<>();

    public BigDecimal getSubTotal() {
        return getItems().stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addProduct(String id, BigDecimal price, int quantity) {
        CartItem cartItem = getItems().stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem(id, 0, BigDecimal.ZERO);
                    getItems().add(newItem);
                    return newItem;
                });
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setTotal(price.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
    }

}
