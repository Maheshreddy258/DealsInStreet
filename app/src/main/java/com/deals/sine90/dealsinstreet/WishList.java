package com.deals.sine90.dealsinstreet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.deals.sine90.dealsinstreet.Adapters.WishlistAdapter;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WishList extends AppCompatActivity {
    ImageView homeimage,mapimage,wishlistimage,storeimage,expireimage;
    LinearLayout home,map,wishlist,store,expire;
    TextView hometext,maptext,wishlisttext,storetext,expiretext;
    Resources res;
    SessionManagement sessionManagement;
    private static final String url="http://dealsinstores.in/voucher/web_service_deals/wishlistanddelete.php?customer_id=";
    private ProgressDialog pDialog;
    private List<Listview> list = new ArrayList<Listview>();
    private ListView listView;
    private WishlistAdapter adapter;
    private static final String TAG = Expiring.class.getSimpleName();
    String customer_id,Wishliturl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManagement = new SessionManagement(getApplicationContext());
        customer_id=sessionManagement.getUserDetails().get("customerid");
        Log.e("customerid",customer_id);
        Wishliturl=url+customer_id;
        //getting resources
        res = getApplicationContext().getResources();
        final int newColor = res.getColor(R.color.colorPrimary);
        //getting ids
        home=(LinearLayout)findViewById(R.id.home);
        homeimage=(ImageView)findViewById(R.id.homeimage);
        hometext=(TextView)findViewById(R.id.hometext);
        map=(LinearLayout)findViewById(R.id.maps);
        mapimage=(ImageView)findViewById(R.id.mapimage);
        maptext=(TextView)findViewById(R.id.maptext);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        wishlistimage=(ImageView)findViewById(R.id.wishlistimage);
        wishlisttext=(TextView)findViewById(R.id.wishlisttext);
        store=(LinearLayout)findViewById(R.id.mystore);
        storeimage=(ImageView)findViewById(R.id.mystoreimage);
        storetext=(TextView)findViewById(R.id.mystoretext);
        expire=(LinearLayout)findViewById(R.id.expiring);
        expireimage=(ImageView)findViewById(R.id.expiringimage);
        expiretext=(TextView)findViewById(R.id.expiringtext);
        //default color for wishlist icons
        wishlistimage.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
        wishlisttext.setTextColor(newColor);
        //map on clicklistener
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                maptext.setTextColor(newColor);
                hometext.setTextColor(getResources().getColor(R.color.light_blacck));
                homeimage.setColorFilter(null);
                wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
                wishlistimage.setColorFilter(null);
                storeimage.setColorFilter(null);
                storetext.setTextColor(getResources().getColor(R.color.light_blacck));
                expireimage.setColorFilter(null);
                expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
                Intent intent=new Intent(WishList.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlistimage.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                wishlisttext.setTextColor(newColor);
                hometext.setTextColor(getResources().getColor(R.color.light_blacck));
                homeimage.setColorFilter(null);
                maptext.setTextColor(getResources().getColor(R.color.light_blacck));
                mapimage.setColorFilter(null);
                storeimage.setColorFilter(null);
                storetext.setTextColor(getResources().getColor(R.color.light_blacck));
                expireimage.setColorFilter(null);
                expiretext.setTextColor(getResources().getColor(R.color.light_blacck));

            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManagement.isLoggedIn()){
                    storetext.setTextColor(newColor);
                    storeimage.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                    hometext.setTextColor(getResources().getColor(R.color.light_blacck));
                    homeimage.setColorFilter(null);
                    maptext.setTextColor(getResources().getColor(R.color.light_blacck));
                    mapimage.setColorFilter(null);
                    wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
                    wishlistimage.setColorFilter(null);
                    expireimage.setColorFilter(null);
                    expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
                    Intent intent=new Intent(WishList.this,Mystore.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(WishList.this,Login.class);
                    Toast.makeText(WishList.this, "Login to see favourite products", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }

            }
        });
        expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expiretext.setTextColor(newColor);
                expireimage.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                hometext.setTextColor(getResources().getColor(R.color.light_blacck));
                homeimage.setColorFilter(null);
                maptext.setTextColor(getResources().getColor(R.color.light_blacck));
                mapimage.setColorFilter(null);
                wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
                wishlistimage.setColorFilter(null);
                storeimage.setColorFilter(null);
                storetext.setTextColor(getResources().getColor(R.color.light_blacck));
                Intent intent=new Intent(WishList.this,Expiring.class);
                startActivity(intent);
            }
        });
        //home onclicklistener
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                hometext.setTextColor(newColor);
                maptext.setTextColor(getResources().getColor(R.color.light_blacck));
                mapimage.setColorFilter(null);
                wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
                wishlistimage.setColorFilter(null);
                storeimage.setColorFilter(null);
                storetext.setTextColor(getResources().getColor(R.color.light_blacck));
                expireimage.setColorFilter(null);
                expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
                Intent intent=new Intent(WishList.this,Main2Activity.class);
                startActivity(intent);
            }
        });
        listView = (ListView) findViewById(R.id.list);
        adapter = new WishlistAdapter(WishList.this, list);
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(WishList.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        // Creating volley request obj
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,Wishliturl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("wishlist",""+response);
                        try {
                            hidePDialog();
                            JSONArray array = response.optJSONArray("data");
                            if (array.length()==0)
                            {
                                Toast.makeText(getApplicationContext(), "No products to show", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Parsing json
                                for (int i = 0; i < array.length(); i++) {
                                    try {

                                        Listview listview = new Listview();
                                        JSONObject obj2 = array.optJSONObject(i);
                                        listview.setTitle(obj2.getString("p_name"));
                                        listview.setImage(obj2.getString("img_name"));
                                        listview.setProductid(obj2.getString("product_id"));
                                        listview.setWishlistid(obj2.getString("wishlist_item_id"));
                                        list.add(listview);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }
    @Override
    protected void onRestart() {
        final int newColor = res.getColor(R.color.colorPrimary);
        wishlistimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        wishlisttext.setTextColor(newColor);
        expireimage.setColorFilter(null);
        expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
        hometext.setTextColor(getResources().getColor(R.color.light_blacck));
        homeimage.setColorFilter(null);
        storeimage.setColorFilter(null);
        storetext.setTextColor(getResources().getColor(R.color.light_blacck));
        maptext.setTextColor(getResources().getColor(R.color.light_blacck));
        mapimage.setColorFilter(null);
        super.onRestart();
    }
}
