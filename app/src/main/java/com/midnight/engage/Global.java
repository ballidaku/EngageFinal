package com.midnight.engage;

import android.app.Application;


import com.midnight.engage.API.User;
import com.midnight.engage.Adapters.BuyObject;
import com.midnight.engage.Models.MediaForMyMedia;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by adi on 6/2/16.
 */
public class Global extends Application {
    String password;
    User currentUser;
    String money = "0";
    Boolean follow;
    MediaForMyMedia toLike;
    Integer perLike = 2;
    Integer getCoinsPerLike = 2;
    String emailcontactus = "";
    String subjectcontactus = "";

    boolean inapps = true;
    boolean webView = false;


    public String getLink() {
        return link;
    }

    String link = "http://midnight.works/appstore/engage_android/fake_insta/";


    public static native String HelloJNI();
    static {
        System.loadLibrary("HelloJNI");
    }





    public boolean isWebView() {
        return webView;
    }

    public Integer getFubershow() {
        return fubershow;
    }

    Integer fubershow = 5;
    public String getEmailcontactus() {
        return emailcontactus;
    }

    public String getSubjectcontactus() {
        return subjectcontactus;
    }

    String moreappslink = "http://midnight.works/android/moreapps";

    public String getMoreappslink() {
        return moreappslink;
    }

    public Integer getGetCoinsPerLike() {
        return getCoinsPerLike;
    }


    public Boolean isGoogleInApps() { return inapps; }
    public String getToBuy() {
        return toBuy;
    }

    public void setToBuy(String toBuy) {
        this.toBuy = toBuy;
    }

    String toBuy;

    public List<BuyObject> getBuyLikesList() {
        List<BuyObject> list = new ArrayList<>();
        list.add(new BuyObject(100 , "2" , "brst_like_100"));
        list.add(new BuyObject(500, "4" , "brst_like_500"));
        list.add(new BuyObject(1000 , "8" , "brst_like_1000"));
        list.add(new BuyObject(5000, "40" ,"brst_like_5000" ));
        list.add(new BuyObject(10000 , "70","brst_like_10000"));

        return list;
    }
    public List<BuyObject> getBuyCoinsList() {
        List<BuyObject> list = new ArrayList<>();
        list.add(new BuyObject(50 , "2" , "brst_coin_50"));
        list.add(new BuyObject(200, "4" , "brst_coin_200"));
        list.add(new BuyObject(500 , "8" , "brst_coin_500"));
        list.add(new BuyObject(900, "40" , "brst_coin_900"));
        list.add(new BuyObject(3200, "70" , "brst_coin_3200"));
        list.add(new BuyObject(6000 , "79.99" , "brst_coin_6000"));

        return list;
    }
    public List<BuyObject> getBuyFollowList() {
        List<BuyObject> list = new ArrayList<>();
        list.add(new BuyObject(100 , "2" , "brst_follow_100"));
        list.add(new BuyObject(500, "4" , "brst_follow_500"));
        list.add(new BuyObject(1000 , "5.99" , "brst_follow_1000"));
        list.add(new BuyObject(5000, "8.99" ,"brst_follow_5000" ));
        list.add(new BuyObject(10000 , "20.99","brst_follow_10000"));

        return list;
    }

    public Integer getPerLike() {
        return perLike;
    }

    public MediaForMyMedia getToLike() {
        return toLike;
    }

    public void setToLike(MediaForMyMedia toLike) {
        this.toLike = toLike;
    }

    public Boolean getFollow() {
        return follow;
    }

    public void setFollow(Boolean follow) {
        this.follow = follow;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
