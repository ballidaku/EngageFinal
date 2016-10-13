package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adi on 6/13/16.
 */
public class unfollowResponse {
    @SerializedName("coins")
    String cois;
    @SerializedName("object")
    String object;

    public String getCois() {
        return cois;
    }

    public String getObject() {
        return object;
    }
}
