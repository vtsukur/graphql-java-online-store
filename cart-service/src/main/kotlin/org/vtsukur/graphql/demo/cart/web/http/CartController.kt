package org.vtsukur.graphql.demo.cart.web.http

import org.springframework.web.bind.annotation.*
import org.vtsukur.graphql.demo.cart.deps.ProductServiceRestClient
import org.vtsukur.graphql.demo.cart.domain.Cart
import org.vtsukur.graphql.demo.cart.domain.CartService
import org.vtsukur.graphql.demo.cart.domain.Item
import org.vtsukur.graphql.demo.product.api.Product

@RestController
class CartController(private val cartService: CartService, private val productServiceRestClient: ProductServiceRestClient) {

    @RequestMapping("/carts/{id}")
    @ResponseBody
    operator fun get(@PathVariable id: Long?,
                     @RequestParam(value = "projection", required = false) projection: String): Any {
        val cart = cartService.findCart(id)
        return if ("with-products" == projection) {
            getProjectionWithProducts(cart)
        } else cart
    }

    private fun getProjectionWithProducts(source: Cart): CartWithProductsProjection {
        val cart = CartWithProductsProjection()
        cart.id = source.id!!
        cart.items = toItemsWithProducts(source.items)
        cart.subTotal = source.getSubTotal()
        return cart
    }

    private fun toItemsWithProducts(source: List<Item>): List<CartWithProductsProjection.Item> =
            source.map { sourceItem ->
                val item = CartWithProductsProjection.Item()
                item.product = toProductProjection(productServiceRestClient.fetchProduct(sourceItem.productId))
                item.quantity = sourceItem.quantity
                item.total = sourceItem.total
                item
            }

    private fun toProductProjection(source: Product): CartWithProductsProjection.Item.ProductProjection {
        val product = CartWithProductsProjection.Item.ProductProjection()
        product.id = source.id
        product.title = source.title
        product.price = source.price
        product.sku = source.sku
        product.image = source.images[0]
        return product
    }

}
