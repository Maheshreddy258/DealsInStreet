package com.deals.sine90.dealsinstreet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.deals.sine90.dealsinstreet.Adapters.StoreListAdapter;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Categories;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExclusiveStoreList extends AppCompatActivity {
    String prodctname,ExclusiveStorelist,str_productname;
    private List<Listview> list = new ArrayList<Listview>();
    private ListView listView;
    private StoreListAdapter adapter;
    private static final String ExclusiveStores="http://dealsinstores.in/voucher/web_service_deals/offer_store.php?p_name=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclusive_store_list);
        prodctname=getIntent().getStringExtra("name");
        str_productname=prodctname.replaceAll(" ", "%20");
        ExclusiveStorelist=ExclusiveStores+str_productname;
        Log.e("ExclusiveStorelist",ExclusiveStorelist);
        getstorelist();
        listView = (ListView) findViewById(R.id.list);
        adapter = new StoreListAdapter(ExclusiveStoreList.this, list);
        listView.setAdapter(adapter);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void getstorelist(){
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,ExclusiveStorelist,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("exclusivestoresresponse",""+response);
                        JSONArray jsonArray=response.optJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            Listview listview = new Listview();
                              JSONObject obj2=jsonArray.optJSONObject(i);
                            try {
                                listview.setTitle(obj2.getString("name"));
                                listview.setImage(obj2.getString("product_images"));
                                listview.setCity(obj2.getString("city"));
                              //  listview.setDistance(obj2.getString("distance"));
                                listview.setProductid(obj2.getString("storelocator_specialday_id"));
                                listview.setDescription(obj2.getString("description"));
                                listview.setLongnitude(obj2.getString("longtitude"));
                                listview.setLatitude(obj2.getString("latitude"));
                                listview.setMonday(obj2.getString("monday_status"));
                                listview.setTuesday(obj2.getString("tuesday_status"));
                                listview.setWednesday(obj2.getString("wednesday_status"));
                                listview.setThursday(obj2.getString("thursday_status"));
                                listview.setFriday(obj2.getString("friday_status"));
                                listview.setSaturday(obj2.getString("saturday_status"));
                                listview.setSunday(obj2.getString("sunday_status"));
                                listview.setRedeem_head(obj2.getString("redeem_head"));
                                listview.setRedeem_text(obj2.getString("redeem_text"));
                                listview.setTerm_head(obj2.getString("term_head"));
                                listview.setT1(obj2.getString("t1"));
                                listview.setT2(obj2.getString("t2"));
                                listview.setT3(obj2.getString("t3"));
                                listview.setT4(obj2.getString("t4"));
                                listview.setCoupon_head(obj2.getString("coupon_head"));
                                listview.setCoupon_text(obj2.getString("coupon_text"));
                                list.add(listview);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                  adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("error", "Error: " + error.getMessage());
              //  hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

    }
    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

    }
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
