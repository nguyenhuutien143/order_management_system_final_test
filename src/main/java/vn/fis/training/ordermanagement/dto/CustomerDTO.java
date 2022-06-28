package vn.fis.training.ordermanagement.dto;

import lombok.*;
import vn.fis.training.ordermanagement.model.Customer;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;
    private String name;
    private String mobile;
    private String address;
    public static class Mapper{
        public static CustomerDTO fromEntity(Customer customer){
            return CustomerDTO.builder().id(customer.getId())
                    .address(customer.getAddress())
                    .name(customer.getName())
                    .mobile(customer.getMobile())
                    .build();
        }
        public static Customer fromEntityDTO(CustomerDTO customerDTO){
            return null;
        }
    }
}
