package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adi on 6/13/16.
 */
public class awardsResponse {
    @SerializedName("response")
    awards response;

    public awards getResponse() {
        return response;
    }
}
