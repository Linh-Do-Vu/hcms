package com.example.ezhcm.controller.exceptionhander;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice(value = "com.example.ezhcm.controller")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> handleSelfServiceException(CustomException e, WebRequest webRequest) {
   ErrorDetails errorResponse = buildErrorResponse(
                e.getHttpStatus(),
                e.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    private ErrorDetails buildErrorResponse(String httpStatus, String errorMessage, String details) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode(httpStatus);
        errorDetails.setTimestamp(new Date());
        errorDetails.setMessage(errorMessage);
        errorDetails.setDetails(details);
        return errorDetails;
    }

}
