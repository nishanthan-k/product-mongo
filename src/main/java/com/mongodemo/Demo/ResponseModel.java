package com.mongodemo.Demo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel<T> {
    private ResponseStatus status;
    private String message;
    private T data;

    public ResponseModel(ResponseStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
