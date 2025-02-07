package com.Article.Web.Site.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(InvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidArgumentException(InvalidArgumentException ex, WebRequest request) {

        StackTraceElement[] stackTrace = ex.getStackTrace();
        String errorLocation = "";

        if(stackTrace.length > 0) errorLocation = stackTrace[0].getClassName() + ". "
                + stackTrace[0].getMethodName() + "(): "
                + stackTrace[0].getLineNumber();

        return Map.of(
                "errorDetails", request.getDescription(false),
                "errorStatus", HttpStatus.BAD_REQUEST.value(),
                "errorTimeStamp", LocalDateTime.now(),
                "errorMessage", ex.getMessage(),
                "errorLocation", errorLocation
        );
    }

    @ExceptionHandler(EmptyDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleEmptyDataException(EmptyDataException ex, WebRequest webRequest) {

        StackTraceElement[] stackTrace = ex.getStackTrace();
        String errorLocation = "";

        if(stackTrace.length > 0) errorLocation = stackTrace[0].getClassName() + ". "
                + stackTrace[0].getMethodName() + "(): "
                + stackTrace[0].getLineNumber();

        return Map.of(
                "errorDetails", webRequest.getDescription(false),
                "errorStatus", HttpStatus.NOT_FOUND.value(),
                "errorTimeStamp", LocalDateTime.now(),
                "errorMessage", ex.getMessage(),
                "errorLocation", errorLocation
        );
    }

    @ExceptionHandler(InvalidStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleInvalidStateException(InvalidStateException ex, WebRequest webRequest) {

        StackTraceElement[] stackTrace = ex.getStackTrace();
        String errorLocation = "";

        if(stackTrace.length > 0) errorLocation = stackTrace[0].getClassName() + ". "
                + stackTrace[0].getMethodName() + "(): "
                + stackTrace[0].getLineNumber();

        return Map.of(
                "errorDetails", webRequest.getDescription(false),
                "errorStatus", HttpStatus.CONFLICT.value(),
                "errorTimeStamp", LocalDateTime.now(),
                "errorMessage", ex.getMessage(),
                "errorLocation", errorLocation
        );
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest webRequest) {

        StackTraceElement[] stackTrace = ex.getStackTrace();
        String errorLocation = "";

        if(stackTrace.length > 0) errorLocation = stackTrace[0].getClassName() + ". "
                + stackTrace[0].getMethodName() + "(): "
                + stackTrace[0].getLineNumber();

        return Map.of(
                "errorDetails", webRequest.getDescription(false),
                "errorStatus", HttpStatus.NOT_FOUND.value(),
                "errorTimeStamp", LocalDateTime.now(),
                "errorMessage", ex.getMessage(),
                "errorLocation", errorLocation
        );
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleArticleNotFoundException(ArticleNotFoundException ex, WebRequest webRequest) {

        StackTraceElement[] stackTrace = ex.getStackTrace();
        String errorLocation = "";

        if(stackTrace.length > 0) errorLocation = stackTrace[0].getClassName() + ". "
                + stackTrace[0].getMethodName() + "(): "
                + stackTrace[0].getLineNumber();

        return Map.of(
                "errorDetails", webRequest.getDescription(false),
                "errorStatus", HttpStatus.NOT_FOUND.value(),
                "errorTimeStamp", LocalDateTime.now(),
                "errorMessage", ex.getMessage(),
                "errorLocation", errorLocation
        );
    }

    public Map<String, Object> handleCategoryNotFoundException(CategoryNotFoundException ex, WebRequest webRequest) {

        StackTraceElement[] stackTrace = ex.getStackTrace();
        String errorLocation = "";

        if(stackTrace.length > 0) errorLocation = stackTrace[0].getClassName() + ". "
                + stackTrace[0].getMethodName() + "(): "
                + stackTrace[0].getLineNumber();

        return Map.of(
                "errorDetails", webRequest.getDescription(false),
                "errorStatus", HttpStatus.NOT_FOUND.value(),
                "errorTimeStamp", LocalDateTime.now(),
                "errorMessage", ex.getMessage(),
                "errorLocation", errorLocation
        );
    }
}
