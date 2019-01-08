package com.deals.sine90.dealsinstreet;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import com.deals.sine90.dealsinstreet.Adapters.PoupularSearchAdapter;
import com.deals.sine90.dealsinstreet.Adapters.SearchAdapter;
import com.deals.sine90.dealsinstreet.Adapters.SubcategoryAdapter;
import com.deals.sine90.dealsinstreet.Adapters.SuggestionAdapter;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Categories;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;
import com.deals.sine90.dealsinstreet.Webservicecall.Serach_Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search extends AppCompatActivity {
     AutoCompleteTextView search;
    String searchproduct,SearchURL,product_id,p_name,customer_id,customer_history,product_history;
    private static final String TAG = Search.class.getSimpleName();
    SessionManagement sessionManagement;
    ListView listview2,listview1;
    private SearchAdapter adapter;
    private PoupularSearchAdapter poupularSearchAdapter;
    private List<Listview> list = new ArrayList<Listview>();
    private List<Listview> popularlist = new ArrayList<Listview>();
    TextView history,popularsearch;
    ArrayList<HashMap<String, String>> pendinglist ,popularliss;
    private static final String SEARCHURL="http://dealsinstores.in/voucher/web_service_deals/productsearch.php?p_name=";
    private static final String SaveHistory="http://dealsinstores.in/voucher/web_service_deals/product_history.php";
    private static final String Savepopular="http://dealsinstores.in/voucher/web_service_deals/product_popular_search.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview2=(ListView)findViewById(R.id.list);
        pendinglist=new ArrayList<>();
        popularliss=new ArrayList<>();
        listview1=(ListView)findViewById(R.id.list1);
        search=(AutoCompleteTextView)findViewById(R.id.search);
        history=(TextView)findViewById(R.id.history);
        popularsearch=(TextView)findViewById(R.id.popularsearch);
        search.setAdapter(new SuggestionAdapter(this, search.getText().toString()));
        sessionManagement=new SessionManagement(getApplicationContext());
        customer_id=sessionManagement.getUserDetails().get("customerid");
        searchpopular();
        adapter = new SearchAdapter(Search.this, list);
        poupularSearchAdapter=new PoupularSearchAdapter(Search.this,popularlist);
       // listview.setAdapter(adapter);
     // ListUtils.setDynamicHeight(listview2);
     //  ListUtils.setDynamicHeight(listview1);
      //  setListViewHeightBasedOnChildren(listview);
       // setListViewHeightBasedOnChildren(listview1);
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchproduct=search.getText().toString();
                getid();
            }
        });
    }
    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

   public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void searchpopular() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Savepopular,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("searchpopularresponse",response);
                        try {
                            JSONObject request=new JSONObject(response);
                            JSONArray array=request.optJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject dataobject=array.getJSONObject(i);
                                customer_history=dataobject.getString("customer_history");
                                product_history=dataobject.getString("populer_search");
                                JSONArray  array1 = new JSONArray(customer_history);
                                if (array1.length()==0){
                                    history.setVisibility(View.GONE);
                                }else{
                                    for (int j = 0; j < array1.length(); j++) {
                                        Listview listview=new Listview();
                                        JSONObject jsonObject=array1.optJSONObject(j);
                                        listview.setSearchhistory(jsonObject.getString("product_name"));
                                        listview.setProductid(jsonObject.getString("product_id"));
                                        HashMap<String, String> pending = new HashMap<>();
                                        pending.put("product_name",jsonObject.getString("product_name"));
                                        pending.put("product_id",jsonObject.getString("product_id"));
                                        pendinglist.add(pending);
                                        ListAdapter adapter1 = new SimpleAdapter(
                                                Search.this, pendinglist,
                                                R.layout.searchlist, new String[]{"product_name"
                                        }, new int[]{
                                                R.id.product_name});
                                        listview2.setAdapter(adapter1);
                                        setDynamicHeight(listview2);
                                        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent intent=new Intent(getApplicationContext(), Productdescription.class);
                                                intent.putExtra("productid",pendinglist.get(position).get("product_id"));
                                                startActivity(intent);
                                            }
                                        });
                                        //list.add(listview);

                                    }
                                }

                                JSONArray popularsearcharray=new JSONArray(product_history);
                                if (popularsearcharray.length()==0){
                                    popularsearch.setVisibility(View.GONE);
                                }else{
                                    for (int k=0;k<popularsearcharray.length();k++){
                                        Listview listview=new Listview();
                                        JSONObject jsonObject=popularsearcharray.optJSONObject(k);
                                        listview.setSearchhistory(jsonObject.getString("product_name"));
                                        listview.setProductid(jsonObject.getString("product_id"));
                                        //popularlist.add(listview);
                                        HashMap<String, String> populars = new HashMap<>();
                                        populars.put("product_name",jsonObject.getString("product_name"));
                                        populars.put("product_id",jsonObject.getString("product_id"));
                                        popularliss.add(populars);
                                        ListAdapter adapter1 = new SimpleAdapter(
                                                Search.this, popularliss,
                                                R.layout.searchlist, new String[]{"product_name"
                                        }, new int[]{
                                                R.id.product_name});
                                        listview1.setAdapter(adapter1);
                                        setDynamicHeight(listview1);
                                        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent intent=new Intent(getApplicationContext(), Productdescription.class);
                                                intent.putExtra("productid",popularliss.get(position).get("product_id"));
                                                startActivity(intent);
                                            }
                                        });

                                    }
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
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
                params.put("customer_id",customer_id);
                Log.e("cabsparams",""+params);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public static void setDynamicHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        //check adapter if null
        if (adapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }

    public void getid()
    {
        SearchURL=SEARCHURL+searchproduct;
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,SearchURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("searchresponse",""+response);
                        try {

                            JSONArray array = response.optJSONArray("data");
                            if (array.length()==0)
                            {
                                //Toast.makeText(getApplicationContext(), "No products to show", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Parsing json
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj2 = array.optJSONObject(i);
                                    product_id=obj2.getString("entity_id");
                                    p_name=obj2.getString("p_name");
                                    storehistory();
                                    Intent intent=new Intent(Search.this,Productdescription.class);
                                    intent.putExtra("Productname",searchproduct);
                                   // intent.putExtra("productid",product_id);
                                    intent.putExtra("productid",product_id);
                                    startActivity(intent);

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

    }
    public void storehistory(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SaveHistory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("searchhistoryresponse",response);

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
                params.put("product_id",product_id);
                    params.put("product_name",p_name);
                params.put("customer_id",customer_id);
                Log.e("cabsparams",""+params);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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
