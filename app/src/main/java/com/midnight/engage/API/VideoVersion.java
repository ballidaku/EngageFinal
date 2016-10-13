package com.midnight.engage.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoVersion {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("height")
    @Expose
    private Integer height;

    public String getUrl() {
        return url;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getType() {
        return type;
    }

    public Integer getHeight() {
        return height;
    }
}
