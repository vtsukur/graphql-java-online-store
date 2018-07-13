package org.vtsukur.graphql.demo.cart.deps

import org.springframework.web.client.RestTemplate
import org.vtsukur.graphql.demo.product.api.Product

import java.net.URI

class ProductServiceRestClient(private val http: RestTemplate, private val baseUrl: String) {

    fun fetchProduct(productId: String) = http.getForObject(productUrl(productId), Product::class.java)

    fun productUrl(productId: String) = URI.create("$baseUrl/products/$productId")


}
