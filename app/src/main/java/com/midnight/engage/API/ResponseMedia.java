package com.midnight.engage.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public  class ResponseMedia {
    @SerializedName("items")
    @Expose
    List<mymediaites> items = new ArrayList<>();

    public ResponseMedia(List<mymediaites> items, String next_max_id) {
        this.items = items;
        this.next_max_id = next_max_id;
    }

    @SerializedName("next_max_id")

    String next_max_id;

    public String getNext_max_id() {
        return next_max_id;
    }

    public void setItems(List<mymediaites> items) {
        this.items = items;
    }

    public List<mymediaites> getItems() {
        return items;
    }
}
