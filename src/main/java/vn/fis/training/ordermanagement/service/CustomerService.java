package vn.fis.training.ordermanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.fis.training.ordermanagement.dto.CustomerDTO;
import vn.fis.training.ordermanagement.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Page<CustomerDTO> findAll(Pageable pageable);
    Customer findById(Long id);
    Page<CustomerDTO> Create(CustomerDTO customerDTO, Pageable pageable );
    CustomerDTO Update(Long id, CustomerDTO customerDTO);
    void Delete(Long id);
}
