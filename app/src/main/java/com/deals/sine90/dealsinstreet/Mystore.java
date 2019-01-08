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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.deals.sine90.dealsinstreet.Adapters.GridviewAdapter;
import com.deals.sine90.dealsinstreet.Adapters.MyStoreAdapter;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Categories;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mystore extends AppCompatActivity {
    ImageView homeimage,mapimage,wishlistimage,storeimage,expireimage;
    LinearLayout home,map,wishlist,store,expire;
    TextView hometext,maptext,wishlisttext,storetext,expiretext;
    Resources res;
    SessionManagement sessionManagement;
    private GridView gridView;
    private List<Categories> categoriesList = new ArrayList<Categories>();
    private MyStoreAdapter adapter;
    private ProgressDialog pDialog;
    String entityid,childrencount,category_name,userid,likedstores;
    private static final String TAG = Main2Activity.class.getSimpleName();
    private static final String URL="http://dealsinstores.in/voucher/web_service_deals/all_liked_store.php?customer_id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystore);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManagement = new SessionManagement(getApplicationContext());
        userid=sessionManagement.getUserDetails().get("customerid");
        likedstores=URL+userid;
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
        //get default color for mystore icon text
        storetext.setTextColor(newColor);
        storeimage.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
        getlikeddata();
        gridView=(GridView)findViewById(R.id.grid);
        adapter = new MyStoreAdapter(getApplicationContext(), categoriesList);
        gridView.setAdapter(adapter);

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
                Intent intent=new Intent(Mystore.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManagement.isLoggedIn())
                {
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
                    Intent intent=new Intent(Mystore.this,WishList.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(Mystore.this,Login.class);
                    startActivity(intent);
                    Toast.makeText(Mystore.this, "Login to see wislist products", Toast.LENGTH_SHORT).show();
                }


            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent=new Intent(Mystore.this,Expiring.class);
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
                Intent intent=new Intent(Mystore.this,Main2Activity.class);
                startActivity(intent);
            }
        });
    }
    public void getlikeddata(){
        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("storesreponse",response);
                        try {
                            Categories categories;
                            JSONObject object=new JSONObject(response);
                            JSONArray jsonArray=object.optJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++){
                                categories = new Categories();
                                JSONObject obj2 = jsonArray.optJSONObject(i);
                                categories.setCity(obj2.getString("city"));
                                categories.setLattitude(obj2.getString("latitude"));
                                categories.setLangitude(obj2.getString("longtitude"));
                                categories.setImage(obj2.getString("img"));
                                categories.setName(obj2.getString("name"));
                                categories.setDescription(obj2.getString("description"));
                                categories.setStorelocator_id(obj2.getString("storelocator_id"));
                                categories.setMonday(obj2.getString("monday_status"));
                                categories.setTuesday(obj2.getString("tuesday_status"));
                                categories.setWednesday(obj2.getString("wednesday_status"));
                                categories.setThursday(obj2.getString("thursday_status"));
                                categories.setFriday(obj2.getString("friday_status"));
                                categories.setSaturday(obj2.getString("saturday_status"));
                                categories.setSunday(obj2.getString("sunday_status"));
                                categories.setRedeem_head(obj2.getString("redeem_head"));
                                categories.setRedeem_text(obj2.getString("redeem_text"));
                                categories.setTerm_head(obj2.getString("term_head"));
                                categories.setT1(obj2.getString("t1"));
                                categories.setT2(obj2.getString("t2"));
                                categories.setT3(obj2.getString("t3"));
                                categories.setT4(obj2.getString("t4"));
                                categories.setProductimages(obj2.getString("product_images"));
                                categories.setCoupon_head(obj2.getString("coupon_head"));
                                categories.setCoupon_text(obj2.getString("coupon_text"));
                                categoriesList.add(categories);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("customer_id",userid);
                Log.e("sshs",""+params);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        // Adding request to request queue*/
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,likedstores,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray array = response.optJSONArray("data");
                            if (array.length()==0)
                            {
                                Toast.makeText(getApplicationContext(), "No products to show", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Parsing json
                                for (int i = 0; i < array.length(); i++) {
                                    Categories categories;
                                    JSONObject obj2 = array.optJSONObject(i);
                                    categories = new Categories();
                                    categories.setCity(obj2.getString("city"));
                                    categories.setLattitude(obj2.getString("latitude"));
                                    categories.setLangitude(obj2.getString("longtitude"));
                                    categories.setImage(obj2.getString("img"));
                                    categories.setName(obj2.getString("name"));
                                    categories.setDescription(obj2.getString("description"));
                                    categories.setStorelocator_id(obj2.getString("storelocator_id"));
                                    categories.setMonday(obj2.getString("monday_status"));
                                    categories.setTuesday(obj2.getString("tuesday_status"));
                                    categories.setWednesday(obj2.getString("wednesday_status"));
                                    categories.setThursday(obj2.getString("thursday_status"));
                                    categories.setFriday(obj2.getString("friday_status"));
                                    categories.setSaturday(obj2.getString("saturday_status"));
                                    categories.setSunday(obj2.getString("sunday_status"));
                                    categories.setRedeem_head(obj2.getString("redeem_head"));
                                    categories.setRedeem_text(obj2.getString("redeem_text"));
                                    categories.setTerm_head(obj2.getString("term_head"));
                                    categories.setT1(obj2.getString("t1"));
                                    categories.setT2(obj2.getString("t2"));
                                    categories.setT3(obj2.getString("t3"));
                                    categories.setT4(obj2.getString("t4"));
                                    categories.setProductimages(obj2.getString("product_images"));
                                    categories.setCoupon_head(obj2.getString("coupon_head"));
                                    categories.setCoupon_text(obj2.getString("coupon_text"));
                                    categoriesList.add(categories);



                                }
                            }

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        //adapter.notifyDataSetChanged();
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
    @Override
    public void onBackPressed() {
        //Execute your code here
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        finish();

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
        storeimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        storetext.setTextColor(newColor);
        expireimage.setColorFilter(null);
        expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
        hometext.setTextColor(getResources().getColor(R.color.light_blacck));
        homeimage.setColorFilter(null);
        wishlistimage.setColorFilter(null);
        wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
        maptext.setTextColor(getResources().getColor(R.color.light_blacck));
        mapimage.setColorFilter(null);
        super.onRestart();
    }
}
