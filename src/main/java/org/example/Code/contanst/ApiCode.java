package org.example.Code.contanst;

import org.springframework.http.HttpStatus;

public enum ApiCode {
    SUCCESS(HttpStatus.OK, "SUCCESS", "Thành công");
    private final HttpStatus status;
    private final String code;
    private final String message;

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    ApiCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
