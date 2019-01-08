package com.deals.sine90.dealsinstreet.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.deals.sine90.dealsinstreet.Expiring;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.Login;
import com.deals.sine90.dealsinstreet.Productdescription;
import com.deals.sine90.dealsinstreet.R;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;
import com.deals.sine90.dealsinstreet.StoreList;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sine90 on 6/23/2017.
 */

public class NearByAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Listview> listitems;
    Resources res;
    Listview m;
    SessionManagement sessionManagement;
    String url="http://dealsinstores.in/voucher/web_service_deals/wishlistanddelete.php?customer_id=";
    String addwishurl="&product_id=";
    int position1;
    NetworkImageView thumbNail;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private ArrayList<HashMap<String, String>> mData;
    private static final String TAG = Expiring.class.getSimpleName();
    public NearByAdapter(Activity activity, List<Listview> listitems) {
        this.activity = activity;
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
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.nearbymodel, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        sessionManagement = new SessionManagement(activity);
        res = activity.getResources();
        final int newColor = res.getColor(R.color.like_color);
        thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.image);
     final ImageView   like_image=(ImageView)convertView.findViewById(R.id.like);
     final   TextView like=(TextView)convertView.findViewById(R.id.liketext);
        final   TextView shopname=(TextView)convertView.findViewById(R.id.shopname);
        final   TextView distnace=(TextView)convertView.findViewById(R.id.distnace);
        final   TextView city=(TextView)convertView.findViewById(R.id.city);
        final   TextView expdate=(TextView)convertView.findViewById(R.id.expdate);
        final TextView count=(TextView)convertView.findViewById(R.id.count);
        final NetworkImageView logo=(NetworkImageView)convertView.findViewById(R.id.logo);
        final LinearLayout ll1=(LinearLayout)convertView.findViewById(R.id.linear_like);
        final LinearLayout more=(LinearLayout) convertView.findViewById(R.id.more);
        final LinearLayout direction=(LinearLayout)convertView.findViewById(R.id.direction);
        final LinearLayout share=(LinearLayout)convertView.findViewById(R.id.share);
        m = listitems.get(position);
        shopname.setText(m.getTitle());
        distnace.setText(m.getDistance());
        city.setText(m.getAddress());
        count.setText(m.getCount());
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sessionManagement.isLoggedIn()==true)
                {
                    like_image.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                    like.setTextColor(newColor);
                    position1=position;
                    addtowish();
                }
                else {
                    Intent intent=new Intent(activity, Login.class);
                    activity.startActivity(intent);
                    Toast.makeText(activity, "Login to like the product", Toast.LENGTH_SHORT).show();
                }

            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position1=position;
                String productid=listitems.get(position1).getProductid();
                String title=listitems.get(position).getTitle();
                Intent intent=new Intent(activity,Productdescription.class);
                intent.putExtra("Productname",title);
                intent.putExtra("productid",productid);
                activity.startActivity(intent);
            }
        });
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=listitems.get(position1).getTitle();
                String latitude=listitems.get(position1).getLatitude();
                String longnitude=listitems.get(position).getLongnitude();
                Intent intent=new Intent(activity, StoreList.class);
                intent.putExtra("name",name);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longnitude",longnitude);
                activity.startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Amazing offer waiting for you,download the Deals in Stores app to get the offer https://play.google.com/store/apps/details?id=com.deals.sine90.dealsinstreet";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        thumbNail.setImageUrl(m.getImage(), imageLoader);
        logo.setImageUrl(m.getStore_img(), imageLoader);

        thumbNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position1=position;
                String productid=listitems.get(position1).getProductid();
                Log.e("productid",productid);
                String title=listitems.get(position).getTitle();
                Log.e("title",title);
                Intent intent=new Intent(activity,Productdescription.class);
                intent.putExtra("Productname",title);
                intent.putExtra("productid",productid);

                activity. startActivity(intent);
            }
        });
        return convertView;
    }
    public void addtowish()
    {
        String productid=listitems.get(position1).getProductid();
        String customerid=sessionManagement.getUserDetails().get("customerid");
        Log.e("customerid",customerid);
        String wishurl=url+customerid+addwishurl+productid;

        Log.e("wishurl",wishurl);
        // Creating volley request obj
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,wishurl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("wishrespone",""+response);
                        try {

                            String data=response.getString("data");
                            if (data.equals("null")){
                                Toast.makeText(activity, "This product is already liked", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(activity, "Added to wishlist successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        // notifying list adapter about data changes

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());


            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

    }

}
