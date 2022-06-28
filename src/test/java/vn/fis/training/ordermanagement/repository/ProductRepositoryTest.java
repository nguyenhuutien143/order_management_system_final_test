package vn.fis.training.ordermanagement.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void when_QueryAll_Name_Only() {
//        productRepository.findAllByName("Iphone 6s").forEach((productDTO) -> log.info("Product Name: {}", productDTO.getName()));
        productRepository.findAllProjectedBy().forEach((productDTO) -> log.info("Product Name: {}", productDTO.getName()));
    }
}
