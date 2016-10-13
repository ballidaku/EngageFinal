package com.midnight.engage.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by adi on 6/11/16.
 */
public class appToInstall {

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("pack")
    @Expose
    private String pack;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("money")
    @Expose
    private String money;
    @SerializedName("image")
    @Expose
    private String image;

    public String getLink() {
        return link;
    }

    public String getPack() {
        return pack;
    }

    public String getTitle() {
        return title;
    }

    public String getMoney() {
        return money;
    }

    public String getImage() {
        return image;
    }
}
