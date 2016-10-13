package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adi on 7/6/16.
 */
public class BuyResponseList {
    @SerializedName("response")
    List<BuyResponse> responses;

    public List<BuyResponse> getResponses() {
        return responses;
    }
}
