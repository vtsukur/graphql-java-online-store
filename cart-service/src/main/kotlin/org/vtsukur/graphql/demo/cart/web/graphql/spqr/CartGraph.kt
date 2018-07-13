package org.vtsukur.graphql.demo.cart.web.graphql.spqr

import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component
import org.vtsukur.graphql.demo.cart.domain.Cart
import org.vtsukur.graphql.demo.cart.domain.CartService

@Component
class CartGraph (private val cartService: CartService) {

    @GraphQLQuery(name = "cart")
    fun cart(@GraphQLArgument(name = "id") id: Long?): Cart {
        log.info("fetching cart with id={}", id)
        return cartService.findCart(id)
    }

    @GraphQLMutation(name = "addProductToCart")
    fun addProductToCart(@GraphQLArgument(name = "cartId") cartId: Long?,
                         @GraphQLArgument(name = "productId") productId: String,
                         @GraphQLArgument(name = "quantity", defaultValue = "1") quantity: Int): Cart {
        log.info("adding {} product(s) with id={} to cart with id={}", quantity, productId, cartId)
        return cartService.addProductToCart(cartId, productId, quantity)
    }

    companion object {
        var log: Logger = getLogger(CartGraph::class.java)
    }

}
