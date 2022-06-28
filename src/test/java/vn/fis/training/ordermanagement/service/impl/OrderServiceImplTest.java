package vn.fis.training.ordermanagement.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.fis.training.ordermanagement.dto.CreateOrderItemDTO;
import vn.fis.training.ordermanagement.dto.QueryOrderDTO;
import vn.fis.training.ordermanagement.service.OrderService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderServiceImplTest {
//    @Autowired
//    private OrderService orderService;
//    @Test
//    void addOrderItem() {
//        CreateOrderItemDTO createOrderItemDTO= CreateOrderItemDTO.builder().orderId(0L).productId(0L).quantity(1).build();
//        orderService.addOrderItem(createOrderItemDTO);
//    }
//    @Test
//    void queryOrderDTO(){
//        QueryOrderDTO  queryOrderDTO= QueryOrderDTO.builder().customerId(1L).amountGreatThan(1000000).orderDateGreatThan(LocalDateTime.of(2017, 2, 13, 15, 56)).build();
//        orderService.ORDER_LIST(queryOrderDTO);
//    }
}