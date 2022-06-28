package vn.fis.training.ordermanagement.exception;


import vn.fis.training.ordermanagement.constant.Constant;
import vn.fis.training.ordermanagement.model.Customer;

public class InvalidCustomerStatusException extends  ApplicationException{
    private final Customer customer;
    public InvalidCustomerStatusException(Customer customer,String message) {
        super(message);
        this.customer = customer;
    }
    @Override
    public String getErrorCode() {
        return Constant.INVALID_STATUS_CUSTOMER;
    }
}
