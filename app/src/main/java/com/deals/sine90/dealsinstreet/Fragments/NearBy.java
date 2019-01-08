package com.deals.sine90.dealsinstreet.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.deals.sine90.dealsinstreet.Adapters.CustomListAdapter;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NearBy.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NearBy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearBy extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

     //for webservices
     private static final String url="http://dealsinstores.in/voucher/web_service_deals/recent_product.php?cat=";
    private ProgressDialog pDialog;
    private List<Listview> list = new ArrayList<Listview>();
    private ListView listView;
    private CustomListAdapter adapter;
    private static final String TAG = NearBy.class.getSimpleName();
    String categoryid;
    TextView errortext;
    private Bundle bundle;
    String NEARBYURL;
    private OnFragmentInteractionListener mListener;

    public NearBy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearBy.
     */
    // TODO: Rename and change types and number of parameters
    public static NearBy newInstance(String param1, String param2) {
        NearBy fragment = new NearBy();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Set title bar
       /* ((Main2Activity) getActivity())
                .setActionBarTitle("Recent");*/

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_near_by, container, false);
        bundle = this.getArguments();
        categoryid = bundle.getString("categoryid");
        NEARBYURL=url+categoryid;
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), list);
        errortext=(TextView)view.findViewById(R.id.errortext);
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        list.clear();
        // Creating volley request obj
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,NEARBYURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("recentresponse",""+response);
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
                                        listview.setProductid(obj2.getString("entity_id"));
                                        listview.setStore_img(obj2.getString("store_img"));
                                        listview.setLatitude(obj2.getString("latitude"));
                                        listview.setLongnitude(obj2.getString("longtitude"));
                                        listview.setCount(obj2.getString("like_count"));
                                      /*  listview.setValue(obj2.getString("value"));
                                        listview.setPrice(obj2.getString("price"));
                                        listview.setFinal_price(obj2.getString("final_price"));*/

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


        return view;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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
