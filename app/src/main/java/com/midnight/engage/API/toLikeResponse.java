package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adi on 6/3/16.
 */
public class toLikeResponse {
    @SerializedName("id")
    Integer idd;
    @SerializedName("money")
    String money;
    @SerializedName("user_id")
    String unserId;
    @SerializedName("type")
    String type;
    @SerializedName("media_video")
    String media_video;
    @SerializedName("media_image")
    String image;
    @SerializedName("media_id")
    String id;
    @SerializedName("username")
    String username;

    public Integer getIdd() {
        return idd;
    }

    public String getMoney() {
        return money;
    }

    public String getUnserId() {
        return unserId;
    }

    public String getType() {
        return type;
    }

    public String getMedia_video() {
        return media_video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
