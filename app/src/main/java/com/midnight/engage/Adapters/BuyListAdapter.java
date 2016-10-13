package com.midnight.engage.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.midnight.engage.API.API;
import com.midnight.engage.API.APIFactory;
import com.midnight.engage.BuyActivity;
import com.midnight.engage.Dialogs.CardDilaog;
import com.midnight.engage.Global;
import com.midnight.engage.R;
import com.midnight.engage.SomeRandomeClass;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adi on 6/8/16.
 */
public class BuyListAdapter extends BaseAdapter
{
    List<BuyObject> list;
    API api = APIFactory.createAPI();
    BuyListAdapter a = this;


    public BuyListAdapter(List<BuyObject> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    Context context;

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.buy_cell, parent, false);

        final BuyObject item = (BuyObject) getItem(position);

        TextView value = (TextView) rowView.findViewById(R.id.value);
        TextView price = (TextView) rowView.findViewById(R.id.price);

        if (((Global) context.getApplicationContext()).getToBuy().equals("coins"))
            value.setText(item.getValue().toString() + " coins");
        if (((Global) context.getApplicationContext()).getToBuy().equals("likes"))
            value.setText(item.getValue() + " likes");
        if (((Global) context.getApplicationContext()).getToBuy().equals("followers"))
            value.setText(item.getValue() + " followers");

        price.setText("$" + item.getPrice());

        rowView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//        CardDilaog dialog = new CardDilaog(a , item);
//        dialog.show(((BuyActivity)context).getFragmentManager(), "awrd");
                //  ((BuyActivity)context).puchase(item);


                if (((Global) context.getApplicationContext()).isGoogleInApps())
                {
                    ((BuyActivity) context).puchase(item);
                    Log.e("Hello","1");
                }
                else
                {
                    Log.e("Hello","2");
                    CardDilaog dialog = new CardDilaog(a, item);
                    dialog.show(((BuyActivity) context).getFragmentManager(), "awrd");
                }


            }
        });
        return rowView;
    }

    public void buy(com.stripe.android.model.Token token, final BuyObject item)
    {
        final Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", String.valueOf(Float.valueOf(item.getPrice()) * 100).substring(0, String.valueOf(Float.valueOf(item.getPrice()) * 100).length() - 2)); // amount in cents, again
        chargeParams.put("currency", "usd");
        // chargeParams.put("source", token);

        chargeParams.put("card", token.getId());
        chargeParams.put("description", "Example charge");
        new AsyncTask<Void, Void, Void>()
        {


            @Override
            protected Void doInBackground(Void... params)
            {
                try
                {
                    com.stripe.Stripe.apiKey = "sk_test_syPcjBRnRLOb18b3KPATndLr";
                    Charge.create(chargeParams);
                    if (((Global) context.getApplicationContext()).getToBuy().equals("coins"))
                    {
                        final Long sum = Long.parseLong((((Global) context.getApplicationContext()).getMoney())) + item.getValue();
                        SomeRandomeClass.AddToList("userid", ((Global) context.getApplicationContext()).getCurrentUser().getPk().toString());
                        SomeRandomeClass.AddToList("money", sum.toString());
                        Call<Void> call3 = api.addMoney(SomeRandomeClass.GetData());
                        ((Global) context.getApplicationContext()).setMoney(sum.toString());
                        ((BuyActivity) context).runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                ((BuyActivity) context).upd();
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

                    if (((Global) context.getApplicationContext()).getToBuy().equals("likes"))
                    {
                        SomeRandomeClass.AddToList("userid", ((Global) context.getApplicationContext()).getCurrentUser().getPk().toString());
                        SomeRandomeClass.AddToList("count", item.getValue().toString());
                        SomeRandomeClass.AddToList("media_id", ((Global) context.getApplicationContext()).getToLike().getImageId());
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
                    if (((Global) context.getApplicationContext()).getToBuy().equals("followers"))
                    {

                        SomeRandomeClass.AddToList("userid", ((Global) context.getApplicationContext()).getCurrentUser().getPk().toString());
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

                } catch (AuthenticationException e)
                {
                    e.printStackTrace();
                } catch (InvalidRequestException e)
                {
                    e.printStackTrace();
                } catch (APIConnectionException e)
                {
                    e.printStackTrace();
                } catch (CardException e)
                {
                    e.printStackTrace();
                } catch (APIException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

}
