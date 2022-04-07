package com.example.tangntalk.view;

public class ErrorResponse {
    private boolean success;
    private String code;

    public ErrorResponse(boolean success, String code) {
        this.success = success;
        this.code = code;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

}
