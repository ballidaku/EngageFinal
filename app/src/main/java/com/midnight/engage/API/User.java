package com.midnight.engage.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("follower_count")
    @Expose
    private Integer followerCount;
    @SerializedName("pk")
    @Expose
    private Long pk;

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_pic_url")
    @Expose
    private String profilePicUrl;



    String pub;

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public User(Integer followerCount, Long pk, String username, String profilePicUrl) {
        this.followerCount = followerCount;
        this.pk = pk;
        this.username = username;
        this.profilePicUrl = profilePicUrl;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }
    void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public Long getPk() {
        return pk;
    }


    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }


}
