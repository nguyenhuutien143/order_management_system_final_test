package vn.fis.training.ordermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryOrderDTO {
    private Long customerId;
    private double amountGreatThan;
    private LocalDateTime orderDateGreatThan;
}
