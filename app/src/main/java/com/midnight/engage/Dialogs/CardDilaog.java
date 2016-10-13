package com.midnight.engage.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.midnight.engage.Adapters.BuyListAdapter;
import com.midnight.engage.Adapters.BuyObject;
import com.midnight.engage.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

/**
 * Created by adi on 7/7/16.
 */
public class CardDilaog extends DialogFragment {
    BuyListAdapter adapter;
     BuyObject item;
    @SuppressLint("ValidFragment")
    public CardDilaog(BuyListAdapter adapter , BuyObject item ) {
        this.adapter = adapter;
        this.item = item;
    }

    public CardDilaog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.card_dialog, null);
            // TODO: replace with your own test key
            final String publishableApiKey =
                    "pk_live_olpBH5se9XLcsibet8VusefZ";


            final TextView cardNumberField = (TextView) view.findViewById(R.id.cardNumber);
            final TextView monthField = (TextView) view.findViewById(R.id.month);
            final TextView yearField = (TextView) view.findViewById(R.id.year);
            final TextView cvcField = (TextView) view.findViewById(R.id.cvc);
            Button submit = (Button) view.findViewById(R.id.submitButton);
            submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardNumberField.getText().toString().equals("") && !monthField.getText().toString().equals("") && !yearField.getText().toString().equals("") && !cvcField.getText().toString().equals("")) {

                    Card card = new Card(cardNumberField.getText().toString(),
                            Integer.valueOf(monthField.getText().toString()),
                            Integer.valueOf(yearField.getText().toString()),
                            cvcField.getText().toString());

                    Stripe stripe = new Stripe();
                    stripe.createToken(card, publishableApiKey, new TokenCallback() {

                        public void onError(Exception error) {
                            Toast.makeText(getActivity() , error.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                            Log.d("Stripe", error.getLocalizedMessage());
                        }

                        @Override
                        public void onSuccess(Token token) {
                           adapter.buy(token, item);
                            getDialog().dismiss();

                        }
                    });


                }

                    }
                });




        builder.setView(view);

        return builder.create();
    }

}
