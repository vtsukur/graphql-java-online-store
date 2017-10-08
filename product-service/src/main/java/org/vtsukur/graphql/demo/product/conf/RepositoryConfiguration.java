package org.vtsukur.graphql.demo.product.conf;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.vtsukur.graphql.demo.product.domain.Product;
import org.vtsukur.graphql.demo.product.domain.ProductRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return ($) -> insertInitialData(productRepository);
    }

    private static void insertInitialData(ProductRepository productRepository) {
        Product product1 = new Product(
                "59eb83c0040fa80b29938e3f",
                "Combo Pack with Dreamy Eyes 12\" (Pink) Soft Toy",
                BigDecimal.valueOf(26.99),
                "Spread Unicorn love amongst your friends and family by " +
                        "purchasing a Unicorn adoption combo pack today. " +
                        "You'll receive your very own fabulous adoption pack and a " +
                        "12\" Dreamy Eyes (Pink) cuddly toy. It makes the perfect gift for loved ones. " +
                        "Go on, you know you want to, adopt today!",
                "010",
                Arrays.asList(
                        "http://localhost:8080/img/918d8d4cc83d4e5f8680ca4edfd5b6b2.jpg",
                        "http://localhost:8080/img/f343889c0bb94965845e65d3f39f8798.jpg",
                        "http://localhost:8080/img/dd55129473e04f489806db0dc6468dd9.jpg",
                        "http://localhost:8080/img/64eba4524a1f4d5d9f1687a815795643.jpg",
                        "http://localhost:8080/img/5727549e9131440dbb3cd707dce45d0f.jpg",
                        "http://localhost:8080/img/28ae9369ec3c442dbfe6901434ad15af.jpg"
                )
        );
        productRepository.save(product1);

        Product product2 = new Product(
                "59eb83c0040fa80b29938e40",
                "Dreamy Eyes 12\" (Pink) Unicorn Soft Toy",
                BigDecimal.valueOf(12.99),
                "A fabulous 12\" pink and white Unicorn soft toy for you to snuggle at leisure. " +
                        "Also makes the perfect gift for loved ones. Go on you know you want to, adopt today!",
                "006",
                Collections.singletonList("http://localhost:8080/img/f343889c0bb94965845e65d3f39f8798.jpg")
        );
        productRepository.save(product2);

        Product product3 = new Product(
                "59eb88bd040fa8125aa9c400",
                "Combo Pack with Dreamy Eyes 12\" (White) Soft Toy",
                BigDecimal.valueOf(26.99),
                "Spread Unicorn love amongst your friends and family by " +
                        "purchasing a Unicorn adoption combo pack today. " +
                        "You'll receive your very own fabulous adoption pack and a " +
                        "12\" Dreamy Eyes (White) cuddly toy. It makes the perfect gift for loved ones. " +
                        "Go on, you know you want to, adopt today!",
                "010",
                Arrays.asList(
                        "http://localhost:8080/img/fb72e75c5f29488db41abf2f918769e4.jpg",
                        "http://localhost:8080/img/cb8314b3b1e64e6998aad9ab426789be.jpg",
                        "http://localhost:8080/img/dd55129473e04f489806db0dc6468dd9.jpg",
                        "http://localhost:8080/img/64eba4524a1f4d5d9f1687a815795643.jpg",
                        "http://localhost:8080/img/5727549e9131440dbb3cd707dce45d0f.jpg",
                        "http://localhost:8080/img/28ae9369ec3c442dbfe6901434ad15af.jpg"
                )
        );
        productRepository.save(product3);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongoBean = new EmbeddedMongoFactoryBean();
        mongoBean.setBindIp("localhost");
        return new MongoTemplate(mongoBean.getObject(), "products_db");
    }

}
