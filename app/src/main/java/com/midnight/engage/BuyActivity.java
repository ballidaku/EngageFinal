package com.midnight.engage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.midnight.engage.API.API;
import com.midnight.engage.API.APIFactory;
import com.midnight.engage.Adapters.BuyListAdapter;
import com.midnight.engage.Adapters.BuyObject;
import com.midnight.engage.util.IabHelper;
import com.midnight.engage.util.IabResult;
import com.midnight.engage.util.Inventory;
import com.midnight.engage.util.Purchase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuyActivity extends AppCompatActivity
{
    API api = APIFactory.createAPI();

    BuyListAdapter adapter;
    ListView listView;
    TextView title;
    TextView coins;
    Button back;
    Boolean go = false;
    RelativeLayout loading;
    BuyObject item;
    private Context context;

    IabHelper mHelper;

    // Debug tag, for logging
    static final String TAG = "bazar";

    // SKUs for our products: the premium upgrade (non-consumable)

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 1;

    // The helper object


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        title = (TextView) findViewById(R.id.to_buy);
        if (((Global) getApplication()).getToBuy().equals("coins"))
            title.setText("Buy Coin Packs");
        if (((Global) getApplication()).getToBuy().equals("likes"))
            title.setText("Buy instant likes");
        if (((Global) getApplication()).getToBuy().equals("followers"))
            title.setText("Buy instant followers");
        listView = (ListView) findViewById(R.id.buy_list);
        if (((Global) getApplication()).getToBuy().equals("coins"))
            adapter = new BuyListAdapter(((Global) getApplication()).getBuyCoinsList(), this);
        if (((Global) getApplication()).getToBuy().equals("likes"))
            adapter = new BuyListAdapter(((Global) getApplication()).getBuyLikesList(), this);
        if (((Global) getApplication()).getToBuy().equals("followers"))
            adapter = new BuyListAdapter(((Global) getApplication()).getBuyFollowList(), this);

        listView.setAdapter(adapter);
        loading = (RelativeLayout) findViewById(R.id.loadingPanelbuy);
        coins = (TextView) findViewById(R.id.user_coinss);
        back = (Button) findViewById(R.id.back_btnn);
        coins.setText(((Global) getApplication()).getMoney());
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                Intent intent = new Intent(BuyActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        if (((Global) getApplication()).isGoogleInApps())
        {


            String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlIeinCZ2ugvySd7HJXiH95tKQf1Gb0ibxTZq9CHujuFvlNWnLyDGGh7TRVN/cJLcoXbtGeuYQEDOoBmXJBRQUbW7KXXK+nhcbg686xVQSpqw/TRpPPODj6WQ/OQuU4syUFV9Oni8Utd7FTNPXdPgHJsNy8hGGgXWQUrmP4VROyJUfx6IZHGYl630NtbD8HbjB8Io2iGPR2AthBp+1WYJmweBGjvKGHJbaX8CSHqzLfEbhCkz3ulwz3jVKDCEmeVId0bBCYmiz/aIYu+TEpSB/hTQOrhVgS/59JlMk7hORZvrTHcxKBqaF2ZQ56XcPTM028NG7jOwjOLeMSYbwP2YWQIDAQAB";
// You can find it in your Bazaar console, in the Dealers section.
// It is recommended to add more security than just pasting it in your source code;
            mHelper = new IabHelper(BuyActivity.this, base64EncodedPublicKey);

            Log.d(TAG, "Starting setup.");
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener()
            {
                @Override
                public void onIabSetupFinished(IabResult result)
                {
                    Log.d(TAG, "Setup finished.");

                    if (!result.isSuccess())
                    {
                        // Oh noes, there was a problem.
                        Log.d(TAG, "Problem setting up In-app Billing: " + result);
                    }
                    // Hooray, IAB is fully set up!

                    try {

                        mHelper.queryInventoryAsync(mGotInventoryListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error querying inventory. Another async operation in progress.");
                       }
                    go = true;
                }
            });
        }

    }


    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener()
    {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inventory)
        {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure())
            {
                Log.d(TAG, "Failed to query inventory: " + result);
                return;
            }
            else
            {
                Log.d(TAG, "Query inventory was successful.");

            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };


    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener()
    {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase)
        {
            if (result.isFailure())
            {
                Log.d(TAG, "Error purchasing: " + result);
                return;
            }
            else
            {
                loading.setVisibility(View.VISIBLE);

                try
                {
                mHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener()
                {
                    @Override
                    public void onConsumeFinished(Purchase purchase, IabResult result)
                    {
                        //  Toast.makeText(BuyActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);

                        if (((Global) getApplication()).getToBuy().equals("coins"))
                        {
                            final Long sum = Long.parseLong((((Global) getApplication()).getMoney())) + item.getValue();
                            SomeRandomeClass.AddToList("userid", ((Global) getApplication()).getCurrentUser().getPk().toString());
                            SomeRandomeClass.AddToList("money", sum.toString());
                            Call<Void> call3 = api.addMoney(SomeRandomeClass.GetData());
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    upd();
                                }
                            });
                            call3.enqueue(new Callback<Void>()
                            {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response)
                                {

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t)
                                {

                                }
                            });

                        }

                        if (((Global) getApplication()).getToBuy().equals("likes"))
                        {
                            SomeRandomeClass.AddToList("userid", ((Global) getApplication()).getCurrentUser().getPk().toString());
                            SomeRandomeClass.AddToList("count", item.getValue().toString());
                            SomeRandomeClass.AddToList("media_id", ((Global) getApplication()).getToLike().getImageId());
                            Call<Void> il = api.addInstLike(SomeRandomeClass.GetData());
                            il.enqueue(new Callback<Void>()
                            {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response)
                                {

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t)
                                {

                                }
                            });
                        }
                        if (((Global) getApplication()).getToBuy().equals("followers"))
                        {

                            SomeRandomeClass.AddToList("userid", ((Global) getApplication()).getCurrentUser().getPk().toString());
                            SomeRandomeClass.AddToList("count", item.getValue().toString());
                            Call<Void> il = api.addInstFollow(SomeRandomeClass.GetData());
                            il.enqueue(new Callback<Void>()
                            {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response)
                                {

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t)
                                {

                                }
                            });
                        }
                    }
                });

                } catch (IabHelper.IabAsyncInProgressException e)
                {
                    complain("Error consuming gas. Another async operation in progress.");

                    return;
                }



            }

        }


        //--------


    };


    public void puchase(BuyObject object)
    {
        item = object;
        if (go)
        {
            if (mHelper != null)
            {
                try
                {
                    mHelper.launchPurchaseFlow(this, object.getSku(), RC_REQUEST, mPurchaseFinishedListener, "payload-string");

                } catch (IabHelper.IabAsyncInProgressException ex)
                {
                    Toast.makeText(this, "Please retry in a few seconds.", Toast.LENGTH_SHORT).show();
                    mHelper.flagEndAsync();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data))
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
        else
        {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();

    }


    void complain(String message)
    {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message)
    {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    public void upd()
    {
        coins.setText(((Global) getApplication()).getMoney());
    }
}
