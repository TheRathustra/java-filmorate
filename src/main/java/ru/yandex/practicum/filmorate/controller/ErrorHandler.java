package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.error.ValidationException;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice(assignableTypes = {FilmController.class, UserController.class})
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of("error", "ValidationException", "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoSuchElementException(final NoSuchElementException e) {
        return Map.of("error", "NoSuchElementException", "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleIllegalArgumentException(final IllegalArgumentException e) {
        return Map.of("error", "IllegalArgumentException", "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleThrowableException(final Throwable e) {
        return Map.of("error", "Throwable", "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeException(final RuntimeException e) {
        return Map.of("error", "RuntimeException", "errorMessage", e.getMessage());
    }

}
