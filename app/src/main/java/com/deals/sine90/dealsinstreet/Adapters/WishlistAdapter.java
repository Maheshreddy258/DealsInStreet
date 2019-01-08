package com.deals.sine90.dealsinstreet.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.Productdescription;
import com.deals.sine90.dealsinstreet.R;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;
import com.deals.sine90.dealsinstreet.WishList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by sine90 on 4/1/2017.
 */

public class WishlistAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Listview> listitems;
    int position1;
    String url="http://dealsinstores.in/voucher/web_service_deals/wishlistanddelete.php?customer_id=";
    String deleteurl="&delete_id=";
    SessionManagement sessionManagement;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private static final String TAG = "WishlistAdapter";
    public WishlistAdapter(Activity activity, List<Listview> listitems) {
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
            convertView = inflater.inflate(R.layout.wishlist_model, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        sessionManagement = new SessionManagement(activity);
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.image);
        TextView productname=(TextView)convertView.findViewById(R.id.product_name);
        ImageView delete=(ImageView)convertView.findViewById(R.id.delete);
        LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.layout);
        Listview m = listitems.get(position);

        thumbNail.setImageUrl(m.getImage(), imageLoader);
        productname.setText(m.getTitle());
        final String productid=m.getProductid();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position1=position;
                deletewish();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, Productdescription.class);
                intent.putExtra("productid",productid);
                activity.startActivity(intent);


            }
        });
      /*  title.setText(m.getTitle());
        value.setText(m.getValue());
        price.setText(m.getPrice());
        final_price.setText(m.getFinal_price());*/





        return convertView;
    }
    public void deletewish()
    {
        String productid=listitems.get(position1).getWishlistid();
        String customerid=sessionManagement.getUserDetails().get("customerid");
        String wishurl=url+customerid+deleteurl+productid;
        // Creating volley request obj
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,wishurl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array = response.optJSONArray("data");
                        /*  Favourites fragment=new Favourites();
                            android.app.FragmentTransaction fragmentTransaction = activity.getS().beginTransaction();
                            fragmentTransaction.replace(android.R.id.content, fragment);
                            fragmentTransaction.commit();*/
                            Intent intent=new Intent(activity, WishList.class);
                            activity.startActivity(intent);
                            activity.finish();

                            // Parsing json
                            for (int i = 0; i < array.length(); i++) {

                                Listview listview = new Listview();
                                JSONObject obj2 = array.optJSONObject(i);

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
