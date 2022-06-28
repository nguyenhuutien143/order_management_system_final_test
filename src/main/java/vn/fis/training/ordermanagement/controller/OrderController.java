package vn.fis.training.ordermanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fis.training.ordermanagement.dto.*;
import vn.fis.training.ordermanagement.model.Order;
import vn.fis.training.ordermanagement.service.OrderService;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public Page<OrderDTO> findAll(@PathVariable(name="pageNumber") Integer pageNumber, @PathVariable(name="pageSize") Integer pageSize) {

        return orderService.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<DetailOrderDTO> findById(@PathVariable("orderId") Long orderId) {
        Order order = orderService.findById(orderId);
        DetailOrderDTO detailOrderDTO = DetailOrderDTO.Mapper.mapFromOrderEntity(order);
        log.info("DetailOrder with id {}: {}", orderId,detailOrderDTO);
        return new ResponseEntity<>(detailOrderDTO, HttpStatus.OK);
    }
    @PostMapping("/cancel/{orderId}")
    public OrderDTO cancel(@PathVariable("orderId") Long orderId) {
        return null;
    }
    @PostMapping("/")

    public ResponseEntity<DetailOrderDTO> create(@RequestBody CreateOrderDTO createOrderDTO) {

        Order savedOrder = orderService.create(createOrderDTO);
        DetailOrderDTO detailOrderDTO = DetailOrderDTO.Mapper.mapFromOrderEntity(savedOrder);

        return new ResponseEntity<>(detailOrderDTO, HttpStatus.OK);
    }
    @PostMapping("/paid/{orderId}")
    public OrderDTO paid(@PathVariable("orderId") Long orderId) {
        return null;
    }
    @PostMapping("/addOrderItem")
    public DetailOrderDTO addOrderItem(@RequestBody AddOrderItemDTO addOrderItemDTO) {

        Order order = orderService.addOrderItem(addOrderItemDTO);

        return DetailOrderDTO.Mapper.mapFromOrderEntity(order);
    }

    @PostMapping("/removeOrderItem")
    public DetailOrderDTO removeOrderItem(@RequestBody RemoveItemDTO removeItemDTO) {

        Order order = orderService.removeOrderItem(removeItemDTO);

        return DetailOrderDTO.Mapper.mapFromOrderEntity(order);
    }
}

/*
* GET http://localhost:8899/api/order/{pageNumber}/pageSize
* GET http://localhost:8899/api/order?pageNumber=0&pageSiz=10
* Tat ca succ: 200
* Exception : 500 : Internal Server Error
* Http.NotFound --> Customer Not Found
* */

