package vn.fis.training.ordermanagement.dto;

import lombok.*;
import vn.fis.training.ordermanagement.model.Order;
import vn.fis.training.ordermanagement.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DetailOrderDTO {
    private Long id;

    private LocalDateTime orderDateTime;

    private CustomerDTO customerDTO;

    private Double totalAmount;

    private List<OrderItemDTO> orderItemDTOs;

    private OrderStatus status;

    public static class Mapper {
        public static DetailOrderDTO mapFromOrderEntity(Order order) {
            return DetailOrderDTO.builder()
                    .id(order.getId())
                    .orderDateTime(order.getOrderDateTime())
                    .customerDTO(CustomerDTO.Mapper.fromEntity(order.getCustomer()))
                    .totalAmount(order.getTotalAmount())
                    .orderItemDTOs(order.getOrderItems().stream()
                            .map(OrderItemDTO.Mapper::mapFromOrderItemEntity)
                            .collect(Collectors.toList()))
                    .status(order.getStatus())
                    .build();
        }
    }
}
