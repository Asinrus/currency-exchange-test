package com.exchange.app.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler  extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            HttpClientErrorException  ex) {
        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(
                LocalDateTime.now(),
                ex.getMessage() ,
                details);
        return ResponseEntity.status(ex.getStatusCode()).body(err);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class ApiError {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private LocalDateTime timestamp;
        private String message;
        private List<String> errors;
    }
}
