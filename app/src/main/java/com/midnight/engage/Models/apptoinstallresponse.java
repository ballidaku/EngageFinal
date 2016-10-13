package com.midnight.engage.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adi on 6/11/16.
 */
public class apptoinstallresponse {
    @SerializedName("response")
    List<appToInstall> list;

    public List<appToInstall> getList() {
        return list;
    }
}
