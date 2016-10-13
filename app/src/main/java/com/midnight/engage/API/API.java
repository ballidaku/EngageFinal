package com.midnight.engage.API;




import com.midnight.engage.Models.apptoinstallresponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by adi on 6/2/16.
 */
public interface API {
    @FormUrlEncoded
    @POST("index.php?mode=instantLikes")
    Call<Void> addInstLike(@Field("data") String data );
    @FormUrlEncoded
    @POST("index.php?mode=instantFollowers")
    Call<Void> addInstFollow(@Field("data") String data );

    @POST("apps-android.php")
    Call<apptoinstallresponse> appToLike();
    @FormUrlEncoded
    @POST("review.php")
    Call<inReview> inreview(@Field("api_key") String api_key);
    @FormUrlEncoded
    @POST("index.php?mode=checkAwards")
    Call<awardsResponse> dawards(@Field("data") String data);
    @FormUrlEncoded
    @POST("index.php?mode=addCampaign")
    Call<Void> addCampaign(@Field("data") String data);
    @FormUrlEncoded
    @POST("index.php?mode=addAction")
    Call<Void> addAction(@Field("data") String data);
    @FormUrlEncoded
    @POST("index.php?mode=addMoney")
    Call<Void> addMoney(@Field("data") String data);
    @FormUrlEncoded
    @POST("index.php?mode=needToLike")
    Call<toLikelvl1> toLike(@Field("data") String data) ;
    @FormUrlEncoded
    @POST("index.php?mode=users")
    Call<UserResponse> getUser(@Field("data") String data) ;

    @FormUrlEncoded
    @POST("index.php?mode=checkUnFollowers")
    Call<unfollowResponeLvl1> unfollow(@Field("data") String data);


}
