package org.vtsukur.graphql.demo.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal total;

    CartItem(String productId, BigDecimal productPrice, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.total = BigDecimal.valueOf(quantity).multiply(productPrice);
    }

}
