package com.deals.sine90.dealsinstreet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

public class SubcategoryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Categories> listitems;

    private Context mContext;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private static final String TAG = "WishlistAdapter";
    public SubcategoryAdapter(Context mcontext, List<Categories> listitems) {
        this.mContext = mcontext;
        this.listitems = listitems;
    }

    @Override
    public int getCount() {
        return listitems.size();
    }

    @Override
    public Object getItem(int location) {
        return listitems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.sublistmodel, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

     ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.image);
        TextView productname=(TextView)convertView.findViewById(R.id.category_name);
        Categories m = listitems.get(position);
        Picasso.with(mContext).load(m.getImage()).placeholder(R.mipmap.logo_new).fit().into(thumbNail);
       /* imageLoader.get(m.getImage(), ImageLoader.getImageListener(thumbNail,
                R.mipmap.logo_new, android.R.drawable
                        .ic_dialog_alert));*/
       // thumbNail.setImageUrl(m.getImage(), imageLoader);
        productname.setText(m.getCategory_name());
        return convertView;
    }
}
