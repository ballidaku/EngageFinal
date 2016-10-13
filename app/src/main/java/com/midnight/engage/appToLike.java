package com.midnight.engage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.midnight.engage.API.API;
import com.midnight.engage.API.APIFactory;
import com.midnight.engage.Adapters.InstallAppAdapter;
import com.midnight.engage.Models.appToInstall;
import com.midnight.engage.Models.apptoinstallresponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class appToLike extends AppCompatActivity {
    InstallAppAdapter adapter;
    ListView listView;
    TextView coins;
    Button back;
    List<appToInstall> list = new ArrayList<>();
    List<appToInstall> list2 = new ArrayList<>();






    API service;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_to_like);

        service = APIFactory.createAPI();
        listView = (ListView) findViewById(R.id.buy_list1);
        coins = (TextView) findViewById(R.id.user_coinss1);
        back = (Button) findViewById(R.id.back_btnn1);
        coins.setText(((Global)getApplication()).getMoney());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(appToLike.this, MainActivity.class);
                startActivity(intent);

            }
        });

test();

    }


    @Override
    protected void onResume() {
        super.onResume();
test();

    }
    public void test() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


        final API api = APIFactory.createAPI();
        Call<apptoinstallresponse> call = api.appToLike();
        call.enqueue(new Callback<apptoinstallresponse>() {
            @Override
            public void onResponse(Call<apptoinstallresponse> call, Response<apptoinstallresponse> response) {
                if (response.body() != null) {

                    list = response.body().getList();
                    list2 = response.body().getList();

                    for (appToInstall a : list) {

                        if (isAppInstalled(a.getPack()) && !sharedPref.getBoolean(a.getPack(), false)) {
                            final Long sum = Integer.parseInt(a.getMoney()) + Long.parseLong((((Global) getApplication()).getMoney()));
                            coins.setText(sum.toString());
                            SomeRandomeClass.AddToList("userid", ((Global) getApplication()).getCurrentUser().getPk().toString());
                            SomeRandomeClass.AddToList("money", sum.toString());
                            Call<Void> call3 = api.addMoney(SomeRandomeClass.GetData());                             call3.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean(a.getPack(), true);
                            editor.commit();

                        }

                    }

            runOnUiThread(new Runnable() {
    @Override
    public void run() {
            for (int i = 0; i < list.size(); i++) {
                if (isAppInstalled(list.get(i).getPack())) {
                    list2.remove(i);

                }
            }

        adapter = new InstallAppAdapter(appToLike.this , list2);
        listView.setAdapter(adapter);
    }
});
                }
            }

            @Override
            public void onFailure(Call<apptoinstallresponse> call, Throwable t) {

            }
        });

    }

    private boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(appToLike.this, MainActivity.class);
        startActivity(intent);
    }
}
