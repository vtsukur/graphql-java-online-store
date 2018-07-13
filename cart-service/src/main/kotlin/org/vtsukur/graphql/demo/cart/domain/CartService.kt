package org.vtsukur.graphql.demo.cart.domain

import org.springframework.stereotype.Component
import org.vtsukur.graphql.demo.cart.deps.ProductServiceRestClient

@Component
class CartService (private val cartRepository: CartRepository, private val productServiceRestClient: ProductServiceRestClient) {

    fun findCart(cartId: Long?): Cart = cartRepository.findOne(cartId)

    fun addProductToCart(cartId: Long?, productId: String, quantity: Int): Cart {
        val cart = cartRepository.findOne(cartId)
        val product = productServiceRestClient.fetchProduct(productId)
        cart.addProduct(product.id, product.price, quantity)
        return cartRepository.save(cart)
    }

}
