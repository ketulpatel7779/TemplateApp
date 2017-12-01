package com.mastertemplate.data.api.model;

public class UploadResponse {


    /**
     * status : true
     * message : Live uploads
     * user_details : {"user_id":"80","username":"harshang305","update_id":473}
     */

    private boolean status;
    private String message;
    private UserDetailsBean user_details;

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

    public UserDetailsBean getUser_details() {
        return user_details;
    }

    public void setUser_details(UserDetailsBean user_details) {
        this.user_details = user_details;
    }

    public static class UserDetailsBean {
    }
}