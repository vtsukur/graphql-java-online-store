package org.vtsukur.graphql.demo.cart.domain

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Item (

    @Column(nullable = false)
    var productId: String = "",

    @Column(nullable = false)
    var quantity: Int = 0,

    @Column(nullable = false)
    var total: BigDecimal = BigDecimal.ZERO) {

    internal constructor(productId: String, productPrice: BigDecimal, quantity: Int) : this() {
        this.productId = productId
        this.quantity = quantity
        this.total = BigDecimal.valueOf(quantity.toLong()).multiply(productPrice)
    }

}
