package org.vtsukur.graphql.demo.cart.web.graphql.spqr;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import graphql.GraphQL;
import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.batched.BatchedExecutionStrategy;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.BeanResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;

@Component
public class GraphQLProvider {

    @Bean
    public GraphQL graphQL(CartGraph cartGraph, ProductGraph productQuery) { 
        GraphQLSchema schema = new GraphQLSchemaGenerator()
            .withResolverBuilders(
                    new BeanResolverBuilder("org.vtsukur.graphql.demo.cart.domain"),
                    // Resolve by annotations.
                    new AnnotatedResolverBuilder(),
                    // Resolve public methods inside root package.
                    new PublicResolverBuilder("org.vtsukur.graphql.demo.cart.web.graphql.spqr"))
            .withOperationsFromSingleton(cartGraph)
            .withOperationsFromSingleton(productQuery)
            .withValueMapperFactory(new JacksonValueMapperFactory())
            .generate();
        return GraphQL.newGraphQL(schema)
                .queryExecutionStrategy(new BatchedExecutionStrategy())
                .instrumentation(new ChainedInstrumentation(Arrays.asList(
                        new MaxQueryComplexityInstrumentation(200),
                        new MaxQueryDepthInstrumentation(20)
                )))
                .build();
    }

}