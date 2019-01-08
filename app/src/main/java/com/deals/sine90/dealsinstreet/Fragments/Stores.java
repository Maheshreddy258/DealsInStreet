package com.deals.sine90.dealsinstreet.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.deals.sine90.dealsinstreet.Adapters.GridviewAdapter;
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Categories;
import com.deals.sine90.dealsinstreet.Main2Activity;
import com.deals.sine90.dealsinstreet.Products;
import com.deals.sine90.dealsinstreet.R;
import com.deals.sine90.dealsinstreet.Subcategories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Stores.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Stores#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Stores extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView gridView;
    private List<Categories> categoriesList = new ArrayList<Categories>();
    private GridviewAdapter adapter;
    private ProgressDialog pDialog;
    String entityid,childrencount,category_name;
    private static final String TAG = Main2Activity.class.getSimpleName();
    private static final String URL="http://dealsinstores.in/voucher/web_service_deals/maincategories.php";
    String travel_id="108",jewellery_id="97",winemart_id="211",spasaloonid="106";
    LinearLayout foodbaverages,fashion,health,groceries,home_improvements,travel,jewellary,winemarts,fitners,mob_gad,spa_salon,automotives;
    private OnFragmentInteractionListener mListener;

    public Stores() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Stores.
     */
    // TODO: Rename and change types and number of parameters
    public static Stores newInstance(String param1, String param2) {
        Stores fragment = new Stores();
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
        View view= inflater.inflate(R.layout.fragment_stores, container, false);
        Fragment homeadds = new Homescreen_Banners();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.homeadds_container,homeadds).commit();
        gridView=(GridView)view.findViewById(R.id.grid);
        adapter = new GridviewAdapter(getContext(), categoriesList);
        gridView.setAdapter(adapter);
        pDialog = new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
       /* foodbaverages=(LinearLayout)view.findViewById(R.id.foodbeverages);
        fashion=(LinearLayout)view.findViewById(R.id.fashion);
        health=(LinearLayout)view.findViewById(R.id.health);
        groceries=(LinearLayout)view.findViewById(R.id.grocery);
        home_improvements=(LinearLayout)view.findViewById(R.id.homeimprovemets);
        travel=(LinearLayout)view.findViewById(R.id.travel);
        jewellary=(LinearLayout)view.findViewById(R.id.jewellary);
        winemarts=(LinearLayout)view.findViewById(R.id.winemart);
        fitners=(LinearLayout)view.findViewById(R.id.fitners);
        mob_gad=(LinearLayout)view.findViewById(R.id.mobile_gadjets);
        spa_salon=(LinearLayout)view.findViewById(R.id.spa_saloon);
        automotives=(LinearLayout)view.findViewById(R.id.automotives);*/
        //for banners

        /*foodbaverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Food_BaverageCategories.class);
                startActivity(intent);
            }
        });
        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Fashion_Categories.class);
                startActivity(intent);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Health_categories.class);
                startActivity(intent);
            }
        });
        groceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Groceries_Categories.class);
                startActivity(intent);
            }
        });
        home_improvements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), HomeImprovements_Categories.class);
                startActivity(intent);
            }
        });
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Products.class);
                intent.putExtra("category_name","Travel");
                intent.putExtra("category_id",travel_id);
                startActivity(intent);
            }
        });
        jewellary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Products.class);
                intent.putExtra("category_name","Jewellery");
                intent.putExtra("category_id",jewellery_id);
                startActivity(intent);
            }
        });
        winemarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Products.class);
                intent.putExtra("category_name","WineMarts");
                intent.putExtra("category_id",winemart_id);
                startActivity(intent);
            }
        });
        fitners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Fitners_Categories.class);
                startActivity(intent);
            }
        });
        mob_gad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Mobile_Gadjet_categories.class);
                startActivity(intent);
            }
        });
        spa_salon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Products.class);
                intent.putExtra("category_name","Spa & saloon");
                intent.putExtra("category_id",spasaloonid);
                startActivity(intent);
            }
        });
        automotives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Automotives_Categories.class);
                startActivity(intent);
            }
        });*/
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,URL,null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            hidePDialog();
                            JSONArray array = response.optJSONArray("data");
                            // Parsing json
                            Categories categories;
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    categories = new Categories();
                                    JSONObject obj2 = array.optJSONObject(i);
                                    categories.setCategory_name(obj2.getString("category_name"));
                                    categories.setEntity_id(obj2.getString("entity_id"));
                                    categories.setImage(obj2.getString("img_name"));
                                    categories.setChildren_count(obj2.getString("children_count"));
                                    // adding movie to movies array
                                    categoriesList.add(categories);


                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
                //  hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 entityid=categoriesList.get(position).getEntity_id();
                childrencount=categoriesList.get(position).getChildren_count();
                category_name=categoriesList.get(position).getCategory_name();
                if (childrencount.equals("0")){
                    Intent intent=new Intent(getContext(),Products.class);
                    intent.putExtra("category_name",category_name);
                    intent.putExtra("category_id",entityid);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(getContext(), Subcategories.class);
                    intent.putExtra("category_name",category_name);
                    intent.putExtra("category_id",entityid);
                    startActivity(intent);

                }
            }
        });

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
