package org.vtsukur.graphql.demo.cart.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.vtsukur.graphql.demo.cart.deps.ProductServiceRestClient;

@Configuration
public class RestClientsConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @Bean
    public ProductServiceRestClient productServiceRestClient() {
        return new ProductServiceRestClient(restTemplate(), "http://localhost:9090");
    }

}
