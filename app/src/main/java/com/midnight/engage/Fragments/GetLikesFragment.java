package com.midnight.engage.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import com.loopj.android.http.*;
import com.midnight.engage.API.API;
import com.midnight.engage.API.APIFactory;
import com.midnight.engage.Adapters.GetLikesAdapter;
import com.midnight.engage.Global;

import com.midnight.engage.InstagramRequestClass;
import com.midnight.engage.MainActivity;
import com.midnight.engage.Models.MediaForMyMedia;
import com.midnight.engage.R;
import com.midnight.engage.SomeRandomeClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by adi on 6/3/16.
 */
public class GetLikesFragment extends Fragment {
    List<MediaForMyMedia> list = new ArrayList<>();
    MediaForMyMedia item ;
    GridView grid;
    GetLikesAdapter adapter;
    API service = APIFactory.createAPI();

    String id = "";
    Boolean firs = true;

    private static AsyncHttpClient client = new AsyncHttpClient();
    PersistentCookieStore cookieStore;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        cookieStore = new PersistentCookieStore(getActivity());

        client.addHeader("User-Agent", "Instagram 8.2.0 Android (18/4.3; 320dpi; 720x1280; Xiaomi; HM 1SW; armani; qcom; en_US)");
        client.setCookieStore(cookieStore);

        View view = inflater.inflate(R.layout.get_likes_fragment , null);
        grid = (GridView) view.findViewById(R.id.get_likes_greed);
fill();
                    ((MainActivity) getActivity()).coinsEnable();
                    ((MainActivity) getActivity()).followersEnable();

        return view;
    }
//    public void fill(){
//        ((MainActivity)getActivity()).setLoading(true);
//
//        Call<myMedia> call =  service.myMedia(((Global)getActivity().getApplication()).getCurrentUser().getUsername() , ((Global)getActivity().getApplication()).getPassword() , "kasld1>!<123kml1" , id);
//        call.enqueue(new Callback<myMedia>() {
//            @Override
//            public void onResponse(Call<myMedia> call, Response<myMedia> response) {
//                if (response.body() != null) {
//
//                    for (mymediaites i : response.body().getResponseMedia().getItems()) {
//                        item = new MediaForMyMedia(i.getImageVersions2().getCandidates().get(1).getUrl(), i.getId(), i.getLikeCount());
//                        list.add(item);
//                    }
//                    if (response.body().getResponseMedia().getNext_max_id() != null)
//                        id = response.body().getResponseMedia().getNext_max_id();
//                    else
//                        id = "";
//
//                    adapter = new GetLikesAdapter(list, GetLikesFragment.this, getActivity() , id);
//                    grid.setNumColumns(2);
//                    if (firs) {
//                        grid.setAdapter(adapter);
//                        firs = false;
//                    }
//                    ((MainActivity) getActivity()).setLoading(false);
//                    ((MainActivity) getActivity()).coinsEnable();
//                    ((MainActivity) getActivity()).followersEnable();
//                }else {
//                    ((MainActivity) getActivity()).setLoading(false);
//                    ((MainActivity) getActivity()).coinsEnable();
//                    ((MainActivity) getActivity()).followersEnable();
//                }
//            }
//            @Override
//            public void onFailure(Call<myMedia> call, Throwable t) {
//                ((MainActivity) getActivity()).coinsEnable();
//                ((MainActivity) getActivity()).followersEnable();
//                ((MainActivity) getActivity()).setLoading(false);
//
//            }
//        });
//
//    }
    public void fill() {
        ((MainActivity)getActivity()).setLoading(true);
        API service = APIFactory.createAPI();

        SomeRandomeClass.AddToList("login" , ((Global)getActivity().getApplication()).getCurrentUser().getUsername());
        SomeRandomeClass.AddToList("password" , ((Global)getActivity().getApplication()).getPassword());
        SomeRandomeClass.AddToList("max_id" , id);
                try {
                    //JSONObject media =  InstagramPostHelper.getInstance().getMedia(((Global)getActivity().getApplication()).getCurrentUser().getPk().toString() , id);
                    InstagramRequestClass.getInstance().getMedia(((Global)getActivity().getApplication()).getCurrentUser().getPk().toString(), id, cookieStore, client, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            JSONObject media = response;
                            JSONArray mediax = null;
                            try {
                                mediax = media.getJSONArray("items");
                                for (int i = 0 ;  i < mediax.length(); i++) {
                                    item = new MediaForMyMedia(mediax.getJSONObject(i).getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(1).getString("url"), mediax.getJSONObject(i).getString("id"), mediax.getJSONObject(i).getLong("like_count"));
                                    list.add(item);
                                }
                                ((MainActivity)getActivity()).setLoading(false);

                                if (media.has("next_max_id"))
                                    id = media.getString("next_max_id");
                                else
                                    id = "";
                                adapter = new GetLikesAdapter(list, GetLikesFragment.this,getActivity() , id);
                                assert grid != null;
                                grid.setNumColumns(3);
                                if (firs) {
                                    grid.setAdapter(adapter);
                                    firs = false;
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });




                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

    }
}
