package org.vtsukur.graphql.demo.cart.domain

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Cart (

    @Id
    @GeneratedValue
    var id: Long? = null,

    @ElementCollection(fetch = FetchType.EAGER)
    val items: MutableList<Item> = mutableListOf()) {

    fun getSubTotal() : BigDecimal = items
                .map { it.total }
                .reduce { obj, augend -> obj.add(augend) }

    fun addProduct(id: String, price: BigDecimal, quantity: Int) {
        val item = items.stream()
                .filter({ p -> p.productId == id })
                .findFirst()
                .orElseGet({
                    val newItem = Item(id, 0, BigDecimal.ZERO)
                    items.add(newItem)
                    newItem
                })
        item.quantity = item.quantity + quantity
        item.total = price.multiply(BigDecimal.valueOf(item.quantity.toLong()))
    }

}
