package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adi on 6/13/16.
 */
public class awards {
    @SerializedName("awards_count")
    String awards_count;
    @SerializedName("new")
    String nwaward;

    public String getType() {
        return type;
    }

    @SerializedName("type")

    String type;

    public String getNwaward() {
        return nwaward;
    }

    public String getAwards_count() {

        return awards_count;
    }
}
