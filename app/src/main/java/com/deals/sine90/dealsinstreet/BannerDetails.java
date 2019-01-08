package com.deals.sine90.dealsinstreet;

import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.deals.sine90.dealsinstreet.Fragments.Maps;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

public class BannerDetails extends AppCompatActivity {
     ImageView banner;
    String DetailsURL,image,soldprice,snewprice,descr;
    public static final String PRODUCT_NAME="Productname",PRODUCT_ID="productid";
    private static final String url="http://dealsinstores.in/voucher/web_service_deals/product_desc.php?p_id=";
    private static final String TAG = BannerDetails.class.getSimpleName();
    CollapsingToolbarLayout collapsingToolbar;
    Button viewallproducts,addfavourites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_details);
        viewallproducts=(Button)findViewById(R.id.allproducts);
        addfavourites=(Button)findViewById(R.id.favourites);
        Fragment homemaps = new Maps();
        FragmentTransaction homemap1 = getSupportFragmentManager().beginTransaction();
        homemap1.add(R.id.map_fragment,homemaps).commit();
        Intent intent = getIntent();
        final String productname = intent.getStringExtra(PRODUCT_NAME);
        final String productid = intent.getStringExtra(PRODUCT_ID);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
      //  collapsingToolbar.setTitle("");
        DetailsURL=url+productid;
        Log.e("detailsurl",DetailsURL);
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,DetailsURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("detailsresponse",""+response);

                            JSONArray array = response.optJSONArray("data");
                            if (array.length()==0)
                            {
                                //Toast.makeText(getApplicationContext(), "No products to show", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Parsing json
                                for (int i = 0; i < array.length(); i++) {


                                    JSONObject obj2 = array.optJSONObject(i);
                                    String productname=obj2.getString("p_name");
                                     image=obj2.getString("img_name");
                                     soldprice=obj2.getString("price");
                                     snewprice=obj2.getString("special_price");
                                    descr=obj2.getString("des");
                                    collapsingToolbar.setTitle(productname);
                                    setimage();
                                    setprices();
                                  //  Glide.with(BannerDetails.this).load(image).centerCrop().into(imageView);
                                       /* listview.setTitle(obj2.getString("p_name"));
                                        listview.setImage(obj2.getString("img_name"));
                                        listview.setProductid(obj2.getString("entity_id"));
                                      /*/


                                }
                            }

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

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

      //  banner=(ImageView)findViewById(R.id.banner_image);
        //banner.setImageBitmap(bitmap);
    }
    private void setimage() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(image).centerCrop().into(imageView);
    } private void setprices() {
        final TextView oldprice = (TextView) findViewById(R.id.old_price);
        final TextView newprice = (TextView) findViewById(R.id.new_price);
        final TextView description=(TextView)findViewById(R.id.description);
        description.setText(descr);
        oldprice.setPaintFlags(oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        oldprice.setText(soldprice);
        newprice.setText(snewprice);

    }
    @Override
    public void onBackPressed() {
        //Execute your code here
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


}
