package com.midnight.engage;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import com.google.android.gms.ads.MobileAds;
import com.loopj.android.http.*;
import com.midnight.engage.API.API;
import com.midnight.engage.API.APIFactory;
import com.midnight.engage.API.UserResponse;
import com.midnight.engage.API.awardsResponse;
import com.midnight.engage.API.unfollowResponeLvl1;
import com.midnight.engage.Adapters.UserListAdapter;
import com.midnight.engage.Dao.DaoMaster;
import com.midnight.engage.Dao.DaoSession;
import com.midnight.engage.Dao.Users;
import com.midnight.engage.Fragments.GetCoinsFragment;
import com.midnight.engage.Fragments.GetLikesFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adi on 6/3/16.
 */
public class MainActivity extends AppCompatActivity {
    RelativeLayout actionBar;
    LinearLayout container;
    RelativeLayout sidemenu;
    ImageView userpic;
    TextView username;
    TextView usercoins;
    RelativeLayout logOut;
    RelativeLayout addAcc;
    TextView coins;
    Button getLikes;
    Button getCoins;
    Button getFollowers;
    RelativeLayout buyCoins;
    RelativeLayout getfreeCoins;
    RelativeLayout moreapps;

    DaoMaster daoMaster;
    DaoSession daoSession;
    SQLiteDatabase db;
    DaoMaster.DevOpenHelper helper;
    ListView listView;
    UserListAdapter adapter;
    RelativeLayout loading;
    RelativeLayout contactUs;
    SharedPreferences sharedPref;


    private static AsyncHttpClient client = new AsyncHttpClient();
    PersistentCookieStore cookieStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        Fabric.with(this, new Crashlytics());
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        cookieStore = new PersistentCookieStore(this);

        client.addHeader("User-Agent", "Instagram 8.2.0 Android (18/4.3; 320dpi; 720x1280; Xiaomi; HM 1SW; armani; qcom; en_US)");
        client.setCookieStore(cookieStore);

        sharedPref = getSharedPreferences("my",Context.MODE_PRIVATE);
        actionBar = (RelativeLayout) findViewById(R.id.top_bar);
        container = (LinearLayout) findViewById(R.id.fragment_container);
        userpic = (ImageView) findViewById(R.id.side_bar_user_pic);
        username = (TextView) findViewById(R.id.side_bar_username);
        usercoins = (TextView) findViewById(R.id.side_bar_user_coin);
        coins = (TextView) findViewById(R.id.coins);
        contactUs = (RelativeLayout)findViewById(R.id.side_bar_contact_us);
        moreapps = (RelativeLayout) findViewById(R.id.side_bar_more_apps);
        moreapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(((Global)getApplication()).getMoreappslink()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { ((Global)getApplication()).getEmailcontactus() });
                intent.putExtra(Intent.EXTRA_SUBJECT, ((Global)getApplication()).getSubjectcontactus());
                startActivity(Intent.createChooser(intent, ""));
            }
        });
        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global)getApplication()).setToBuy("coins");

                Intent intent = new Intent(MainActivity.this, BuyActivity.class);
                startActivity(intent);
            }
        });
        loading = (RelativeLayout) findViewById(R.id.loadingPanelmain);

        buyCoins = (RelativeLayout) findViewById(R.id.side_bar_buy_coins);
        buyCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global)getApplication()).setToBuy("coins");

                Intent intent = new Intent(MainActivity.this, BuyActivity.class);
                startActivity(intent);
            }
        });
        getfreeCoins = (RelativeLayout) findViewById(R.id.side_bar_get_coins);
        getfreeCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, appToLike.class);
                startActivity(intent);
            }
        });
        helper = new DaoMaster.DevOpenHelper(this , "db" , null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        Picasso.with(this).load(((Global)getApplication()).getCurrentUser().getProfilePicUrl()).into(userpic);
        username.setText("@"+((Global)getApplication()).getCurrentUser().getUsername());
        usercoins.setText("0");
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        Button menu = (Button) findViewById(R.id.top_bar_menu_item);
        sidemenu = (RelativeLayout) findViewById(R.id.side_bar);
        assert sidemenu != null;
        sidemenu.setX(-size.x);
        assert menu != null;
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidemenu.animate().x(0);
            }
        });
        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidemenu.animate().x(-size.x);
            }
        });
        logOut = (RelativeLayout) findViewById(R.id.side_bar_log_out);
        addAcc = (RelativeLayout) findViewById(R.id.side_bar_add_acc);
        addAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("username");
                editor.commit();
                Intent intent = new Intent(MainActivity.this, MasterActivity.class);
                startActivity(intent);

            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("username");
                editor.commit();
                finish();
                Intent intent = new Intent(MainActivity.this, MasterActivity.class);
                startActivity(intent);
            }
        });
        getLikes = (Button) findViewById(R.id.get_likes_main_btn);
        getCoins = (Button) findViewById(R.id.get_coin_main_btn);
        getFollowers = (Button) findViewById(R.id.get_follower_main_btn);
        getCoins.animate().y(getCoins.getY() -4);
        getCoins.setEnabled(false);

        getLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCoins.setEnabled(false);
                getLikes.setEnabled(false);
                getFollowers.setEnabled(false);
                addFragmentToBakcStack(new GetLikesFragment());
                getCoins.animate().y(getCoins.getY() +4);
                getLikes.animate().y(getLikes.getY() - 4);
                      }
        });
        getFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global)getApplication()).setFollow(true);
                Intent intent = new Intent(MainActivity.this, AddCompain.class);
                startActivity(intent);
            }
        });
        getCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLikes.setEnabled(false);
                getCoins.setEnabled(false);
                getFollowers.setEnabled(false);
                addFragmentToBakcStack(new GetCoinsFragment());
                getCoins.animate().y(getCoins.getY() -4);
                getLikes.animate().y(getLikes.getY() + 4);
            }
        });
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listView = (ListView) findViewById(R.id.side_bar_acc_list);
        final API service = APIFactory.createAPI();
        SomeRandomeClass.AddToList("userid" , ((Global)getApplication()).getCurrentUser().getPk().toString());
        SomeRandomeClass.AddToList("money" ,((Global)getApplication()).getMoney() );
        Call<UserResponse> call = service.getUser(SomeRandomeClass.GetData());
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
               if (response.body() != null) {
                   ((Global) getApplication()).setMoney(response.body().getResponselvl2().getMoney());
                   for (Users u : daoSession.getUsersDao().loadAll()) {
                       if (u.getId().equals(((Global) getApplication()).getCurrentUser().getPk())) {
                           u.setCoins(response.body().getResponselvl2().getMoney());
                           daoSession.getUsersDao().insertOrReplace(u);

                       }
                       coins.setText(response.body().getResponselvl2().getMoney());
                       usercoins.setText(response.body().getResponselvl2().getMoney());
                       MainActivity.this.runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               List<Users> list = daoSession.getUsersDao().loadAll();
                               for (Users x : daoSession.getUsersDao().loadAll()) {
                                   if (x.getId().equals(((Global) getApplication()).getCurrentUser().getPk()))
                                       list.remove(x);
                               }
                               adapter = new UserListAdapter(list, MainActivity.this);
                               listView.setAdapter(adapter);
                               addFragmentToBakcStack(new GetCoinsFragment());

                           }
                       });


                   }

               } else {
                   MainActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           List<Users> list = daoSession.getUsersDao().loadAll();
                           for (Users x : daoSession.getUsersDao().loadAll()) {
                               if (x.getId().equals(((Global) getApplication()).getCurrentUser().getPk()))
                                   list.remove(x);
                           }
                           adapter = new UserListAdapter(list, MainActivity.this);
                           listView.setAdapter(adapter);
                           addFragmentToBakcStack(new GetCoinsFragment());

                       }
                   });
               }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });

        SomeRandomeClass.AddToList("userid" , ((Global)getApplication()).getCurrentUser().getPk().toString());

        Call<awardsResponse> awrd = service.dawards(SomeRandomeClass.GetData());
        awrd.enqueue(new Callback<awardsResponse>() {
            @Override
            public void onResponse(Call<awardsResponse> call, Response<awardsResponse> response) {
                if (response.body() != null) {

                    if (!response.body().getResponse().getNwaward().equals("0")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setMessage("Hey, thank you for dayly access of App\nYou received 10 coins for your fidelity.").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).create().show();

                        Long sum = Long.parseLong((((Global) getApplication()).getMoney())) + 10;
                        updateCoins(sum.toString());



                    }
                }
            }

            @Override
            public void onFailure(Call<awardsResponse> call, Throwable t) {
                Throwable x = t;
            }
        });

                    //String followers = InstagramPostHelper.getInstance().userFollowers(((Global) getApplication()).getCurrentUser().getPk().toString());
        try {
            InstagramRequestClass.getInstance().userFollowers(((Global) getApplication()).getCurrentUser().getPk().toString(), cookieStore, client, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    String followers = "";
                    JSONArray ar = null;
                    try {
                        ar = response.getJSONArray("users");

                        for (int i = 0; i < ar.length(); i++) {
                            if (i < ar.length() - 1)
                                followers = followers + ar.getJSONObject(i).getString("pk") + ",";
                            else
                                followers = followers + ar.getJSONObject(i).getString("pk");
                        }

                        if (followers.length() > 0) {
                            SomeRandomeClass.AddToList("user_id", ((Global) getApplication()).getCurrentUser().getPk().toString());
                            SomeRandomeClass.AddToList("login", ((Global) getApplication()).getCurrentUser().getUsername());
                            SomeRandomeClass.AddToList("password", ((Global) getApplication()).getPassword());
                            SomeRandomeClass.AddToList("followers", followers);

                            //  Log.d("Some Random Class Unfollow", SomeRandomeClass.GetData().toString());
                            Call<unfollowResponeLvl1> unfollow = APIFactory.createAPI().unfollow(SomeRandomeClass.GetData());
                            unfollow.enqueue(new Callback<unfollowResponeLvl1>() {
                                @Override
                                public void onResponse(Call<unfollowResponeLvl1> call, final Response<unfollowResponeLvl1> response) {
                                    if (response.body() != null) {

                                        if (!response.body().getResponse().getCois().equals("0")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                                                    builder.setMessage("Hey we detected that you unfollowed " + response.body().getResponse().getObject() + "\nWe deducted " + response.body().getResponse().getCois() + " coins from your account.").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.dismiss();
                                                        }
                                                    }).create().show();
                                                    Long sum = Long.parseLong((((Global) getApplication()).getMoney())) - Long.parseLong(response.body().getResponse().getCois());
                                                    updateCoins(sum.toString());
                                                }
                                            });

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<unfollowResponeLvl1> call, Throwable t) {

                                }
                            });


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

    public void addFragmentToBakcStack(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }
    public void setLoading(Boolean acitve) {
        if (acitve) {
            loading.setVisibility(View.VISIBLE);

        } else {
            loading.setVisibility(View.INVISIBLE);
        }
    }
public void updateCoins(String coincount) {
    coins.setText(coincount);
    usercoins.setText(coincount);
}

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        coins.setText(((Global)getApplication()).getMoney());
        usercoins.setText(((Global)getApplication()).getMoney());
    }

    public void coinsEnable() {
        getCoins.setEnabled(true);
    }
    public void likesEnable() {
        getLikes.setEnabled(true);
    }
    public void followersEnable() {
        getFollowers.setEnabled(true);
    }

}
