package at.kolleg.erplite.ordermanagement.api;

import at.kolleg.erplite.ordermanagement.services.exceptions.OrderDataValidationException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderPaymentCheckFailedException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderPlacementNotSuccessfullException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderWithGivenIDNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
class ExceptionRestController {

    @ExceptionHandler(value = OrderPlacementNotSuccessfullException.class)
    public ResponseEntity<ApiErrorResponse> handle(OrderPlacementNotSuccessfullException orderPlacementNotSuccessfullException) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order placement not sucessfull exception ...");
        return ResponseEntity.badRequest().body(new ApiErrorResponse("1000", orderPlacementNotSuccessfullException.getMessage()));
    }

    @ExceptionHandler(value = OrderWithGivenIDNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle(OrderWithGivenIDNotFoundException orderWithGivenIDNotFoundException) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order with given ID not found exception ...");
        return ResponseEntity.badRequest().body(new ApiErrorResponse("2000", orderWithGivenIDNotFoundException.getMessage()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handle(IllegalArgumentException illegalArgumentException) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling illegal argument exception ...");
        return ResponseEntity.badRequest().body(new ApiErrorResponse("3000", illegalArgumentException.getMessage()));
    }

    @ExceptionHandler(value = OrderDataValidationException.class)
    public ResponseEntity<ApiErrorResponse> handle(OrderDataValidationException orderDataValidationException) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order data validation exception ...");
        return ResponseEntity.badRequest().body(new ApiErrorResponse("4000", orderDataValidationException.getMessage()));
    }

    @ExceptionHandler(value = OrderPlacedFieldValidationException.class)
    public ResponseEntity<ApiValidationErrorResponse> handle(OrderPlacedFieldValidationException orderPlacedFieldValidationException) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order placed field validation exception ...");
        return ResponseEntity.badRequest().body(new ApiValidationErrorResponse("5000", orderPlacedFieldValidationException.getValidationErrors()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handle(OrderPaymentCheckFailedException orderPaymentCheckFailedException) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order pacment check failed exception ...");
        return ResponseEntity.badRequest().body(new ApiErrorResponse("6000", orderPaymentCheckFailedException.getMessage()));
    }

}
