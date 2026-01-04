package cn.uuxl.dawn.http.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

    @Autowired
    HttpServletRequest httpServletRequest;

    private void logErrorRequest(Exception ex) {
        logger.error("error_request:url={},msg={}", httpServletRequest.getRequestURI(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logErrorRequest(ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("failed_valid:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
        }

        return ResponseEntity.badRequest().body(sb.toString());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handlerRuntimeException(RuntimeException ex) {
        logErrorRequest(ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handlerHttpClientErrorException(HttpClientErrorException ex) {
        logErrorRequest(ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
