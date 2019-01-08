package com.deals.sine90.dealsinstreet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Categories;
import com.deals.sine90.dealsinstreet.R;
import com.deals.sine90.dealsinstreet.StoreDescription;

import java.util.List;

/**
 * Created by sine90 on 6/23/2017.
 */

public class MyStoreAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<Categories> fashionitems;
    String product_images,redeem_head,redeem_text,term_head,t1,t2,t3,t4,coupon_head,coupon_text,description,productname,productid,img,city,lattitude,longtitude,mondaystatus,tuesdaystatus,wednesdatstatus,thus_status,fridaystatus,satstatus,sundaystatus;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public MyStoreAdapter(Context mcontext, List<Categories> fashionitems) {
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
            convertView = inflater.inflate(R.layout.mystoremodel, parent,false);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView ) convertView
                .findViewById(R.id.img_name);
        TextView name=(TextView)convertView.findViewById(R.id.shopname);
        TextView city1=(TextView)convertView.findViewById(R.id.city);
         LinearLayout linearLayout=(LinearLayout)convertView.findViewById(R.id.listv);
        Categories m = fashionitems.get(position);
        thumbNail.setImageDrawable(null);
        imageLoader.get(m.getImage(), ImageLoader.getImageListener(thumbNail,
                R.mipmap.logo_new, android.R.drawable
                        .ic_dialog_alert));
        thumbNail.setImageUrl(m.getImage(), imageLoader);
        name.setText(m.getName());
        city1.setText(m.getCity());
        productid=m.getProductid();
        productname=m.getName();
        img=m.getImg();
        Log.e("img",""+img);
        city=m.getCity();
        description=m.getDescription();
        lattitude=m.getLattitude();
        longtitude=m.getLangitude();
        mondaystatus=m.getMonday();
        tuesdaystatus=m.getTuesday();
        wednesdatstatus=m.getWednesday();
        thus_status=m.getThursday();
        fridaystatus=m.getFriday();
        satstatus=m.getSaturday();
        sundaystatus=m.getSunday();
        redeem_head=m.getRedeem_head();
        redeem_text=m.getRedeem_text();
        term_head=m.getTerm_head();
        t1=m.getT1();
        t2=m.getT2();
        t3=m.getT3();
        t4=m.getT4();
        coupon_head=m.getCoupon_head();
        coupon_text=m.getCoupon_text();
        product_images=m.getProductimages();
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("product_images",product_images);
                Intent intent = new Intent(mContext, StoreDescription.class);
                intent.putExtra("Productname",productname);
                intent.putExtra("productid",productid);
                intent.putExtra("img",product_images);
                intent.putExtra("city",city);
                intent.putExtra("description",description);
                intent.putExtra("latitude",lattitude);
                intent.putExtra("longnitude",longtitude);
                intent.putExtra("mondaystatus",mondaystatus);
                intent.putExtra("tuesdaystatus",tuesdaystatus);
                intent.putExtra("wednesdatstatus",wednesdatstatus);
                intent.putExtra("thus_status",thus_status);
                intent.putExtra("fridaystatus",fridaystatus);
                intent.putExtra("satstatus",satstatus);
                intent.putExtra("sundaystatus",sundaystatus);
                intent.putExtra("redeem_head",redeem_head);
                intent.putExtra("term_head",term_head);
                intent.putExtra("redeem_text",redeem_text);
                intent.putExtra("t1",t1);
                intent.putExtra("t2",t2);
                intent.putExtra("t3",t3);
                intent.putExtra("t4",t4);
                intent.putExtra("coupon_head",coupon_head);
                intent.putExtra("coupon_text",coupon_text);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

}
