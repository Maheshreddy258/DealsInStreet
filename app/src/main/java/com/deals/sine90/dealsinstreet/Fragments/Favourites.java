package com.deals.sine90.dealsinstreet.Fragments;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.deals.sine90.dealsinstreet.Adapters.CustomListAdapter;
import com.deals.sine90.dealsinstreet.Adapters.WishlistAdapter;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.Login;
import com.deals.sine90.dealsinstreet.R;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Favourites.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Favourites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favourites extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SessionManagement sessionManagement;
    private static final String url="http://dealsinstores.in/voucher/web_service_deals/category_wise_product.php?cat=";
    private static final String Favourites="http://dealsinstores.in/voucher/web_service_deals/favorite.php?cat=";
    private List<Listview> list = new ArrayList<Listview>();
    private ListView listView;
   // private CustomListAdapter adapter;
   private WishlistAdapter adapter;
    private static final String TAG = "Favourites";
    String customer_id,Wishliturl,categoryids;
    private Bundle bundle;
    String NEARBYURL;
    TextView errortext;
    private OnFragmentInteractionListener mListener;

    public Favourites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favourites.
     */
    // TODO: Rename and change types and number of parameters
    public static Favourites newInstance(String param1, String param2) {
        Favourites fragment = new Favourites();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       // View view= inflater.inflate(R.layout.fragment_favourites, null);
        View view= inflater.inflate(R.layout.fragment_favourites, container,false);
        sessionManagement = new SessionManagement(getContext());
            customer_id=sessionManagement.getUserDetails().get("customerid");
            bundle = this.getArguments();
            categoryids = bundle.getString("categoryid");
            NEARBYURL=Favourites+categoryids+"&c_id="+customer_id;
            Log.e("nearr",NEARBYURL);
            errortext=(TextView)view.findViewById(R.id.errortext);

       /* sessionManagement = new SessionManagement(getContext());
        getChildFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        customer_id=sessionManagement.getUserDetails().get("customerid");
        Wishliturl=url+customer_id;*/
            listView = (ListView)view. findViewById(R.id.list);
            adapter = new WishlistAdapter(getActivity(), list);
            listView.setAdapter(adapter);
            list.clear();
            JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET,NEARBYURL,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                hidePDialog();
                                JSONArray array = response.optJSONArray("data");
                                if (array.length()==0)
                                {
                                    errortext.setVisibility(View.VISIBLE);
                                    errortext.setText("No products to show");
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
            AppController.getInstance().addToRequestQueue(jsonreq);



        //for favourites


        return view;
    }
   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            Fragment videoFragment = new Favourites();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.favourites, videoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else{
            //no
        }
    }*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
       /* if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }*/
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
       /* if (context instanceof OnFragmentInteractionListener) {
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
