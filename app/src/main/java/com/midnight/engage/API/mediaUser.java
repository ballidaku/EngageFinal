package com.midnight.engage.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class mediaUser {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("has_anonymous_profile_picture")
    @Expose
    private Boolean hasAnonymousProfilePicture;
    @SerializedName("is_unpublished")
    @Expose
    private Boolean isUnpublished;
    @SerializedName("profile_pic_url")
    @Expose
    private String profilePicUrl;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("pk")
    @Expose
    private Integer pk;
    @SerializedName("is_verified")
    @Expose
    private Boolean isVerified;
    @SerializedName("is_private")
    @Expose
    private Boolean isPrivate;

    public String getUsername() {
        return username;
    }

    public Boolean getHasAnonymousProfilePicture() {
        return hasAnonymousProfilePicture;
    }

    public Boolean getUnpublished() {
        return isUnpublished;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getPk() {
        return pk;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }
}
