package com.example.ajit.imageupload.model;

/**
 * Created by Ajit on 19-12-2017.
 */

public class SucessModel {

    /**
     * success : true
     * status_code : 200
     * message : success
     */

    private String success;
    private int status_code;
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
