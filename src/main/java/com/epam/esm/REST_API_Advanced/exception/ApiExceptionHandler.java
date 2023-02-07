package com.epam.esm.REST_API_Advanced.exception;

import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> catchApiException(ApiException exception,
                                                         WebRequest request) {
        return ResponseEntity
                .status(exception.getException().getStatus())
                .body(ApiResponse.of()
                        .errorCode(exception.getException().getId())
                        .errorName(exception.getException().name())
                        .message(exception.getException().getMessage())
                        .path(request.getDescription(false).substring(4))
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> catchNotValidException(MethodArgumentNotValidException e,
                                                              WebRequest request) {
        String errorMessages = e
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.of()
                        .errorCode(404)
                        .errorName("Not Valid")
                        .message(errorMessages)
                        .path(request.getDescription(false).substring(4))
                        .build());
    }
}
