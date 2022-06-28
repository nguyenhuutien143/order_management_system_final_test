package vn.fis.training.ordermanagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddOrderItemDTO {
    private Long orderId;
    private Long productId;
    private Long quantity;
}
