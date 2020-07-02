package ro.utcn.sd.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.utcn.sd.dto.responses.ResponseFactory;

/**
 * This class uses a Controller Advice in order to handle all the predefined exceptions.
 * It returns a Response Entity with negative response, according to the exception
 */
@ControllerAdvice
public class ExceptionControl extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseFactory responseFactory;

    @ExceptionHandler(ProducerAlreadyExistsException.class)
    public ResponseEntity handleProducerAlreadyExistsException(ProducerAlreadyExistsException ex, WebRequest request){
        return responseFactory.generateResponse("error", "Producer. already exists!", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity handleIncorrectCredentialsException(IncorrectCredentialsException ex, WebRequest request){
        return responseFactory.generateResponse("error", "Incorrect credentials!", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity handleInvalidProductException(InvalidProductException ex, WebRequest request){
        return responseFactory.generateResponse("error", "Some fields are incorrectly introduced!", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(InexistentProductException.class)
    public ResponseEntity handleInexistentProductException(InexistentProductException ex, WebRequest request){
        return responseFactory.generateResponse("warning", "Cannot find this product", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(NotEnoughProductsException.class)
    public ResponseEntity handleNotEnoughProductsException(NotEnoughProductsException ex, WebRequest request){
        return responseFactory.generateResponse("warning", "Not enough quantity available", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity handleUsernameAlreadyInUseException(UsernameAlreadyInUseException ex, WebRequest request){
        return responseFactory.generateResponse("error", "Username already in use!", HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity handleEmailAlreadyInUseException(EmailAlreadyInUseException ex, WebRequest request){
        return responseFactory.generateResponse("warning", "Email already associated with an account!", HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler(PasswordTooSmallException.class)
    public ResponseEntity handlePasswordTooSmallException(PasswordTooSmallException ex, WebRequest request){
        return responseFactory.generateResponse("info", "Password mush have at least 8 characters!", HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity handleEmptyCartException(EmptyCartException ex, WebRequest request){
        return responseFactory.generateResponse("warning", "You cannot place an order with no elements!", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(IncompatibleDatesException.class)
    public ResponseEntity handleIncompatibleDatesException(IncompatibleDatesException ex, WebRequest request){
        return responseFactory.generateResponse("warning", "The inserted dates are incorrect!", HttpStatus.BAD_REQUEST, null);
    }
}
