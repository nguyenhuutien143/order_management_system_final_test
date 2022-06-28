package vn.fis.training.ordermanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.fis.training.ordermanagement.model.Order;
import vn.fis.training.ordermanagement.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {
    Page<Order> findAllByStatus(OrderStatus orderStatus, Pageable page);
    Page<Order> findAllByOrOrderDateTimeBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
    @EntityGraph(value = "Order.orderItemEntityGraph")
    @Query(value="from Order o where o.id=:orderId")
    Optional<Order> findByIdUsingEntityGraph(@Param(value="orderId") Long orderId);
    @Query(value="select o from Order o join fetch o.orderItems join fetch o.customer where  o.id=:orderId")
    Optional<Order> findByIdUsingJoinFetch(@Param(value="orderId") Long orderId);
}
