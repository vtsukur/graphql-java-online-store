package org.vtsukur.graphql.demo.cart.domain

import org.springframework.data.repository.CrudRepository

interface CartRepository : CrudRepository<Cart, Long>
