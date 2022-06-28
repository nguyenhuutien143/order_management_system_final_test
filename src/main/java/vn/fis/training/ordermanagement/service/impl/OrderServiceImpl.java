package vn.fis.training.ordermanagement.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.fis.training.ordermanagement.dto.*;
import vn.fis.training.ordermanagement.model.Order;
import vn.fis.training.ordermanagement.model.OrderItem;
import vn.fis.training.ordermanagement.model.OrderStatus;
import vn.fis.training.ordermanagement.model.Product;
import vn.fis.training.ordermanagement.repository.OrderRepository;
import vn.fis.training.ordermanagement.repository.ProductRepository;
import vn.fis.training.ordermanagement.service.OrderService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.info("Query all Order. PageNumber: {}, PageSize: {}", pageable.getPageNumber(), pageable.getPageSize());
        return orderRepository.findAll(pageable).map(OrderDTO.Mapper::fromEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDTO> findAllPaidOrders(Pageable pageable) {
        return orderRepository.findAllByStatus(OrderStatus.PAID, pageable).map(OrderDTO.Mapper::fromEntity);
    }

    @Override
    public OrderDTO findById(Long orderId) {
        return OrderDTO.Mapper.fromEntity(orderRepository.findById(orderId).orElseThrow(
                () -> {throw new IllegalArgumentException(String.format("Not found order with id %s",orderId));}));
    }
    @Override
    public Order addOrderItem(CreateOrderItemDTO createOrderItemDTO) {
        Optional<Order> order = orderRepository.findById(createOrderItemDTO.getOrderId());
        Optional<Product> p= productRepository.findById(createOrderItemDTO.getProductId());
        OrderItem orderItem= new OrderItem();
        orderItem.setOrder(order.get());
        orderItem.setProduct(p.get());
        orderItem.setQuantity(createOrderItemDTO.getQuantity());
        orderItem.setAmount( p.get().getPrice()*createOrderItemDTO.getQuantity());

        if(!order.isPresent()|| !p.isPresent()){
            throw new IllegalArgumentException("not found product or order");
        }
        order.get().getOrderItems().add(orderItem);
        order.get().setTotalAmount(order.get().getOrderItems().stream().mapToDouble(orderItem1->orderItem1.getAmount()).sum());
        //order.get().setTotalAmount(order.get().getTotalAmount()+p.get().getPrice()*createOrderItemDTO.getQuantity());
        //orderRepository.save(order.get());
        return order.get();
    }

    @Override
    public Order create(CreateOrderDTO createOrderDTO) {
        return null;
    }

    @Override
    public void delete(Long orderId) {

    }

    @Override
    public Order addOrderItem(AddOrderItemDTO addOrderItemDTO) {
        return null;
    }

    @Override
    public Order removeOrderItem(RemoveItemDTO removeItemDTO) {
        return null;
    }


//    @Override
//    public List<Order> ORDER_LIST(QueryOrderDTO queryOrderDTO) {
//        StringBuffer sql=new StringBuffer("select o from Order o");
//        if(queryOrderDTO.getCustomerId()!=null) sql.append("where id = ").append(queryOrderDTO.getCustomerId());
//        if (queryOrderDTO.getAmountGreatThan()!=0 ) sql.append("and o.amount greater ").append(queryOrderDTO.getAmountGreatThan());
//        if(queryOrderDTO.getOrderDateGreatThan()!=null) sql.append("and o.order_datetime greater").append(queryOrderDTO.getOrderDateGreatThan());
//        return entityManager.createQuery(sql.toString()).getResultList();
//    }
}
