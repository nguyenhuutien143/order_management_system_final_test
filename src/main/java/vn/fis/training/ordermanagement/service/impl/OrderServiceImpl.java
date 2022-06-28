package vn.fis.training.ordermanagement.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.fis.training.ordermanagement.dto.*;
import vn.fis.training.ordermanagement.exception.*;
import vn.fis.training.ordermanagement.model.*;
import vn.fis.training.ordermanagement.repository.OrderItemRepository;
import vn.fis.training.ordermanagement.repository.OrderRepository;
import vn.fis.training.ordermanagement.repository.ProductRepository;
import vn.fis.training.ordermanagement.service.CustomerService;
import vn.fis.training.ordermanagement.service.OrderService;
import vn.fis.training.ordermanagement.service.ProductService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    private ProductService productService;
    private CustomerService customerService;
    private OrderItemRepository orderItemRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            ProductService productService,CustomerService customerService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.customerService= customerService;
        this.orderItemRepository =orderItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {

        return orderRepository.findAll(pageable).map(OrderDTO.Mapper::fromEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDTO> findAllPaidOrders(Pageable pageable) {
        return orderRepository.findAllByStatus(OrderStatus.PAID, pageable).map(OrderDTO.Mapper::fromEntity);
    }

    @Override
    public Order findById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            try {
                throw new OrderNotFoundException(String.format("Not found order with id %s", orderId));
            } catch (OrderNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return order;
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
        Customer customer = customerService.findById(createOrderDTO.getCustomerId());

        List<OrderItem> orderItemList = new ArrayList<>();
    createOrderDTO.getOrderItemInfo().forEach(productQuantityDTO ->{
        if(productService.findById(productQuantityDTO.getProductId()).getAvaiable()<productQuantityDTO.getQuantity()){
            try {
                throw  new ProductNotEnoughAmountException(String.format("not enough product"));
            } catch (ProductNotEnoughAmountException e) {
                throw new RuntimeException(e);
            }
        }
        Product product= productService.findById(productQuantityDTO.getProductId());
        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .quantity(productQuantityDTO.getQuantity())
                .amount(product.getPrice() * productQuantityDTO.getQuantity())
                .build();
        orderItemList.add(orderItem);

    } );
        Order order = Order.builder()
                .orderDateTime(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .customer(customer)
                .orderItems(orderItemList)
                .totalAmount(orderItemList.stream().mapToDouble(OrderItem::getAmount).sum())
                .build();
        orderRepository.save(order);
        return order;
    }

    @Override
    public void delete(Long id) {
        Order order = findById(id);
        if (OrderStatus.CREATED.equals(order.getStatus())) {
            try {
                throw new CanNotDeleteCreatedStatusOrderException(
                        "Can not delete Order has status is CREATED!");
            } catch (CanNotDeleteCreatedStatusOrderException e) {
                throw new RuntimeException(e);
            }
        }
        if (OrderStatus.CANCELLED.equals(order.getStatus())) {
            try {
                throw new CanNotDeleteCancelledStatusOrderException(
                        "Can not delete Order has status is CANCELLED!");
            } catch (CanNotDeleteCancelledStatusOrderException e) {
                throw new RuntimeException(e);
            }
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Order addOrderItem(AddOrderItemDTO addOrderItemDTO) {
        Order order = findById(addOrderItemDTO.getOrderId());
        if (!OrderStatus.CREATED.equals(order.getStatus())) {
            try {
                throw new CanOnlyAddOrderItemToCreatedOrderException("Can only add order item to order has status is CREATED!");
            } catch (CanOnlyAddOrderItemToCreatedOrderException e) {
                throw new RuntimeException(e);
            }
        }
        Product product = productService.findById(addOrderItemDTO.getProductId());
        if (product.getAvaiable() < addOrderItemDTO.getQuantity()) {
            try {
                throw new ProductNotEnoughAmountException(String.format("Product %s Not Enought !!", product.getName()));
            } catch (ProductNotEnoughAmountException e) {
                throw new RuntimeException(e);
            }
        }
        OrderItem newOrderItem = OrderItem.builder()
                .amount(product.getPrice() * addOrderItemDTO.getQuantity())
                .quantity(addOrderItemDTO.getQuantity())
                .order(order)
                .product(product)
                .build();
        order.setTotalAmount(order.getTotalAmount() + newOrderItem.getAmount());
        order.getOrderItems().add(newOrderItem);
        orderRepository.save(order);
        product.setAvaiable(product.getAvaiable() - addOrderItemDTO.getQuantity());
        productService.update(product);
        return findById(addOrderItemDTO.getOrderId());}


    @Override
    public Order removeOrderItem(RemoveItemDTO removeItemDTO) {
        Order order = findById(removeItemDTO.getOrderId());
        if (!OrderStatus.CREATED.equals(order.getStatus())) {
            try {
                throw new CanOnlyRemoveOrderItemOnCreatedOrderException(
                        "Can only remove order item on order has status is CREATED!");
            } catch (CanOnlyRemoveOrderItemOnCreatedOrderException e) {
                throw new RuntimeException(e);
            }
        }
        OrderItem orderItem = orderItemRepository.findById(removeItemDTO.getOrderItemId()).orElseThrow(() -> {
            try {
                throw new OrderItemNotFoundException(String.format("Not Found Order Item With id %s",
                        removeItemDTO.getOrderItemId()));
            } catch (OrderItemNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        List<OrderItem> orderItems = orderRepository.findById(removeItemDTO.getOrderItemId()).get().getOrderItems();
        order.setTotalAmount(order.getTotalAmount() - orderItem.getAmount());
        Product product = productService.findById(orderItem.getProduct().getId());
        product.setAvaiable(product.getAvaiable() + orderItem.getQuantity());
        orderRepository.deleteById(removeItemDTO.getOrderItemId());
        return findById(removeItemDTO.getOrderId());
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
