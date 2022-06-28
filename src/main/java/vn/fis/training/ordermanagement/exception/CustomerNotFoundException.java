package vn.fis.training.ordermanagement.exception;


import vn.fis.training.ordermanagement.constant.Constant;

public class CustomerNotFoundException extends ApplicationException{
    public CustomerNotFoundException(String message) {
        super(message);
    }
    @Override
    public String getErrorCode() {
        return Constant.CUSTOMER_NOT_FOUND;
    }
}
