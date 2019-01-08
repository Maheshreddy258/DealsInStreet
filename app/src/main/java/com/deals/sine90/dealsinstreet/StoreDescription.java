package com.deals.sine90.dealsinstreet;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.deals.sine90.dealsinstreet.Adapters.HLVAdapter;
import com.deals.sine90.dealsinstreet.Fragments.Maps;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreDescription extends AppCompatActivity {
    private static final String TAG = BannerDetails.class.getSimpleName();
    CollapsingToolbarLayout collapsingToolbar;
    Button viewallproducts,addfavourites;
    String customerid,images,productid,image,productname,city,str_description,latitude,longnitude,monday_status,tuesday_status,wednesday_status,thursday_status,friday_status,saturday_status,sunday_status;
    ImageView imageView,monday,tuesday,wednesday,thursday,friday,saturday,sunday,store;
    TextView brandname;
    String url="http://dealsinstores.in/voucher/web_service_deals/liked_file.php";
    SessionManagement sessionManagement;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList alImage=new ArrayList();
    TextView redeem_head,redeem_text,term_head,t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_description);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        redeem_head=(TextView)findViewById(R.id.redeem_head);
        redeem_text=(TextView)findViewById(R.id.redeem);
        term_head=(TextView)findViewById(R.id.termshead);
        store=(ImageView)findViewById(R.id.store);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);

        redeem_head.setText(getIntent().getStringExtra("redeem_head"));
        term_head.setText(getIntent().getStringExtra("term_head"));
        redeem_text.setText(getIntent().getStringExtra("redeem_text"));
        t1.setText("*"+getIntent().getStringExtra("t1"));
        t2.setText("*"+getIntent().getStringExtra("t2"));
        t3.setText("*"+getIntent().getStringExtra("t3"));
        t4.setText("*"+getIntent().getStringExtra("t4"));

        sessionManagement=new SessionManagement(getApplicationContext());
        customerid=sessionManagement.getUserDetails().get("customerid");
       // viewallproducts=(Button)findViewById(R.id.allproducts);
        addfavourites=(Button)findViewById(R.id.favourites);
        brandname=(TextView)findViewById(R.id.brandname);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
         imageView = (ImageView) findViewById(R.id.backdrop);
        monday=(ImageView)findViewById(R.id.monday);
        tuesday=(ImageView)findViewById(R.id.tuesday);
        wednesday=(ImageView)findViewById(R.id.wednesday);
        thursday=(ImageView)findViewById(R.id.thursady);
        friday=(ImageView)findViewById(R.id.friday);
        saturday=(ImageView)findViewById(R.id.saturday);
        sunday=(ImageView)findViewById(R.id.sunday);
        final TextView description=(TextView)findViewById(R.id.description);
        Intent intent = getIntent();
      //  image=intent.getStringExtra("img");
        productname=intent.getStringExtra("Productname");
        city=intent.getStringExtra("city");
        str_description=intent.getStringExtra("description");
        latitude=intent.getStringExtra("latitude");
        longnitude=intent.getStringExtra("longnitude");
        monday_status=intent.getStringExtra("mondaystatus");
        tuesday_status=intent.getStringExtra("tuesdaystatus");
        wednesday_status=intent.getStringExtra("wednesdatstatus");
        thursday_status=intent.getStringExtra("thus_status");
        friday_status=intent.getStringExtra("fridaystatus");
        saturday_status=intent.getStringExtra("satstatus");
        sunday_status=intent.getStringExtra("sundaystatus");
        productid=intent.getStringExtra("productid");
        images=intent.getStringExtra("img");
        Log.e("images",images);
        Bundle bundle = new Bundle();
        bundle.putString("latitude", latitude);
        bundle.putString("longnitude", longnitude);
        Fragment homemaps = new Maps();
        homemaps.setArguments(bundle);
        FragmentTransaction homemap1 = getSupportFragmentManager().beginTransaction();
        homemap1.add(R.id.map_fragment,homemaps).commit();
        description.setText(str_description);
        collapsingToolbar.setTitle(productname);
        brandname.setText(productname);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            JSONArray jsonArray=new JSONArray(images);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.optJSONObject(i);
                String image=jsonObject.getString("p_img");
                alImage.add(image);
                if (i==0){
                    Glide.with(this).load(alImage.get(0)).centerCrop().into(imageView);
                    Glide.with(this).load(alImage.get(0)).centerCrop().into(store);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter = new HLVAdapter(StoreDescription.this, alImage);
        mRecyclerView.setAdapter(mAdapter);
      //  Glide.with(this).load(image).centerCrop().into(imageView);
        if (monday_status.equals("1")){
            monday.setImageResource(R.drawable.monday_active);
        }
        if (tuesday_status.equals("1")){
            tuesday.setImageResource(R.drawable.tuesday_active);
        }
        if (wednesday_status.equals("1")){
            wednesday.setImageResource(R.drawable.wednesday_active);
        }
        if (thursday_status.equals("1")){
            thursday.setImageResource(R.drawable.tuesday_active);
        }
        if (friday_status.equals("1")){
            friday.setImageResource(R.drawable.friday_active);
        }
        if (saturday_status.equals("1")){
            saturday.setImageResource(R.drawable.saturday_active);
        }
        if (friday_status.equals("1")){
            sunday.setImageResource(R.drawable.saturday_active);
        }
        addfavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("storeslikereponse", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("data").equals("Successfully liked")) {
                                        Toast.makeText(StoreDescription.this, "Successfully added to favourites", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StoreDescription.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("customer_id", customerid);
                        params.put("storelocator_id", productid);
                        Log.e("storeslikparams", "" + params);
                        return params;

                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(StoreDescription.this);
                requestQueue.add(stringRequest);

            }

        });


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
