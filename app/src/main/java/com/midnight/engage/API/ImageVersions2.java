package com.midnight.engage.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ImageVersions2 {

    @SerializedName("candidates")
    @Expose
    private List<Candidate> candidates = new ArrayList<Candidate>();

    public List<Candidate> getCandidates() {
        return candidates;
    }
}
