package br.com.chain.workflow_processor.exception.handler;

import br.com.chain.workflow_processor.exception.ErrorResponse;
import br.com.chain.workflow_processor.exception.ProfileClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProfileClientException.class)
    public ResponseEntity<ErrorResponse> handleProfileClientException(ProfileClientException ex) {
        // Log the exception details for debugging
        log.error("ProfileClientException caught: {}", ex.getMessage());

        // You can customize the response based on the exception.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())); // Or return a more structured error response
    }

    // Handle other exceptions as needed...
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
        // Log the exception details for debugging
        log.error("WebClientResponseException caught: " + ex.getMessage());

        // You can customize the response based on the exception.
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage()); // Or return a more structured error response
    }
}
