package com.deals.sine90.dealsinstreet.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.deals.sine90.dealsinstreet.Adapters.CustomListAdapter;
import com.deals.sine90.dealsinstreet.Adapters.ExclusiveAdapter;
import com.deals.sine90.dealsinstreet.Adapters.NearByAdapter;
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
 * {@link Exclusive.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Exclusive#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Exclusive extends Fragment /*implements AdapterView.OnItemSelectedListener */ {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //LinearLayout filter,sortby;
    private OnFragmentInteractionListener mListener;
    String categoryid;
    private Bundle bundle;
        private static final String url="http://dealsinstores.in/voucher/web_service_deals/special_price.php?cat=";
    private List<Listview> list = new ArrayList<Listview>();
    private ListView listView;
    private ExclusiveAdapter adapter;
    private static final String TAG = Recent.class.getSimpleName();
    String NEARBYURL;
    TextView errortext;
    public Exclusive() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Exclusive.
     */
    // TODO: Rename and change types and number of parameters
    public static Exclusive newInstance(String param1, String param2) {
        Exclusive fragment = new Exclusive();
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
        View view= inflater.inflate(R.layout.fragment_exclusive, container, false);
        bundle = this.getArguments();
        categoryid = bundle.getString("categoryid");
        NEARBYURL=url+categoryid;
        Log.e("NEARBYURL",NEARBYURL);
        listView = (ListView) view.findViewById(R.id.list);
        errortext=(TextView)view.findViewById(R.id.errortext);
        adapter = new ExclusiveAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        list.clear();
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,NEARBYURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("exclusivresponse",""+response);

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
                                       listview.setCount(obj2.getString("like_count"));
                                         listview.setStore_img(obj2.getString("store_img"));
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


            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

       /* sortby=(LinearLayout)view.findViewById(R.id.sortby);
        filter=(LinearLayout) view.findViewById(R.id.filter);
        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        Food fragB = new Food ();
        childFragTrans.replace(R.id.fragmet, fragB);
        childFragTrans.addToBackStack("B");
        childFragTrans.commit();*/
       /* // Spinner element
        Spinner spinner = (Spinner)view. findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);*/

        // Spinner Drop down elements
        /*List<String> categories = new ArrayList<String>();
        categories.add("Food");
        categories.add("Clothes");
        categories.add("Computers");
        categories.add("Mobiles");*/


        // Creating adapter for spinner
     /*   ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

        // attaching data adapter to spinner
      //  spinner.setAdapter(dataAdapter);

        /*filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(), filter);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String itemtext = item.getTitle().toString();
                        if(itemtext.equals("Food"))
                        {
                            FragmentManager childFragMan = getChildFragmentManager();
                            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                            Food fragB = new Food ();
                            childFragTrans.replace(R.id.fragmet, fragB);
                            childFragTrans.addToBackStack("B");
                            childFragTrans.commit();
                        }
                        if (itemtext.equals("Clothes"))
                        {
                            FragmentManager childFragMan = getChildFragmentManager();
                            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                            Clothes fragB = new Clothes ();
                            childFragTrans.replace(R.id.fragmet, fragB);
                            childFragTrans.addToBackStack("B");
                            childFragTrans.commit();
                        }
                        if (itemtext.equals("Computers"))
                        {
                            FragmentManager childFragMan = getChildFragmentManager();
                            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                            Electronics fragB = new Electronics ();
                            childFragTrans.replace(R.id.fragmet, fragB);
                            childFragTrans.addToBackStack("B");
                            childFragTrans.commit();
                        }
                        if (itemtext.equals("Mobiles"))
                        {
                            FragmentManager childFragMan = getChildFragmentManager();
                            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                            Mobiles fragB = new Mobiles ();
                            childFragTrans.replace(R.id.fragmet, fragB);
                            childFragTrans.addToBackStack("B");
                            childFragTrans.commit();
                        }


                       // Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu

        }
        });
        sortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(), sortby);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.sort_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String itemtext = item.getTitle().toString();
                        if(itemtext.equals("Date"))
                        {
                            FragmentManager childFragMan = getChildFragmentManager();
                            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                            Food fragB = new Food ();
                            childFragTrans.replace(R.id.fragmet, fragB);
                            childFragTrans.addToBackStack("B");
                            childFragTrans.commit();
                        }
                        if (itemtext.equals("Name"))
                        {
                            FragmentManager childFragMan = getChildFragmentManager();
                            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                            Clothes fragB = new Clothes ();
                            childFragTrans.replace(R.id.fragmet, fragB);
                            childFragTrans.addToBackStack("B");
                            childFragTrans.commit();
                        }
                        if (itemtext.equals("Price"))
                        {
                            FragmentManager childFragMan = getChildFragmentManager();
                            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                            Electronics fragB = new Electronics ();
                            childFragTrans.replace(R.id.fragmet, fragB);
                            childFragTrans.addToBackStack("B");
                            childFragTrans.commit();
                        }

                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });*/
        return view;
    }
   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

       if(item.equals("Food"))
       {
           FragmentManager childFragMan = getChildFragmentManager();
           FragmentTransaction childFragTrans = childFragMan.beginTransaction();
           Food fragB = new Food ();
           childFragTrans.replace(R.id.fragmet, fragB);
           childFragTrans.addToBackStack("B");
           childFragTrans.commit();
       }
        if (item.equals("Clothes"))
        {
            FragmentManager childFragMan = getChildFragmentManager();
            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
            Clothes fragB = new Clothes ();
            childFragTrans.replace(R.id.fragmet, fragB);
            childFragTrans.addToBackStack("B");
            childFragTrans.commit();
        }
        if (item.equals("Computers"))
        {
            FragmentManager childFragMan = getChildFragmentManager();
            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
            Electronics fragB = new Electronics ();
            childFragTrans.replace(R.id.fragmet, fragB);
            childFragTrans.addToBackStack("B");
            childFragTrans.commit();
        }
        if (item.equals("Mobiles"))
        {
            FragmentManager childFragMan = getChildFragmentManager();
            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
            Mobiles fragB = new Mobiles ();
            childFragTrans.replace(R.id.fragmet, fragB);
            childFragTrans.addToBackStack("B");
            childFragTrans.commit();
        }
    }*/
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
