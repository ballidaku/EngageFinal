package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adi on 6/5/16.
 */
public class toLikelvl1 {
    public List<toLikeResponse> getLikeResponses() {
        return likeResponses;
    }

    @SerializedName("response")

    List<toLikeResponse> likeResponses;

}
