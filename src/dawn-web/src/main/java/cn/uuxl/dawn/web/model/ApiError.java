package cn.uuxl.dawn.web.model;

import java.time.Instant;

/**
 * 简单的错误响应对象，便于前端统一处理。
 */
public class ApiError {
    private final long timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    private ApiError(int status, String error, String message, String path) {
        this.timestamp = Instant.now().toEpochMilli();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(status, error, message, path);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
