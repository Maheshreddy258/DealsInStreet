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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.deals.sine90.dealsinstreet.BannerDetails;
import com.deals.sine90.dealsinstreet.DirectionActivity;
import com.deals.sine90.dealsinstreet.Expiring;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.Login;
import com.deals.sine90.dealsinstreet.R;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;
import com.deals.sine90.dealsinstreet.StoreDescription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sine90 on 6/23/2017.
 */

public class StoreListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Listview> listitems;
    Resources res;
    Listview m;
    SessionManagement sessionManagement;
    String url="http://dealsinstores.in/voucher/web_service_deals/liked_file.php";
    String addwishurl="&product_id=";
    int position1;
    NetworkImageView thumbNail;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private ArrayList<HashMap<String, String>> mData;
    private static final String TAG = Expiring.class.getSimpleName();
    public StoreListAdapter(Activity activity, List<Listview> listitems) {
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
            convertView = inflater.inflate(R.layout.storelistmodel, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        sessionManagement = new SessionManagement(activity);
        res = activity.getResources();
        final int newColor = res.getColor(R.color.like_color);
        //thumbNail = (NetworkImageView) convertView
     /*           .findViewById(R.id.image);
        final ImageView like_image=(ImageView)convertView.findViewById(R.id.like);
        final TextView like=(TextView)convertView.findViewById(R.id.liketext);*/
        final TextView shopname=(TextView)convertView.findViewById(R.id.storename);
        final LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.layout);
     //   final TextView distnace=(TextView)convertView.findViewById(R.id.distnace);
        final TextView city=(TextView)convertView.findViewById(R.id.address);
        final Button route=(Button)convertView.findViewById(R.id.route);
      /*  final TextView expdate=(TextView)convertView.findViewById(R.id.expdate);
        final LinearLayout ll1=(LinearLayout)convertView.findViewById(R.id.linear_like);
        final LinearLayout share=(LinearLayout)convertView.findViewById(R.id.share);*/
        m = listitems.get(position);
        shopname.setText(m.getTitle());
     //   distnace.setText(m.getDistance());
        city.setText(m.getCity());

      /*  ll1.setOnClickListener(new View.OnClickListener() {
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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Amazing offer waiting for you,download the Deals in street app to get the offer";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        thumbNail.setImageUrl(m.getImage(), imageLoader);

        thumbNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position1=position;
                String productid=listitems.get(position1).getProductid();
                Log.e("productid",productid);
                String title=listitems.get(position).getTitle();
                Log.e("title",title);
                Intent intent=new Intent(activity,BannerDetails.class);
                intent.putExtra("Productname",title);
                intent.putExtra("productid",productid);

                activity. startActivity(intent);
            }
        });*/
      layout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String productid=listitems.get(position1).getProductid();
              String title=listitems.get(position).getTitle();
              String img=listitems.get(position1).getImage();
              String city=listitems.get(position1).getCity();
              String description=listitems.get(position1).getDescription();
              String latitude=listitems.get(position1).getLatitude();
              String longnitude=listitems.get(position1).getLongnitude() ;
              String mondaystatus=listitems.get(position1).getMonday();
              String tuesdaystatus=listitems.get(position1).getTuesday();
              String wednesdatstatus=listitems.get(position1).getWednesday();
              String thus_status=listitems.get(position1).getThursday();
              String fridaystatus=listitems.get(position1).getFriday();
              String satstatus=listitems.get(position1).getSaturday();
              String sundaystatus=listitems.get(position1).getSunday();
              String  redeem_head=listitems.get(position1).getRedeem_head();
              String redeem_text=listitems.get(position1).getRedeem_text();
              String  term_head=listitems.get(position1).getTerm_head();
              String  t1=listitems.get(position1).getT1();
              String   t2=listitems.get(position1).getT2();
              String t3=listitems.get(position1).getT3();
              String   t4=listitems.get(position1).getT4();
              String coupon_head=listitems.get(position1).getCoupon_head();
              String  coupon_text=listitems.get(position1) .getCoupon_text();
              Intent intent=new Intent(activity,StoreDescription.class);
              intent.putExtra("Productname",title);
              intent.putExtra("productid",productid);
              intent.putExtra("img",img);
              intent.putExtra("city",city);
              intent.putExtra("description",description);
              intent.putExtra("latitude",latitude);
              intent.putExtra("longnitude",longnitude);
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
              activity. startActivity(intent);
          }
      });
        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude=listitems.get(position1).getLatitude();
                String longnitude=listitems.get(position1).getLongnitude() ;
                String title=listitems.get(position).getTitle();
                Intent intent=new Intent(activity,DirectionActivity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longnitude",longnitude);
                intent.putExtra("name",title);
                activity. startActivity(intent);
            }
        });
        return convertView;
    }
    public void addtowish()
    {
     final    String productid=listitems.get(position1).getProductid();
     final    String customerid=sessionManagement.getUserDetails().get("customerid");
        Log.e("customerid",customerid);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("storeslikereponse",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("data").equals("Successfully liked")){
                                Toast.makeText(activity, "Successfully added to favourites", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity,error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("customer_id",customerid);
                params.put("storelocator_id",productid);
                Log.e("storeslikparams",""+params);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);

    }

}
