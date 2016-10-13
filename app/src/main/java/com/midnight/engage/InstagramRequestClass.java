package com.midnight.engage;

import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import com.loopj.android.http.*;
import com.midnight.engage.API.User;
import com.midnight.engage.Dao.Users;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cz.msebera.android.httpclient.Header;

/**
 * Created by tolea on 29.09.16.
 */

public class InstagramRequestClass {
    private static InstagramRequestClass instance = null;

    private static final String BASE_URL = "https://i.instagram.com/api/v1/";

    public static InstagramRequestClass getInstance() {
        if (instance == null) {
            instance = new InstagramRequestClass();
        }
        return instance;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private String GenerateSignature(String value, String key)
    {
        String result = null;
        try {
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(value.getBytes());
            byte[] hexBytes = new Hex().encode(rawHmac);
            result = new String(hexBytes, "UTF-8");
        }
        catch (Exception e) {

        }
        return result;
    }


    public void Login(final String username , final String password, final AsyncHttpClient client, final MasterActivity obj, final int option, Users item, JsonHttpResponseHandler handler) throws UnsupportedEncodingException, ParseException
    {
        String Guid = java.util.UUID.randomUUID().toString();
        String DeviceId = "android-" + Guid;
        String Data = "{\"device_id\":\""  +  DeviceId + "\",\"username\":\""+username+"\",\"password\":\""+password+"\",\"_csrftoken\":\"ZMhyU8cztJDMhgg63RUVUUHwV6T1sTuF\",\"login_attempt_count\":\"0\"}";

        String Sig = GenerateSignature(Data, "55e91155636eaa89ba5ed619eb4645a4daf1103f2161dbfe6fd94d5ea7716095");
        Data  = Sig + "." + Data;

        RequestParams params = new RequestParams();
        params.put("signed_body", Data);
        params.put("ig_sig_key_version", "4");

        String url = "accounts/login/";


        //TODO: TO CLARIFY WITH COOKIES

        if(option == 1) {
            client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    //TODO: UPDATING PROGRESS FURTHER
                    if (option == 1) {
                        try {
                            userInfo(response.getJSONObject("logged_in_user").getString("pk"), client, obj, password, username, null, 1);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    obj.failedLogin();
                }
            });

        }
        else{
            client.post(getAbsoluteUrl(url), params, handler);
        }
    }

    public void getMedia(String pk , String max_id, PersistentCookieStore cookieStore, AsyncHttpClient client, JsonHttpResponseHandler handler) throws UnsupportedEncodingException, ParseException, JSONException {

        String Data = "{\"_csrftoken\":\""  +  cookieStore.getCookies().get(0).getValue() + "\",\"_uid\":\""+pk+"\"}";
        String Sig = GenerateSignature(Data, "55e91155636eaa89ba5ed619eb4645a4daf1103f2161dbfe6fd94d5ea7716095");
        Data  = Sig + "." + Data;

        RequestParams params = new RequestParams();
        params.put("signed_body", Data);
        params.put("ig_sig_key_version", "4");

        String url = "feed/user/" +pk+ "/?max_id=" +max_id+ "&ranked_content=true&/";


        //TODO: TO CLARIFY WITH COOKIES

        client.post(getAbsoluteUrl(url), params, handler);
    }

    public void follow (String pk , String mypk, PersistentCookieStore cookieStore, AsyncHttpClient client) throws UnsupportedEncodingException, ParseException, JSONException {
        String Data = "{\"_csrftoken\":\""  +  cookieStore.getCookies().get(0).getValue() + "\",\"_uid\":\""+mypk+"\",\"user_id\":\""+pk+"\"}";
        String Sig = GenerateSignature(Data, "26e29e57f4ea61a0ebb4ee0ec483e5efe7ca39093adcfa3689dadbfba139546b");
        Data  = Sig + "." + Data;

        RequestParams params = new RequestParams();
        params.put("signed_body", Data);
        params.put("ig_sig_key_version", "4");

        String url = "friendships/create/" +pk+ "/";


        //TODO: TO CLARIFY WITH COOKIES

        client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("INSTAGRAM-API-RESPONSE-FOLLOW", response.toString());

            }
        });
    }

    public void like (String media_id , String pk, PersistentCookieStore cookieStore, AsyncHttpClient client) throws UnsupportedEncodingException, ParseException, JSONException {


        String Data = "{\"_csrftoken\":\""  +  cookieStore.getCookies().get(0).getValue() + "\",\"_uid\":\""+pk+"\",\"media_id\":\""+media_id+"\"}";
        String Sig = GenerateSignature(Data, "26e29e57f4ea61a0ebb4ee0ec483e5efe7ca39093adcfa3689dadbfba139546b");
        Data  = Sig + "." + Data;

        RequestParams params = new RequestParams();
        params.put("signed_body", Data);
        params.put("ig_sig_key_version", "4");

        String url = "media/"+media_id+"/like/";

Log.d("URL", getAbsoluteUrl(url));
        Log.d("prams", params.toString());

        //TODO: TO CLARIFY WITH COOKIES

        client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Log.d("INSTAGRAM-API-RESPONSE-LIKE", responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.d("INSTAGRAM-API-RESPONSE-LIKE", response.toString());

                //TODO: UPDATING PROGRESS FURTHER
            }
        });
    }

    public void userFollowers (String pk, PersistentCookieStore cookieStore, AsyncHttpClient client, JsonHttpResponseHandler handler) throws UnsupportedEncodingException, ParseException, JSONException {

        String Data = "{\"_csrftoken\":\""  +  cookieStore.getCookies().get(0).getValue() + "\",\"_uid\":\""+pk+"\"}";
        String SigKey = "55e91155636eaa89ba5ed619eb4645a4daf1103f2161dbfe6fd94d5ea7716095";
        String Sig = GenerateSignature(Data, SigKey);
        Data  = Sig + "." + Data;

        RequestParams params = new RequestParams();
        params.put("signed_body", Data);
        params.put("ig_sig_key_version", "4");

        String url = "friendships/followers/?ig_sig_key_version="+SigKey;

        //TODO: TO CLARIFY WITH COOKIES

        client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //TODO: UPDATING PROGRESS FURTHER
            }
        });
    }

    public void makeitPublick (String pk, PersistentCookieStore cookieStore, AsyncHttpClient client, final MasterActivity obj, final String username, final String pass, final Boolean redirect, final int option) throws UnsupportedEncodingException, ParseException{
        String Data = "{\"_csrftoken\":\""  +  cookieStore.getCookies().get(0).getValue() + "\",\"_uid\":\""+pk+"\"}";
        String Sig = GenerateSignature(Data, "55e91155636eaa89ba5ed619eb4645a4daf1103f2161dbfe6fd94d5ea7716095");
        Data  = Sig + "." + Data;

        RequestParams params = new RequestParams();
        params.put("signed_body", Data);
        params.put("ig_sig_key_version", "4");

        String url = "accounts/set_public/";

        //TODO: TO CLARIFY WITH COOKIES

        if(option == 1) {

            client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    //TODO: UPDATING PROGRESS FURTHER
                    if (option == 1) obj.login(username, pass, redirect);
                }
            });
        }
        else{
            client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    //TODO: UPDATING PROGRESS FURTHER
                }
            });
        }
    }

    public void userInfo(String pk, AsyncHttpClient client, final MasterActivity obj, final String pass, final String login, JsonHttpResponseHandler handler, int option) throws UnsupportedEncodingException, ParseException, JSONException {

        String url = "users/" + pk + "/info/";

        if (option == 1) {

            client.get(getAbsoluteUrl(url), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    //TODO: UPDATING PROGRESS FURTHER
                    try {
                        JSONObject userObject = response.getJSONObject("user");
                        User user = new User(userObject.getInt("follower_count"), userObject.getLong("pk"), userObject.getString("username"), userObject.getString("profile_pic_url"));
                        user.setPub(userObject.getString("is_private"));
                        obj.nextLogin(user, login, pass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            client.get(getAbsoluteUrl(url), handler);
        }
    }
}

