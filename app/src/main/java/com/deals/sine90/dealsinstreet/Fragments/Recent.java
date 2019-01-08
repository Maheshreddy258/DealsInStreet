package com.deals.sine90.dealsinstreet.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.deals.sine90.dealsinstreet.Adapters.CustomListAdapter;

import com.deals.sine90.dealsinstreet.Adapters.NearByAdapter;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Recent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Recent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recent extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   /* ImageView banner1,banner2,banner3,banner4,banner5,banner6,like,like2,like3,lik4,like5,like6;
    Resources res;
    LinearLayout ll1,ll2,ll3,ll4,ll5,ll6,more1,more2,more3,more4,more5,more6,share,share2,share3,share4,share5,share6,direction,direction1,direction2,direction3,direction4,direction5;*/
    private OnFragmentInteractionListener mListener;
   /* SessionManagement sessionManagement;
    TextView liketext1,liketext2,liketext3,liketext4,liketext5,liketext6;*/
   String categoryid,latitude,longnitude;
    private Bundle bundle;
    private static final String url="http://dealsinstores.in/voucher/web_service_deals/category_wise_product.php?cat=";
    private static final String NEARBY="http://dealsinstores.in/voucher/web_service_deals/nearby_loc.php";
    private ProgressDialog pDialog;
    private List<Listview> list = new ArrayList<Listview>();
    private ListView listView;
    private NearByAdapter adapter;
    TextView errortext;
    private static final String TAG = Recent.class.getSimpleName();
    String NEARBYURL;
    public Recent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Recent.
     */
    // TODO: Rename and change types and number of parameters
    public static Recent newInstance(String param1, String param2) {
        Recent fragment = new Recent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recent, container, false);
        bundle = this.getArguments();
        categoryid = bundle.getString("categoryid");
        latitude=bundle.getString("latitude");
        longnitude=bundle.getString("longnitude");
        Log.e("category",categoryid);
        NEARBYURL=url+categoryid;
        Log.e("near",NEARBYURL);
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new NearByAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        errortext=(TextView)view.findViewById(R.id.errortext);
       getnearbyproducts();
        list.clear();
        return view;
    }
    public void getnearbyproducts(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NEARBYURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("nearbyreponse",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.optJSONArray("data");
                            Log.e("obje",""+jsonArray);
                            if (jsonArray.length()==0){
                                errortext.setVisibility(View.VISIBLE);
                                errortext.setText("No products to show");
                            }else {


                                // Parsing json
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {

                                        Listview listview = new Listview();
                                        JSONObject obj2 = jsonArray.optJSONObject(i);
                                        listview.setTitle(obj2.getString("p_name"));
                                        listview.setImage(obj2.getString("img_name"));
                                        listview.setProductid(obj2.getString("entity_id"));
                                        listview.setLatitude(obj2.getString("latitude"));
                                        listview.setLongnitude(obj2.getString("longtitude"));
                                        listview.setDistance(obj2.getString("distance"));
                                        listview.setAddress(obj2.getString("address"));
                                        listview.setStore_img(obj2.getString("store_img"));
                                        listview.setCount(obj2.getString("like_count"));

                                        list.add(listview);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(getContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("latitude",latitude);
                params.put("longtitude",longnitude);
                Log.e("cabsparams",""+params);
                if(params != null)params.put("params",latitude);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        // Adding request to request queue


        // return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
