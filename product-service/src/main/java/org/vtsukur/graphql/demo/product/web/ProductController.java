package org.vtsukur.graphql.demo.product.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.vtsukur.graphql.demo.product.domain.ProductRepository;
import org.vtsukur.graphql.demo.product.mapper.ProductMapper;
import org.vtsukur.graphql.demo.product.api.Product;
import org.vtsukur.graphql.demo.product.api.Products;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping
    @ResponseBody
    public Products getProducts(@RequestParam(value = "ids", required = false) String[] ids,
                                @RequestParam(value = "include", required = false) String rawFields) {
        List<org.vtsukur.graphql.demo.product.domain.Product> products = ids == null ?
                productRepository.findAllBy() :
                productRepository.findAllByIdIn(ids);
        Function<org.vtsukur.graphql.demo.product.domain.Product, Product> mapper = StringUtils.isBlank(rawFields) ?
                this::toFullSingletonResource :
                new FieldMapper(rawFields.split(","));
        return new Products(toListResource(products, mapper));
    }

    @RequestMapping("/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable("id") String id) {
        return toFullSingletonResource(productRepository.findById(id).get());
    }

    private List<Product> toListResource(List<org.vtsukur.graphql.demo.product.domain.Product> products, Function<org.vtsukur.graphql.demo.product.domain.Product, Product> mapper) {
        return products.stream().map(mapper).collect(toList());
    }

    private Product toFullSingletonResource(org.vtsukur.graphql.demo.product.domain.Product product) {
        return ProductMapper.INSTANCE.fromEntity(product);
    }

    private static class FieldMapper implements Function<org.vtsukur.graphql.demo.product.domain.Product, Product> {

        private final String[] fields;

        FieldMapper(String[] fields) {
            this.fields = fields;
        }

        @Override
        public Product apply(org.vtsukur.graphql.demo.product.domain.Product in) {
            Product out = new Product();
            Arrays.stream(fields).forEach(field -> {
                PropertyDescriptor sourceDescriptor = BeanUtils.getPropertyDescriptor(org.vtsukur.graphql.demo.product.domain.Product.class, field);
                Object value = ReflectionUtils.invokeMethod(sourceDescriptor.getReadMethod(), in);

                PropertyDescriptor destDescriptor = BeanUtils.getPropertyDescriptor(Product.class, field);
                ReflectionUtils.invokeMethod(destDescriptor.getWriteMethod(), out, value);
            });
            return out;
        }

    }

}
