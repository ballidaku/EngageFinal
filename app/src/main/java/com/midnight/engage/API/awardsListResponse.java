package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adi on 6/23/16.
 */
public class awardsListResponse {
    @SerializedName("response")
    List<awardsList> response;

    public List<awardsList> getResponse() {
        return response;
    }
}
