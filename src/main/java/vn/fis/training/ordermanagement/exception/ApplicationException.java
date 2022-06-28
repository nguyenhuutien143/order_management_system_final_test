package vn.fis.training.ordermanagement.exception;

public abstract class ApplicationException extends RuntimeException{

    public ApplicationException(String message) {
        super(message);
    }


    public abstract String getErrorCode();
}
