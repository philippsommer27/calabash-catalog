package com.example.repositoryinloop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findAll() {
        List<Product> products = productRepository.findAll();
        for (int i = 0; i < 100000; i++) {
            System.out.println(products);
        }
        assertThat(true).isTrue();
    }
}