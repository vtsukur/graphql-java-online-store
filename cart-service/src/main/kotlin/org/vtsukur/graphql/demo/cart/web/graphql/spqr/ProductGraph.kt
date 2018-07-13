package org.vtsukur.graphql.demo.cart.web.graphql.spqr

import graphql.execution.batched.Batched
import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLContext
import io.leangen.graphql.annotations.GraphQLEnvironment
import io.leangen.graphql.annotations.GraphQLQuery
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.vtsukur.graphql.demo.cart.domain.Item
import org.vtsukur.graphql.demo.product.api.Product
import org.vtsukur.graphql.demo.product.api.Products
import java.util.stream.Collectors.joining

@Component
class ProductGraph (private val http: RestTemplate) {

    @GraphQLQuery(name = "product")
    @Batched
    fun products(@GraphQLContext items: List<Item>,
                 @GraphQLEnvironment subFields: Set<String>) =
            http.getForObject(
                "http://localhost:9090/products?ids={id}",
                Products::class.java,
                items.stream().map<String>({ it.productId }).collect(joining(",")),
                subFields.joinToString(",")
            ).products

    @GraphQLQuery(name = "images")
    fun images(@GraphQLContext product: Product,
               @GraphQLArgument(name = "limit", defaultValue = "0") limit: Int) =
            product.images.subList(
                0, if (limit > 0) limit else product.images.size)

}
