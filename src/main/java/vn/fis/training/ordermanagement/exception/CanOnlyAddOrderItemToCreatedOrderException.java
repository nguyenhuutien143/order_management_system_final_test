package vn.fis.training.ordermanagement.exception;

public class CanOnlyAddOrderItemToCreatedOrderException extends Exception {
    public CanOnlyAddOrderItemToCreatedOrderException(String message) {
        super(message);
    }
}
