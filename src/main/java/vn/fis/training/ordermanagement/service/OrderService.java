package vn.fis.training.ordermanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.fis.training.ordermanagement.dto.*;
import vn.fis.training.ordermanagement.model.Order;

import javax.management.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Page<OrderDTO> findAll(Pageable pageable);
    Page<OrderDTO> findAllPaidOrders(Pageable pageable);

    OrderDTO findById(Long orderId);
    Order addOrderItem(CreateOrderItemDTO createOrderItemDTO);
//    List<Order> ORDER_LIST(QueryOrderDTO queryOrderDTO);
Order create(CreateOrderDTO createOrderDTO);
    void delete(Long orderId);
    Order addOrderItem(AddOrderItemDTO addOrderItemDTO);

    Order removeOrderItem(RemoveItemDTO removeItemDTO);
}
