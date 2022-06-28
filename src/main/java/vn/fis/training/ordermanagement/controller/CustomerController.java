package vn.fis.training.ordermanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import vn.fis.training.ordermanagement.dto.CustomerDTO;
import vn.fis.training.ordermanagement.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

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
    public CustomerDTO findById(@PathVariable(name = "id") Long id){
        return customerService.findById(id);
    }
    @PutMapping("/")
    public CustomerDTO create(@RequestBody CustomerDTO customerDTO){
        customerService.Create(CustomerDTO.Mapper.fromEntityDTO(customerDTO));
        return customerDTO;
    }
}
