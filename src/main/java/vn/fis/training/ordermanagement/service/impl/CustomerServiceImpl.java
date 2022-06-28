package vn.fis.training.ordermanagement.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.fis.training.ordermanagement.dto.CustomerDTO;
import vn.fis.training.ordermanagement.exception.CustomerNotFoundException;
import vn.fis.training.ordermanagement.model.Customer;
import vn.fis.training.ordermanagement.repository.CustomerRepository;
import vn.fis.training.ordermanagement.service.CustomerService;

import java.util.Optional;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(CustomerDTO.Mapper::fromEntity);
    }

    @Override
    public Customer findById(Long id) {
        Customer customer = customerRepository.findById(id).get();
        if(customer==null) throw new CustomerNotFoundException(String.format("customer not found"));
        return customer;
    }

    @Override
    public Page<CustomerDTO> Create(CustomerDTO customerDTO, Pageable pageable) {
        Customer customer = Customer.builder()
                .name(customerDTO.getName())
                .mobile(customerDTO.getMobile())
                .address(customerDTO.getAddress())
                .build();
        customerRepository.save(customer);
        return findAll(pageable);
    }

    @Override
    public CustomerDTO Update(Long id,CustomerDTO customerDTO) {
        Optional<Customer> customer= customerRepository.findById(id);
        if(customer.isPresent())
        {
            customer.get().setAddress(customerDTO.getAddress());
            customer.get().setMobile(customerDTO.getMobile());
            customer.get().setName(customerDTO.getName());
            customerRepository.save(customer.get());
        }
         throw new CustomerNotFoundException(String.format("customer not found"));
    }

    @Override
    public void Delete(Long id) {
        customerRepository.deleteById(id);
    }
}
