package vn.fis.training.ordermanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.fis.training.ordermanagement.exception.ErrorMessage;
import vn.fis.training.ordermanagement.exception.OrderItemNotFoundException;
import vn.fis.training.ordermanagement.exception.OrderNotFoundException;
import vn.fis.training.ordermanagement.exception.ProductNotEnoughAmountException;

import static vn.fis.training.ordermanagement.constant.Constant.*;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            IllegalArgumentException.class
    })
    protected ResponseEntity<ErrorMessage> handleOrderNotFoundException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.builder().code(ORDER_NOT_FOUND).message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = {
            Exception.class
    })
    protected ResponseEntity<ErrorMessage> handleSystemError(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder().code(INTERNAL_SERVER_ERROR).message(exception.getMessage()).build());
    }
    @ExceptionHandler(value = {
            OrderItemNotFoundException.class
    })
    protected ResponseEntity<ErrorMessage> handlerOrderItemNotFoundException(OrderItemNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder().code(ORDER_ITEM_NOT_FOUND).message(e.getMessage()).build());
    }
    @ExceptionHandler(value = {
            OrderNotFoundException.class
    })
    protected ResponseEntity<ErrorMessage> handlerOrderItemNotFoundException(OrderNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder().code(ORDER_NOT_FOUND).message(e.getMessage()).build());
    }
    @ExceptionHandler(value = {
            OrderNotFoundException.class
    })
    protected ResponseEntity<ErrorMessage> handlerProductNotFoundException(OrderNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder().code(PRODUCT_NOT_FOUND).message(e.getMessage()).build());
    }
    @ExceptionHandler(value = {
            ProductNotEnoughAmountException.class
    })
    protected ResponseEntity<ErrorMessage> handlerProductNotFoundException(ProductNotEnoughAmountException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder().code(PRODUCT_NOT_ENOUGH_AMOUNT).message(e.getMessage()).build());
    }

}
