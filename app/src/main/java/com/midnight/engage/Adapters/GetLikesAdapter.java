package com.midnight.engage.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.midnight.engage.AddCompain;
import com.midnight.engage.Fragments.GetLikesFragment;
import com.midnight.engage.Global;
import com.midnight.engage.Models.MediaForMyMedia;
import com.midnight.engage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adi on 6/3/16.
 */
public class GetLikesAdapter extends BaseAdapter {
    List<MediaForMyMedia> list;
    Fragment fr;
    Context context;
    String max_id;
    int pas = 16;

    public GetLikesAdapter(List<MediaForMyMedia> list, Fragment fr, Context context , String max_id) {
        this.list = list;
        this.fr = fr;
        this.context = context;
        this.max_id  =max_id;
    }

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (context == null)
            return null;
        if (!max_id.equals("") && position > pas) {
            ((GetLikesFragment) fr).fill();
            notifyDataSetChanged();
            pas = pas * 2;
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.get_likes_greed_cell , parent , false);
        final MediaForMyMedia item  = (MediaForMyMedia) getItem(position);
        final ImageView image = (ImageView) rowView.findViewById(R.id.mymedia_image);

            Picasso.with(context).load(item.getImageUrl()).into(image);


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global) context.getApplicationContext()).setToLike(item);
                ((Global) context.getApplicationContext()).setFollow(false);
                Intent intent = new Intent(context, AddCompain.class);
                context.startActivity(intent);
//                MenuItemFragment fragment = new MenuItemFragment();
//                SharedPreferences sharedpreferences = context.getSharedPreferences("",Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString("selectedCategory", item.getUrlCategory());
//                editor.putInt("categorySelectedId" , item.getId());
//                editor.commit();
//
//                fr.getFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, fragment, fragment.getClass().getName())
//                        .addToBackStack(fragment.getClass().getName())
//                        .commit();
            }
        });


        return rowView;

    }
}
