package org.vtsukur.graphql.demo.cart.conf

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.vtsukur.graphql.demo.cart.deps.ProductServiceRestClient
import org.vtsukur.graphql.demo.cart.domain.Cart
import org.vtsukur.graphql.demo.cart.domain.CartRepository

@Configuration
class RepositoriesConfiguration {

    @Bean
    fun commandLineRunner(cartRepository: CartRepository,
                          productServiceRestClient: ProductServiceRestClient) =
            CommandLineRunner { insertInitialData(cartRepository, productServiceRestClient) }

    private fun insertInitialData(cartRepository: CartRepository,
                                  productServiceRestClient: ProductServiceRestClient) {
        val product1 = productServiceRestClient.fetchProduct("59eb83c0040fa80b29938e3f")
        val product2 = productServiceRestClient.fetchProduct("59eb83c0040fa80b29938e40")
        val product3 = productServiceRestClient.fetchProduct("59eb88bd040fa8125aa9c400")

        val cart = Cart()
        cart.addProduct(product1.id, product1.price, 1)
        cart.addProduct(product2.id, product2.price, 2)
        cart.addProduct(product3.id, product3.price, 1)
        cartRepository.save(cart)
    }

}
