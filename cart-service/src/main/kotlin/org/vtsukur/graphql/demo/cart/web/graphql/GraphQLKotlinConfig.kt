package org.vtsukur.graphql.demo.cart.web.graphql

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.vtsukur.graphql.demo.cart.domain.CartService
import org.vtsukur.graphql.demo.cart.domain.Item
import org.vtsukur.graphql.demo.product.api.Product

@Configuration
class GraphQLKotlinConfig(private val cartService: CartService, private val http: RestTemplate) {

    @Bean
    fun query() =
            object : GraphQLQueryResolver {
                fun hello() = "Hello, Unicorns!"

                fun cart(id: Long?) = cartService.findCart(id)
            }

    @Bean
    fun cartItemResolver() =
            object : GraphQLResolver<Item> {
                fun product(item: Item) =
                        http.getForObject("http://localhost:9090/products/{id}",
                                Product::class.java,
                                item.productId)
            }

    @Bean
    fun productResolver() =
            object : GraphQLResolver<Product> {
                fun images(product: Product, limit: Int) =
                        product.images.subList(
                                0, if (limit > 0) limit else product.images.size)
            }

    @Bean
    fun mutations() =
            object : GraphQLMutationResolver {
                fun addProductToCart(cartId: Long?, productId: String, quantity: Int) =
                        cartService.addProductToCart(cartId, productId, quantity)
            }

}
