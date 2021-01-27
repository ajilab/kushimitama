package alab.api.app.controller;

import alab.api.app.resource.ErrorResponse;
import alab.api.domain.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        String msg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s: %s",
                        fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining(";"));

        return this.handleExceptionInternal(ex,
                new ErrorResponse("Bad Request", msg, ""),headers, status, request);
    }

    /**
     * 404
     *
     * @param ex      throwされたException
     * @param request the current request
     * @return エラーレスポンス
     */
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handle404(NotFoundException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        ErrorResponse body = new ErrorResponse("Not Found", "", "");
        HttpStatus status = HttpStatus.NOT_FOUND;

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * 500
     *
     * @param ex      throwされたException
     * @param request the current request
     * @return エラーレスポンス
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handle500(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        ErrorResponse body = new ErrorResponse("Internal Server Error", "", "");
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (!(body instanceof ErrorResponse)) {
            body = new ErrorResponse(status.getReasonPhrase(), "", "");
        }
        return new ResponseEntity<>(body, headers, status);
    }
}