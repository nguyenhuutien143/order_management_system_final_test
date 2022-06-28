package vn.fis.training.ordermanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fis.training.ordermanagement.dto.CustomerDTO;
import vn.fis.training.ordermanagement.model.Customer;
import vn.fis.training.ordermanagement.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private CustomerService customerService;
    private ModelMapper modelMapper;
    @Autowired

    public CustomerController(CustomerService customerService,ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper=modelMapper;
    }
    @GetMapping("/")
    public Page<CustomerDTO> findAll(){
        return customerService.findAll(PageRequest.of(1,1));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable(name = "id") Long id){
        Customer customer = customerService.findById(id);
        CustomerDTO customerDTO = CustomerDTO.Mapper.fromEntity(customer);

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable("id") Long id,
                                              @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.Update(id, customerDTO);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
}
