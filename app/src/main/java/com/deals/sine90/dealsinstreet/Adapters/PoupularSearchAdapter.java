package com.deals.sine90.dealsinstreet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.Productdescription;
import com.deals.sine90.dealsinstreet.R;

import java.util.List;

/**
 * Created by sine90 on 6/30/2017.
 */

public class PoupularSearchAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Listview> listitems;
    Listview m;
    private Context mContext;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private static final String TAG = "WishlistAdapter";
    public PoupularSearchAdapter(Context mcontext, List<Listview> listitems) {
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
            convertView = inflater.inflate(R.layout.searchlist, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

       /* ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.image);*/
        TextView productname=(TextView)convertView.findViewById(R.id.product_name);
        LinearLayout listview=(LinearLayout)convertView.findViewById(R.id.listview);
        m = listitems.get(position);
        productname.setText(m.getSearchhistory());
        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Productdescription.class);
                intent.putExtra("productid",listitems.get(position).getProductid());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
}
