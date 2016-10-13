package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("response")
    private Response response;
    @SerializedName("error")
    private String error;

    public Response getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}




