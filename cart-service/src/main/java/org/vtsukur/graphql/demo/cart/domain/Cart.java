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
    private List<Item> items = new ArrayList<>();

    public BigDecimal getSubTotal() {
        return getItems().stream()
                .map(Item::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addProduct(String id, BigDecimal price, int quantity) {
        Item item = getItems().stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElseGet(() -> {
                    Item newItem = new Item(id, 0, BigDecimal.ZERO);
                    getItems().add(newItem);
                    return newItem;
                });
        item.setQuantity(item.getQuantity() + quantity);
        item.setTotal(price.multiply(BigDecimal.valueOf(item.getQuantity())));
    }

}
