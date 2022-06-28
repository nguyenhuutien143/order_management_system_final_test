package vn.fis.training.ordermanagement.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void loadAllOrder() {
        log.info("ORDER: {}",orderRepository.findById(1L).get());
    }

    @Test
    void findAllByStatus() {
    }
    @Test
    void findBiId(){

    }

    @Test
    void findAllByOrOrderDateTimeBetween() {
    }

    @Test
    void findByIdUsingEntityGraph() {
        assertEquals(1L, orderRepository.findByIdUsingEntityGraph(1L).get().getId());
    }

    @Test
    void findByIdUsingJoinFetch() {
        assertEquals(1L, orderRepository.findByIdUsingJoinFetch(1L).get().getId());
    }
    @Test
    @Transactional
    void queryAllOrder_UsingCustomerOrderRepository(){
        orderRepository.findAllOrderUsingCustomRepository()
                .forEach(o-> log.info("Order: {}",o));
    }
}
