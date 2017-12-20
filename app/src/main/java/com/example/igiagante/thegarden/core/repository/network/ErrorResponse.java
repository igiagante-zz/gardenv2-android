package com.example.igiagante.thegarden.core.repository.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ignacio Giagante, on 22/4/16.
 */

public class ErrorResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("errorType")
    private String errorType;

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("errorMessage")
    private String errorMessage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
