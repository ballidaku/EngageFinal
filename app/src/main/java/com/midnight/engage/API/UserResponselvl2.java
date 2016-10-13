package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adi on 6/5/16.
 */
public class UserResponselvl2 {

    @SerializedName("money")
    String money;
    @SerializedName("followers")
    String followers;
    @SerializedName("likes")
    String likes;

    public String getFollowers() {
        return followers;
    }

    public String getLikes() {
        return likes;
    }

    public String getMoney() {
        return money;
    }
}
