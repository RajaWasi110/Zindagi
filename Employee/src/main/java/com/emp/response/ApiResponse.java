package com.emp.response;

public class ApiResponse<T> {
    private String responseCode;
    private String httpStatus;
    private T payload;  // Generic type for payload
    private String responseMessage;

    public ApiResponse(String responseCode, String httpStatus, T payload, String responseMessage) {
        this.responseCode = responseCode;
        this.httpStatus = httpStatus;
        this.payload = payload;
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
