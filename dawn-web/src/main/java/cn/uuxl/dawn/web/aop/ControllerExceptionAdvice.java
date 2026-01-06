package cn.uuxl.dawn.web.aop;

import cn.uuxl.dawn.web.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

    @Autowired
    HttpServletRequest httpServletRequest;

    private void logErrorRequest(String reason, Exception ex) {
        // 记录堆栈，方便排查
        logger.error("error_request:url={},reason={}", httpServletRequest.getRequestURI(), reason, ex);
    }

    private ResponseEntity<ApiError> buildError(HttpStatus status, String message, Exception ex) {
        logErrorRequest(status.getReasonPhrase(), ex);
        ApiError apiError = ApiError.of(status.value(), status.getReasonPhrase(), message, httpServletRequest.getRequestURI());
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("failed_valid:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
        }

        return buildError(HttpStatus.BAD_REQUEST, sb.toString(), ex);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handlerRuntimeException(RuntimeException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiError> handlerHttpClientErrorException(HttpClientErrorException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return buildError(status, ex.getMessage(), ex);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handlerResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return buildError(status, ex.getReason(), ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handlerGenericException(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部错误", ex);
    }
}
