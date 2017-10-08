package org.vtsukur.graphql.demo.cart.domain;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
