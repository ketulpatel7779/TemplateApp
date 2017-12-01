package com.mastertemplate.data.api.model;

import com.google.gson.Gson;

public class LoginResponse {


    /**
     * status : false
     * message : Invalid email address or password.
     * data :
     */

    private boolean status;
    private String message;
    private String data;

    public static LoginResponse JSONtoObject(String JSON) {

        return new Gson().fromJson(JSON, LoginResponse.class);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}