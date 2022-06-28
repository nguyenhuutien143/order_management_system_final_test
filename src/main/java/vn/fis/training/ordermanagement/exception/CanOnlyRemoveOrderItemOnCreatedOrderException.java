package vn.fis.training.ordermanagement.exception;

public class CanOnlyRemoveOrderItemOnCreatedOrderException extends Exception {
    public CanOnlyRemoveOrderItemOnCreatedOrderException(String message) {
        super(message);
    }
}
