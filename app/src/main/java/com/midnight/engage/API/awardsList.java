package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adi on 6/23/16.
 */
public class awardsList {
    @SerializedName("id")
    String id;
    @SerializedName("day")
    String day;
    @SerializedName("type")
    String type;
    @SerializedName("amound")
    String amound;
    @SerializedName("used")
    String used;

    public String getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getType() {
        return type;
    }

    public String getAmound() {
        return amound;
    }

    public String getUsed() {
        return used;
    }
}
