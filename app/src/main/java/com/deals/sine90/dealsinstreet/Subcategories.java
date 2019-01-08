package com.deals.sine90.dealsinstreet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.deals.sine90.dealsinstreet.Adapters.SubcategoryAdapter;
import com.deals.sine90.dealsinstreet.Fragments.Homescreen_Banners;
import com.deals.sine90.dealsinstreet.Fragments.SubcategoryBanners;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Categories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Subcategories extends AppCompatActivity {
    String categoryid,categoryname,SUBURL,category_id,category_name,children_count;
    ListView listview;
    private ProgressDialog pDialog;
    private SubcategoryAdapter adapter;
    private static final String url="http://dealsinstores.in/voucher/web_service_deals/maincategories.php?parent_id=";
    private List<Categories> list = new ArrayList<Categories>();
    private static final String TAG = Subcategories.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        categoryid=getIntent().getStringExtra("category_id");
        categoryname=getIntent().getStringExtra("category_name");
        Bundle bundle = new Bundle();
        bundle.putString("categoryname", categoryname);
        Fragment homeadds = new SubcategoryBanners();
        homeadds.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.homeadds_container,homeadds).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(categoryname);
        listview = (ListView) findViewById(R.id.list);
        adapter = new SubcategoryAdapter(getApplicationContext(), list);
        listview.setAdapter(adapter);
        SUBURL=url+categoryid;
        pDialog = new ProgressDialog(Subcategories.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,SUBURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hidePDialog();
                            JSONArray array = response.optJSONArray("data");
                            /*if (array.length()==0)
                            {

                                 Toast.makeText(getContext(), "No products to show", Toast.LENGTH_SHORT).show();
                            }
                            else {*/
                                // Parsing json
                                for (int i = 0; i < array.length(); i++) {
                                    try {
                                        Categories categories = new Categories();
                                        JSONObject obj2 = array.optJSONObject(i);
                                        categories.setChildren_count(obj2.getString("children_count"));
                                        categories.setImage(obj2.getString("img_name"));
                                        categories.setEntity_id(obj2.getString("entity_id"));
                                        categories.setCategory_name(obj2.getString("category_name"));
                                        list.add(categories);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
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
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category_id=list.get(position).getEntity_id();
                category_name=list.get(position).getCategory_name();
                children_count=list.get(position).getChildren_count();
                if (children_count.equals("0")){
                    Intent intent=new Intent(getApplicationContext(),Products.class);
                    intent.putExtra("category_name",category_name);
                    intent.putExtra("category_id",category_id);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getApplicationContext(), Subcategories.class);
                    intent.putExtra("category_name",category_name);
                    intent.putExtra("category_id",category_id);
                    startActivity(intent);
                }


            }
        });
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
