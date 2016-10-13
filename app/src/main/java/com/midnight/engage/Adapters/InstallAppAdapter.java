package com.midnight.engage.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.midnight.engage.Models.appToInstall;
import com.midnight.engage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adi on 6/11/16.
 */
public class InstallAppAdapter extends BaseAdapter {
    Context context;

    public InstallAppAdapter(Context context, List<appToInstall> list ) {
        this.context = context;
        this.list = list;
    }

    List<appToInstall> list;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.install_app_cell , parent , false);

        final appToInstall item  = (appToInstall) getItem(position);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.appimage);
        TextView price = (TextView) rowView.findViewById(R.id.appprice);
        Picasso.with(context).load(item.getImage()).into(imageView);
        price.setText(item.getMoney() + " coins");
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + item.getPack())));
                } catch (android.content.ActivityNotFoundException anfe) {

                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + item.getPack())));
                }
            }
        });

        return rowView;
    }

}
