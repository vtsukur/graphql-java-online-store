package org.vtsukur.graphql.demo.product.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {

    List<Product> findAllBy();

    List<Product> findAllByIdIn(String[] ids);

}
