package vn.fis.training.ordermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderItemDTO {
    private Long orderId;
    private Long productId;
    private Long quantity;
}
