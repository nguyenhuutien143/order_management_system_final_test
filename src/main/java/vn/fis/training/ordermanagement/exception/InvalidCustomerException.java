package vn.fis.training.ordermanagement.exception;


import vn.fis.training.ordermanagement.model.Customer;

import static vn.fis.training.ordermanagement.constant.Constant.INVALID_CUSTOMER;

public class InvalidCustomerException extends ApplicationException{

    private Customer customer;

    public InvalidCustomerException(Customer customer, String message) {
        super(message);
        this.customer = customer;
    }

    @Override
    public String getErrorCode() {
        return INVALID_CUSTOMER;
    }

    public Customer getCustomer() {
        return this.customer;
    }
}
