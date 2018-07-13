package org.vtsukur.graphql.demo.cart.web.http

import java.math.BigDecimal

data class CartWithProductsProjection(var id: Long = -1L, var items: List<Item> = mutableListOf(),
                                      var subTotal: BigDecimal = BigDecimal.ZERO) {

    data class Item(var product: ProductProjection = ProductProjection(), var quantity: Int = 0,
                    var total: BigDecimal = BigDecimal.ZERO) {

        data class ProductProjection(var id: String = "", var title: String = "", var price: BigDecimal = BigDecimal.ZERO,
                                     var image: String = "", var sku: String = "")
    }
}


