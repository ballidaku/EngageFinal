package com.midnight.engage.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public  class mymediaites {

    @SerializedName("taken_at")
    @Expose
    private Integer takenAt;
    @SerializedName("pk")
    @Expose
    private Long pk;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("device_timestamp")
    @Expose
    private String deviceTimestamp;
    @SerializedName("media_type")
    @Expose
    private Integer mediaType;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("client_cache_key")
    @Expose
    private String clientCacheKey;
    @SerializedName("filter_type")
    @Expose
    private Integer filterType;
    @SerializedName("image_versions2")
    @Expose
    private ImageVersions2 imageVersions2;
    @SerializedName("original_width")
    @Expose
    private Integer originalWidth;
    @SerializedName("original_height")
    @Expose
    private Integer originalHeight;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("organic_tracking_token")
    @Expose
    private String organicTrackingToken;
    @SerializedName("like_count")
    @Expose
    private Long likeCount;
    @SerializedName("has_liked")
    @Expose
    private Boolean hasLiked;
    @SerializedName("has_more_comments")
    @Expose
    private Boolean hasMoreComments;
    @SerializedName("max_num_visible_preview_comments")
    @Expose
    private Integer maxNumVisiblePreviewComments;
    @SerializedName("comments")
    @Expose
    private List<Object> comments = new ArrayList<Object>();
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("caption")
    @Expose
    private Object caption;
    @SerializedName("caption_is_edited")
    @Expose
    private Boolean captionIsEdited;
    @SerializedName("photo_of_you")
    @Expose
    private Boolean photoOfYou;
    @SerializedName("video_versions")
    @Expose
    private List<VideoVersion> videoVersions = new ArrayList<VideoVersion>();
    @SerializedName("has_audio")
    @Expose
    private Boolean hasAudio;

    public Integer getTakenAt() {
        return takenAt;
    }

    public Long getPk() {
        return pk;
    }

    public String getId() {
        return id;
    }

    public String getDeviceTimestamp() {
        return deviceTimestamp;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public String getCode() {
        return code;
    }

    public String getClientCacheKey() {
        return clientCacheKey;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public ImageVersions2 getImageVersions2() {
        return imageVersions2;
    }

    public Integer getOriginalWidth() {
        return originalWidth;
    }

    public Integer getOriginalHeight() {
        return originalHeight;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public User getUser() {
        return user;
    }

    public String getOrganicTrackingToken() {
        return organicTrackingToken;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public Boolean getHasLiked() {
        return hasLiked;
    }

    public Boolean getHasMoreComments() {
        return hasMoreComments;
    }

    public Integer getMaxNumVisiblePreviewComments() {
        return maxNumVisiblePreviewComments;
    }

    public List<Object> getComments() {
        return comments;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Object getCaption() {
        return caption;
    }

    public Boolean getCaptionIsEdited() {
        return captionIsEdited;
    }

    public Boolean getPhotoOfYou() {
        return photoOfYou;
    }

    public List<VideoVersion> getVideoVersions() {
        return videoVersions;
    }

    public Boolean getHasAudio() {
        return hasAudio;
    }
}
