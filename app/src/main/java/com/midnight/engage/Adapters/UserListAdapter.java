package com.midnight.engage.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.*;
import com.midnight.engage.API.API;
import com.midnight.engage.API.APIFactory;
import com.midnight.engage.API.User;
import com.midnight.engage.Dao.Users;
import com.midnight.engage.Global;
import com.midnight.engage.InstagramRequestClass;
import com.midnight.engage.MainActivity;
import com.midnight.engage.R;
import com.squareup.picasso.Picasso;

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
public class UserListAdapter extends BaseAdapter {
    List<Users> list = new ArrayList<>();
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    private static AsyncHttpClient client = new AsyncHttpClient();
    PersistentCookieStore cookieStore;


    public UserListAdapter(List<Users> list, Context context) {
        this.list = list;
        this.context = context;
        sharedPref = context.getSharedPreferences("my",Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        cookieStore = new PersistentCookieStore(context);

        client.addHeader("User-Agent", "Instagram 8.2.0 Android (18/4.3; 320dpi; 720x1280; Xiaomi; HM 1SW; armani; qcom; en_US)");
        client.setCookieStore(cookieStore);
    }

    Context context;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_list_cell , parent , false);
        final Users item  = (Users) getItem(position);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.user_list_user_pic);
        TextView username = (TextView) rowView.findViewById(R.id.user_list__username);
        TextView coins = (TextView) rowView.findViewById(R.id.user_list_user_coin);
       if (item.getCoins()==null )
           coins.setText("0");
        else
        coins.setText(item.getCoins());
        Picasso.with(context).load(item.getImageUrl()).into(imageView);
        username.setText("@" + item.getUsername());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logFunc(item);
            }
        });
        return rowView;
    }
    private void logFunc(final Users item) {


        final API service = APIFactory.createAPI();
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

                try {
                    //final User adi = InstagramPostHelper.getInstance().Login(item.getUsername(), item.getPassword());
                    InstagramRequestClass.getInstance().Login(item.getUsername(), item.getPassword(), client, null, 2, item, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                InstagramRequestClass.getInstance().userInfo(response.getJSONObject("logged_in_user").getString("pk"), client, null, item.getPassword(), item.getUsername(), new JsonHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        JSONObject userObject = null;
                                        try {
                                            userObject = response.getJSONObject("user");
                                            User user = new User(userObject.getInt("follower_count"), userObject.getLong("pk"), userObject.getString("username"), userObject.getString("profile_pic_url"));
                                            user.setPub(userObject.getString("is_private"));
                                            nextPartLogin(dialog, user, item);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 2);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }



    }

    public void nextPartLogin(ProgressDialog dialog, final User adi, Users item){
        if (adi.getPub().equals("true")) {
                    dialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Error!").setMessage("We found your profile is 'Private'").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("Make it 'public", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                    try {

                                        //InstagramPostHelper.getInstance().makeitPublick(adi.getPk().toString());
                                        InstagramRequestClass.getInstance().makeitPublick(adi.getPk().toString(), cookieStore, client, null, null, null, null, 2);

                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                        }
                    }).create().show();
            return;
        }

                ((Global) context.getApplicationContext()).setCurrentUser(adi);
                ((Global) context.getApplicationContext()).setPassword(item.getPassword());
                Intent intent = new Intent(context, MainActivity.class);
                ((Activity) context).finish();
                context.startActivity(intent);
                editor.putString("username", adi.getUsername());
                editor.putString("password", item.getPassword());
                editor.commit();
    }
}
