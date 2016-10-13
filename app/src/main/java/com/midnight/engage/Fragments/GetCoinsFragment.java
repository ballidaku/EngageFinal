package com.midnight.engage.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyber.Fyber;
import com.fyber.ads.AdFormat;
import com.fyber.requesters.InterstitialRequester;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.loopj.android.http.*;
import com.midnight.engage.API.API;
import com.midnight.engage.API.APIFactory;
import com.midnight.engage.API.toLikeResponse;
import com.midnight.engage.API.toLikelvl1;
import com.midnight.engage.BuyActivity;
import com.midnight.engage.Global;
import com.midnight.engage.InstagramRequestClass;
import com.midnight.engage.MainActivity;
import com.midnight.engage.R;
import com.midnight.engage.SomeRandomeClass;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adi on 6/3/16.
 */
public class GetCoinsFragment extends Fragment {
    ImageView image;
    ImageView mask;

    private static AsyncHttpClient client = new AsyncHttpClient();
    PersistentCookieStore cookieStore;

    TextView username;
    List<toLikeResponse> likeResponses;
    Button skip;
    Button like;
    Button buy ;
    Integer pas = 0;
    int pos = 1;
    RequestCallback requestCallback;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cookieStore = new PersistentCookieStore(getActivity());

        client.addHeader("User-Agent", "Instagram 9.3.0 Android (18/4.3; 320dpi; 720x1280; Xiaomi; HM 1SW; armani; qcom; en_US)");
        client.setCookieStore(cookieStore);



        View view = inflater.inflate(R.layout.get_coins_fragment , null);
        image = (ImageView) view.findViewById(R.id.to_like_content_image);
        mask = (ImageView) view.findViewById(R.id.mask_getcoins);
        buy = (Button) view.findViewById(R.id.get_likes_get_coins_btn);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global)getActivity().getApplication()).setToBuy("coins");
                getActivity().finish();
                Intent intent = new Intent(getActivity(), BuyActivity.class);
                startActivity(intent);
            }
        });
        username = (TextView) view.findViewById(R.id.tofollowusername);
        skip = (Button) view.findViewById(R.id.get_likes_skip_btn);
        like = (Button) view.findViewById(R.id.get_likes_like_btn);
        Fyber.with("45471", getActivity()).withSecurityToken("7ad34061a319985f29d0ac2155cbb19f")
                .start();

        MobileAds.initialize(getActivity().getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        requestCallback = new RequestCallback() {
            @Override
            public void onAdAvailable(Intent intent) {
            }

            @Override
            public void onAdNotAvailable(AdFormat adFormat) {
            }

            @Override
            public void onRequestError(RequestError requestError) {
            }
        };
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos < likeResponses.size()) {

                    skipp(pos);
                    fill(pos);

                    pos++;

                }
                pas ++;
                if (((Global)getActivity().getApplication()).getFubershow()==pas) {
                    InterstitialRequester.create(requestCallback)
                            .request(getActivity());
                    pas = 0;
                }
            }
        });

        API service = APIFactory.createAPI();

        ( (MainActivity)getActivity()).setLoading(true);

        SomeRandomeClass.AddToList("user_id", ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());
        Call<toLikelvl1> call = service.toLike(SomeRandomeClass.GetData());
        call.enqueue(new Callback<toLikelvl1>() {
            @Override
            public void onResponse(Call<toLikelvl1> call, Response<toLikelvl1> response) {
                if (response.body() != null) {

                    if (response.body().getLikeResponses().size() != 0) {
                        likeResponses = response.body().getLikeResponses();
                        fill(0);

                        ((MainActivity) getActivity()).likesEnable();
                        ((MainActivity) getActivity()).followersEnable();
                        ((MainActivity) getActivity()).setLoading(false);


                    } else {
                        username.setVisibility(View.VISIBLE);
                        username.setText("Nothing to like");
                        skip.setEnabled(false);
                        like.setEnabled(false);
                        ((MainActivity) getActivity()).likesEnable();

                        ((MainActivity) getActivity()).followersEnable();
                        ((MainActivity) getActivity()).setLoading(false);




                    }
                }
            }

            @Override
            public void onFailure(Call<toLikelvl1> call, Throwable t) {
                ((MainActivity) getActivity()).likesEnable();
                ((MainActivity) getActivity()).followersEnable();
                ((MainActivity) getActivity()).setLoading(false);

            }
        });
        return view;
    }
    void  fill(final int pos) {

        Picasso.with(getActivity()).load(likeResponses.get(pos).getImage()).error(R.drawable.image_not_found).into(image);
        if (likeResponses.get(pos).getType().equals("1")) {
            mask.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            username.setText("@"+likeResponses.get(pos).getUsername());
            like.setText("FOLLOW");
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pas ++;
                    if (((Global)getActivity().getApplication()).getFubershow()==pas) {
                        InterstitialRequester.create(requestCallback)
                                .request(getActivity());
                        pas = 0;
                    }
                    final API service = APIFactory.createAPI();
dop();
                    ( (MainActivity)getActivity()).setLoading(true);

                            try {
                                //InstagramPostHelper.getInstance().follow(likeResponses.get(pos).getId(), ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());
                                InstagramRequestClass.getInstance().follow(likeResponses.get(pos).getId(), ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString(), cookieStore, client);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    Long sum = ((Global) getActivity().getApplication()).getGetCoinsPerLike() + Long.parseLong((((Global) getActivity().getApplication()).getMoney()));
                                ((Global)getActivity().getApplication()).setMoney(sum.toString());
                                ((MainActivity) getActivity()).updateCoins(sum.toString());
                    SomeRandomeClass.AddToList("userid", ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());
                    SomeRandomeClass.AddToList("money", sum.toString());
                    Call<Void> call3 = service.addMoney(SomeRandomeClass.GetData());
                              call3.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        SomeRandomeClass.AddToList("user_id", ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());
                                        SomeRandomeClass.AddToList("media_id", likeResponses.get(pos).getId());
                                        SomeRandomeClass.AddToList("like", "1");
                                        SomeRandomeClass.AddToList("moneyForLike", "2");
                                        SomeRandomeClass.AddToList("type", likeResponses.get(pos).getType());
                                        Call<Void> last = service.addAction(SomeRandomeClass.GetData());
                                           last.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    if (pos + 1 == likeResponses.size()) {
                                                        SomeRandomeClass.AddToList("user_id", ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());

                                                        Call<toLikelvl1> callx = service.toLike(SomeRandomeClass.GetData());
                                                        callx.enqueue(new Callback<toLikelvl1>() {
                                                            @Override
                                                            public void onResponse(Call<toLikelvl1> call, Response<toLikelvl1> response) {
                                                                if (response.body() != null) {

                                                                    if (response.body().getLikeResponses().size() != 0) {
                                                                        likeResponses = response.body().getLikeResponses();
                                                                        fill(0);
                                                                        ((MainActivity) getActivity()).setLoading(false);

                                                                    } else {
                                                                        username.setVisibility(View.VISIBLE);
                                                                        username.setText("Nothing to like");
                                                                        skip.setEnabled(false);
                                                                        like.setEnabled(false);
                                                                        ((MainActivity) getActivity()).setLoading(false);

                                                                    }


                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<toLikelvl1> call, Throwable t) {
                                                                ((MainActivity) getActivity()).setLoading(false);

                                                            }
                                                        });
                                                    } else {
                                                        fill(pos + 1);
                                                        ((MainActivity) getActivity()).setLoading(false);
                                                    }

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
                            }




            });
        } else  {
            mask.setVisibility(View.GONE);

            username.setVisibility(View.INVISIBLE);
            like.setText("LIKE");
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pas ++;
                    if (((Global)getActivity().getApplication()).getFubershow()==pas) {
                        InterstitialRequester.create(requestCallback)
                                .request(getActivity());
                        pas = 0;
                    }
                    final API service = APIFactory.createAPI();
dop();
                    ( (MainActivity)getActivity()).setLoading(true);
                            try {
                                //InstagramPostHelper.getInstance().like(likeResponses.get(pos).getId(), ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());
                                InstagramRequestClass.getInstance().like(likeResponses.get(pos).getId(), ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString(), cookieStore, client);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                                Long sum = ((Global) getActivity().getApplication()).getGetCoinsPerLike() + Long.parseLong((((Global) getActivity().getApplication()).getMoney()));
                                ((MainActivity) getActivity()).updateCoins(sum.toString());
                            ((Global)getActivity().getApplication()).setMoney(sum.toString());
                    SomeRandomeClass.AddToList("userid", ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());
                    SomeRandomeClass.AddToList("money", sum.toString());
                    Call<Void> call3 = service.addMoney(SomeRandomeClass.GetData());
                    call3.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        SomeRandomeClass.AddToList("user_id", ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());
                                        SomeRandomeClass.AddToList("media_id", likeResponses.get(pos).getId());
                                        SomeRandomeClass.AddToList("like", "1");
                                        SomeRandomeClass.AddToList("moneyForLike", "2");
                                        SomeRandomeClass.AddToList("type", likeResponses.get(pos).getType());
                                        Call<Void> last = service.addAction(SomeRandomeClass.GetData());                                            last.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {

                                                        if (pos + 1 == likeResponses.size()) {
                                                            SomeRandomeClass.AddToList("user_id", ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());

                                                            Call<toLikelvl1> callx = service.toLike(SomeRandomeClass.GetData());                                                            callx.enqueue(new Callback<toLikelvl1>() {
                                                                @Override
                                                                public void onResponse(Call<toLikelvl1> call, Response<toLikelvl1> response) {
                                                                    if (response.body() != null) {

                                                                        if (response.body().getLikeResponses().size() != 0) {
                                                                            likeResponses = response.body().getLikeResponses();
                                                                            fill(0);
                                                                            ((MainActivity) getActivity()).setLoading(false);
                                                                        } else {
                                                                            username.setVisibility(View.VISIBLE);
                                                                            username.setText("Nothing to like");
                                                                            skip.setEnabled(false);
                                                                            like.setEnabled(false);
                                                                            ((MainActivity) getActivity()).setLoading(false);

                                                                        }

                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<toLikelvl1> call, Throwable t) {
                                                                    ((MainActivity) getActivity()).setLoading(false);

                                                                }
                                                            });
                                                        } else {
                                                            fill(pos + 1);
                                                            ((MainActivity) getActivity()).setLoading(false);
                                                        }


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
                            }


            });

        }
    }
    void skipp(int pos) {

        final API service = APIFactory.createAPI();
        SomeRandomeClass.AddToList("user_id", ((Global) getActivity().getApplication()).getCurrentUser().getPk().toString());
        SomeRandomeClass.AddToList("media_id", likeResponses.get(pos).getId());
        SomeRandomeClass.AddToList("like", "0");
        SomeRandomeClass.AddToList("moneyForLike", likeResponses.get(pos).getMoney());
        SomeRandomeClass.AddToList("type", likeResponses.get(pos).getType());
        Call<Void> last = service.addAction(SomeRandomeClass.GetData());        last.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }


    public  void  dop(){
        ((MainActivity) getActivity()).setLoading(true);

        like.setEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                like.setEnabled(true);
                ((MainActivity) getActivity()).setLoading(false);

            }
        }, 1000);


    }

}
