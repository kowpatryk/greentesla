package com.maracuya.app.common.error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Iterator;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@RestControllerAdvice
public class ErrorHandlingAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        ValidationErrorResponse responseBody = convertToValidationErrorResponse(exception);
        return badRequest().body(responseBody);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception) {
        ValidationErrorResponse responseBody = convertToValidationErrorResponse(exception);
        return badRequest().body(responseBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException() {
        return internalServerError().body(new InternalErrorResponse("Internal error"));
    }

    private ValidationErrorResponse convertToValidationErrorResponse(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
            .map(this::toFieldError)
            .toList();
        return new ValidationErrorResponse(BAD_REQUEST, "Validation failed", fieldErrors);
    }

    private ValidationErrorResponse convertToValidationErrorResponse(ConstraintViolationException exception) {
        List<FieldError> fieldErrors = exception.getConstraintViolations().stream()
            .map(this::toFieldError)
            .toList();
        return new ValidationErrorResponse(BAD_REQUEST, "Validation failed", fieldErrors);
    }

    private FieldError toFieldError(org.springframework.validation.FieldError fieldError) {
        return new FieldError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    private FieldError toFieldError(ConstraintViolation<?> constraintViolation) {
        String field = getLastPathElement(constraintViolation.getPropertyPath());
        return new FieldError(field, constraintViolation.getMessage());
    }

    private String getLastPathElement(Path path) {
        Iterator<Path.Node> iterator = path.iterator();
        if (!iterator.hasNext()) {
            return "";
        }
        Path.Node last = iterator.next();
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        return last.toString();
    }
}
