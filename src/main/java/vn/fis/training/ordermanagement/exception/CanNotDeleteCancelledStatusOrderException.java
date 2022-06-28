package vn.fis.training.ordermanagement.exception;

public class CanNotDeleteCancelledStatusOrderException extends Throwable {
    public CanNotDeleteCancelledStatusOrderException(String message) {
        super(message);
    }
}
