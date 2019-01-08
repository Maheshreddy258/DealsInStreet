package com.deals.sine90.dealsinstreet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Categories;
import com.deals.sine90.dealsinstreet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sine90 on 5/2/2017.
 */

public class GridviewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<Categories> fashionitems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public GridviewAdapter(Context mcontext, List<Categories> fashionitems) {
        this.mContext = mcontext;
        this.fashionitems = fashionitems;
    }
    @Override
    public int getCount() {
        return fashionitems.size();
    }

    @Override
    public Object getItem(int location) {
        return fashionitems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        if (inflater == null)
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.listview, parent,false);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.img_name);
        Categories m = fashionitems.get(position);
      //  thumbNail.setImageDrawable(null);
        Picasso.with(mContext).load(m.getImage()).placeholder(R.mipmap.logo_new).fit().into(thumbNail);
      /*  imageLoader.get(m.getImage(), ImageLoader.getImageListener(thumbNail,
                R.mipmap.logo_new, android.R.drawable
                        .ic_dialog_alert));*/

   //   thumbNail.setImageUrl(m.getImage(), imageLoader);
        return convertView;
    }

}
