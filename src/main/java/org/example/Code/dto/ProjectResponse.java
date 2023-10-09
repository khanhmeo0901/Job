package org.example.Code.dto;

import org.example.Code.contanst.ApiCode;

public class ProjectResponse<T> {
    private String status;
    private String code;
    private String message;
    private T data;

    public ProjectResponse(T data) {
        this.status = "OK";
        this.code = "200";
        this.message = "SUCCESS";
        this.data = data;
    }

    public ProjectResponse() {
        this.status = "OK";
        this.code = "200";
        this.message = "SUCCESS";
    }

    public ProjectResponse(ApiCode apiCode) {
        this.status = apiCode.getStatus().name();
        this.code = apiCode.getCode();
        this.message = apiCode.getMessage();
    }

    public ProjectResponse(ApiCode apiCode, T data) {
        this.status = apiCode.getStatus().name();
        this.code = apiCode.getCode();
        this.message = apiCode.getMessage();
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
