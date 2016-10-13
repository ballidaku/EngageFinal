package com.midnight.engage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.midnight.engage.API.API;
import com.midnight.engage.API.APIFactory;
import com.midnight.engage.Models.MediaForMyMedia;
import com.squareup.picasso.Picasso;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCompain extends AppCompatActivity {
    Button back;
    Button more;
    Button less;
    RelativeLayout go;
    RelativeLayout goInstant;
    ImageView contentmask;

    ImageView content;
    TextView now;
    TextView after;
    TextView propunere;
    TextView count;
    TextView cost;
    TextView username;
    TextView coins;
    Boolean follow;
    Integer countInt = 0;
    MediaForMyMedia like;
    RelativeLayout loading;
    RelativeLayout buyMore;
    RelativeLayout buyMore2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_compain);
        back = (Button) findViewById(R.id.back_btn);
        more = (Button) findViewById(R.id.more);
        less = (Button) findViewById(R.id.less);
        content = (ImageView) findViewById(R.id.content);
        contentmask = (ImageView) findViewById(R.id.mask_content);

        now = (TextView) findViewById(R.id.now);
        after = (TextView) findViewById(R.id.after);
        propunere = (TextView) findViewById(R.id.propunere);
        count = (TextView) findViewById(R.id.count);
        cost = (TextView) findViewById(R.id.cost);
        username = (TextView) findViewById(R.id.username);
        coins = (TextView) findViewById(R.id.user_coins);
        go = (RelativeLayout) findViewById(R.id.go);
        goInstant = (RelativeLayout) findViewById(R.id.go_instant);
        follow = ((Global) getApplication()).getFollow();
        loading = (RelativeLayout) findViewById(R.id.loadingPanel);
        buyMore = (RelativeLayout) findViewById(R.id.buy_more);
        buyMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global)getApplication()).setToBuy("coins");

                Intent intent = new Intent(AddCompain.this, BuyActivity.class);
                startActivity(intent);
            }
        });
        buyMore2 = (RelativeLayout) findViewById(R.id.buy_more2);
        buyMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global)getApplication()).setToBuy("coins");

                Intent intent = new Intent(AddCompain.this, BuyActivity.class);
                startActivity(intent);
            }
        });

        count.setText(countInt.toString());
        cost.setText(countInt.toString());
        if (follow) {
            username.setVisibility(View.VISIBLE);
            username.setText("@"+((Global)getApplication()).getCurrentUser().getUsername());
            propunere.setText("How many followers you whant?");
            Picasso.with(this).load(((Global)getApplication()).getCurrentUser().getProfilePicUrl()).into(content);
            contentmask.setVisibility(View.VISIBLE);
            now.setText(((Global)getApplication()).getCurrentUser().getFollowerCount().toString() + " followers");
            after.setText(((Global)getApplication()).getCurrentUser().getFollowerCount().toString() +" follower");
        } else {
            username.setVisibility(View.INVISIBLE);
            contentmask.setVisibility(View.INVISIBLE);

            propunere.setText("How many likes you whant?");
            like = ((Global) getApplication()).getToLike();
            Picasso.with(this).load(like.getImageUrl()).into(content);
            now.setText(like.getCurrentLikes().toString() + " likes");
            after.setText(like.getCurrentLikes().toString()+ " likes");


        }
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                less.setEnabled(true);

                countInt  = countInt + 5;

                count.setText(countInt.toString());
               Integer y  = countInt*((Global) getApplication()).getPerLike();
                cost.setText(y.toString());
                if(follow) {
                    Integer afterInt = ((Global)getApplication()).getCurrentUser().getFollowerCount() + countInt;
                    after.setText(afterInt.toString() +" followers");
                } else {
                    Long afterLong = like.getCurrentLikes() + countInt;
                    after.setText(afterLong.toString() +" likes");
                }
            }
        });
        less.setEnabled(false);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countInt == 5) {
                    less.setEnabled(false);
                }
                countInt = countInt - 5;
                count.setText(countInt.toString());
                Integer x = countInt*((Global) getApplication()).getPerLike();
                cost.setText(x.toString());
                if(follow) {
                    Integer afterInt = ((Global)getApplication()).getCurrentUser().getFollowerCount() + countInt;
                    after.setText(afterInt.toString() +" followers");
                } else {
                    Long afterLong = like.getCurrentLikes() + countInt;
                    after.setText(afterLong.toString() +" likes");
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();

    }
});
        coins.setText(((Global)getApplication()).getMoney());
        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global)getApplication()).setToBuy("coins");
                Intent intent = new Intent(AddCompain.this, BuyActivity.class);
                startActivity(intent);
            }
        });
        goInstant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (follow)
                ((Global)getApplication()).setToBuy("followers");
                else
                    ((Global)getApplication()).setToBuy("likes");

                Intent intent = new Intent(AddCompain.this, BuyActivity.class);
                startActivity(intent);
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final API api = APIFactory.createAPI();
            final Integer x = countInt * ((Global) getApplication()).getPerLike();
           if (countInt > 0 )
            if (follow) {
                loading.setVisibility(View.VISIBLE);
                if (Integer.parseInt((((Global) getApplication()).getMoney())) > x ){
                    SomeRandomeClass.AddToList("user_id" , ((Global) getApplication()).getCurrentUser().getPk().toString());
                    SomeRandomeClass.AddToList("media_id" ,((Global) getApplication()).getCurrentUser().getPk().toString() );
                    SomeRandomeClass.AddToList("media_image" , ((Global) getApplication()).getCurrentUser().getProfilePicUrl());
                    SomeRandomeClass.AddToList("username" , ((Global) getApplication()).getCurrentUser().getUsername());
                    SomeRandomeClass.AddToList("money" , String.valueOf(x.toString()));
                    SomeRandomeClass.AddToList("wanted" ,String.valueOf(countInt.toString()) );
                    SomeRandomeClass.AddToList("type" , "1" );
                    SomeRandomeClass.AddToList("video" , "");
                    Call<Void> call = api.addCampaign(SomeRandomeClass.GetData());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {


                                final Long sum = Long.parseLong((((Global) getApplication()).getMoney())) - Long.parseLong(x.toString());
                            ((Global)getApplication()).setMoney(sum.toString());
                            coins.setText(((Global)getApplication()).getMoney());

                            SomeRandomeClass.AddToList("userid", ((Global) getApplication()).getCurrentUser().getPk().toString());
                            SomeRandomeClass.AddToList("money", sum.toString());
                            Call<Void> call3 = api.addMoney(SomeRandomeClass.GetData());                                 call3.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                            loading.setVisibility(View.GONE);

                                            RelativeLayout m = (RelativeLayout) findViewById(R.id.awesome);
                                            TextView w = (TextView) findViewById(R.id.on_the_way);
                                            assert m != null;
                                            m.setVisibility(View.VISIBLE);
                                        GifImageView givImageView = (GifImageView)findViewById(R.id.gif);
                                        assert givImageView != null;
                                        givImageView.animate();

                                        assert w != null;
                                            w.setText("Your " + countInt.toString() + " followers are on the way.");
                                            m.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    finish();

                                                }
                                            });
                                        }


                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });

                            }


                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                } else {
                    RelativeLayout m = (RelativeLayout) findViewById(R.id.bummer);

                    loading.setVisibility(View.GONE);

                    assert m != null;
                    m.setVisibility(View.VISIBLE);

                    m.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            finish();

                        }
                    });
                }

            } else {
                loading.setVisibility(View.VISIBLE);

                if (Integer.parseInt((((Global) getApplication()).getMoney())) > x ){
                    SomeRandomeClass.AddToList("user_id" , ((Global) getApplication()).getCurrentUser().getPk().toString());
                    SomeRandomeClass.AddToList("media_id" ,((Global) getApplication()).getCurrentUser().getPk().toString() );
                    SomeRandomeClass.AddToList("media_image" , ((Global) getApplication()).getCurrentUser().getProfilePicUrl());
                    SomeRandomeClass.AddToList("username" , ((Global) getApplication()).getCurrentUser().getUsername());
                    SomeRandomeClass.AddToList("money" , String.valueOf(x.toString()));
                    SomeRandomeClass.AddToList("wanted" ,String.valueOf(countInt.toString()) );
                    SomeRandomeClass.AddToList("type" , "0" );
                    SomeRandomeClass.AddToList("video" , "");
                    Call<Void> call = api.addCampaign(SomeRandomeClass.GetData());                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                                final Long sum = Long.parseLong(((Global) getApplication()).getMoney()) - Long.parseLong(x.toString());
                            ((Global)getApplication()).setMoney(sum.toString());
                            coins.setText(((Global)getApplication()).getMoney());

                            SomeRandomeClass.AddToList("userid", ((Global) getApplication()).getCurrentUser().getPk().toString());
                            SomeRandomeClass.AddToList("money", sum.toString());
                            Call<Void> call3 = api.addMoney(SomeRandomeClass.GetData());                                call3.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                            loading.setVisibility(View.GONE);

                                            RelativeLayout m = (RelativeLayout) findViewById(R.id.awesome);
                                            TextView w = (TextView) findViewById(R.id.on_the_way);
                                            assert m != null;
                                            m.setVisibility(View.VISIBLE);
                                        GifImageView givImageView = (GifImageView)findViewById(R.id.gif);
                                        assert givImageView != null;
                                        givImageView.animate();

                                            assert w != null;
                                            w.setText("Your " + countInt.toString() + " likes are on the way.");


                                            m.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    finish();

                                                }
                                            });
                                        }


                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {


                                    }
                                });
                            }



                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                } else {
                    RelativeLayout m = (RelativeLayout) findViewById(R.id.bummer);

loading.setVisibility(View.GONE);
                    assert m != null;
                    m.setVisibility(View.VISIBLE);

                    m.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            finish();

                        }
                    });
                }
            }
        }
    });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
