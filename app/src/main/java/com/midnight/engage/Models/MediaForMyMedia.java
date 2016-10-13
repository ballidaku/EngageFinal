package com.midnight.engage.Models;

/**
 * Created by adi on 6/3/16.
 */
public class MediaForMyMedia {
    String imageUrl;
    String imageId;
    Long currentLikes;

    public MediaForMyMedia(String imageUrl, String imageId, Long currentLikes) {
        this.imageUrl = imageUrl;
        this.imageId = imageId;
        this.currentLikes = currentLikes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public Long getCurrentLikes() {
        return currentLikes;
    }
}
