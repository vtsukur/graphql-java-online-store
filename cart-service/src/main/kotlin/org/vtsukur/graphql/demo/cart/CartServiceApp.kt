package org.vtsukur.graphql.demo.cart

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

fun main(args: Array<String>) {
    SpringApplication.run(CartServiceApp::class.java, *args)
}

@SpringBootApplication
class CartServiceApp
