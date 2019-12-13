package org.vtsukur.graphql.demo.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.vtsukur.graphql.demo.product.api.Product;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product fromEntity(org.vtsukur.graphql.demo.product.domain.Product product);

}
