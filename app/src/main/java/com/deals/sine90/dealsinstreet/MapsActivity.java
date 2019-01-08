package com.deals.sine90.dealsinstreet;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
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
import com.deals.sine90.dealsinstreet.Listview_Helpers.AppController;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    android.location.Location mLastLocation;
    Marker mCurrLocationMarker;
    LatLng latLng;
    Handler h = new Handler();
    private static final String url = "http://dealsinstores.in/voucher/web_service_deals/allstores.php";
    private static final String DetailsUrl="http://dealsinstores.in/voucher/web_service_deals/allstores.php?store_name=";
    String lattitude,langitude,productname,img,city,description,productid,markertitle,markertitle1,current_latitude,current_longnitude;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    ArrayList<HashMap<String, String>> allstoreslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        setContentView(R.layout.activity_maps);

        allstoreslist = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        getdata();

    }
    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        //  mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(final Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        double lat=location.getLatitude();
        double longitude=location.getLongitude();
        current_latitude=String.valueOf(lat);
        current_longnitude=String.valueOf(longitude);
                //Place current location marker
              /*  if (!stop){*/
                /*latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));*/






    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }
    public void getdata() {
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,url,null,


                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("allstoresresponse", response.toString());
                        try {
                            hidePDialog();
                            JSONArray array = response.optJSONArray("data");
                            // Parsing json
                            for ( int i = 0; i < array.length(); i++) {

                                JSONObject obj2 = array.optJSONObject(i);
                                lattitude=obj2.getString("latitude");
                                langitude=obj2.getString("longtitude");
                                productname=obj2.getString("name");
                                description=obj2.getString("description");
                                city=obj2.getString("city");
                              //  img=obj2.getString("img");
                                productid=obj2.getString("storelocator_id");
                                final HashMap<String, String> allstores = new HashMap<>();
                                allstores.put("lattitude",lattitude);
                                allstores.put("langitude",langitude);
                                allstores.put("productname",productname);
                                allstores.put("description",description);
                                allstores.put("city",city);
                                allstores.put("img",img);
                                allstores.put("productid",productid);
                                allstoreslist.add(allstores);

                                Double lat= Double.parseDouble(lattitude);
                                Double lang=Double.parseDouble(langitude);
                                mCurrLocationMarker=    mGoogleMap.addMarker(new MarkerOptions()
                                        //.title(deliverylist.get(i).get("customer_name"))
                                        .title(obj2.getString("name"))
                                        .snippet(obj2.getString("city"))
                                        .position(new LatLng(
                                                lat, lang
                                        ))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.store_marker)));
                                mCurrLocationMarker.showInfoWindow();
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lang), 15));
                                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lang),15));
                              //  mCurrLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.store));

                                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                         markertitle=marker.getTitle();
                                        markertitle1=markertitle.replaceAll(" ", "%20");
                                         getdetails();

                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                hidePDialog();
                //  hidePDialog();
            }

        });
        AppController.getInstance().addToRequestQueue(movieReq);

    }
    public void getdetails(){
         String url=DetailsUrl+markertitle1;
        Log.e("url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("allstores",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.optJSONArray("data");
                            if (jsonArray.length()==0){
                                Toast.makeText(MapsActivity.this, "No stores to show", Toast.LENGTH_SHORT).show();
                            }else {
                              for (int i=0;i<jsonArray.length();i++){
                                   JSONObject jsonObject1=jsonArray.optJSONObject(i);
                                  String name=jsonObject1.getString("name");
                                  String img=jsonObject1.getString("product_images");
                                  String city=jsonObject1.getString("city");
                                  String description=jsonObject1.getString("description");
                                  String latitude=jsonObject1.getString("latitude");
                                  String longtitude=jsonObject1.getString("longtitude");
                                  String storelocator_id=jsonObject1.getString("storelocator_id");
                                  String mondaystatus=jsonObject1.getString("monday_status");
                                  String tuesdaystatus=jsonObject1.getString("tuesday_status");
                                  String wednesdatstatus=jsonObject1.getString("wednesday_status");
                                  String thus_status=jsonObject1.getString("thursday_status");
                                  String fridaystatus=jsonObject1.getString("friday_status");
                                  String satstatus=jsonObject1.getString("saturday_status");
                                  String sundaystatus=jsonObject1.getString("sunday_status");
                                  String redeem_head=jsonObject1.getString("redeem_head");
                                  String redeem_text=jsonObject1.getString("redeem_text");
                                  String term_head=jsonObject1.getString("term_head");
                                  String t1=jsonObject1.getString("t1");
                                  String t2=jsonObject1.getString("t2");
                                  String t3=jsonObject1.getString("t3");
                                  String t4=jsonObject1.getString("t4");
                                  String coupon_head=jsonObject1.getString("coupon_head");
                                  String coupon_text=jsonObject1.getString("coupon_text");
                                  Intent intent = new Intent(MapsActivity.this,StoreDescription.class);
                                  intent.putExtra("Productname",name);
                                  intent.putExtra("productid",storelocator_id);
                                  intent.putExtra("img",img);
                                  intent.putExtra("city",city);
                                  intent.putExtra("description",description);
                                  intent.putExtra("latitude",latitude);
                                  intent.putExtra("longnitude",longtitude);
                                  intent.putExtra("mondaystatus",mondaystatus);
                                  intent.putExtra("tuesdaystatus",tuesdaystatus);
                                  intent.putExtra("wednesdatstatus",wednesdatstatus);
                                  intent.putExtra("thus_status",thus_status);
                                  intent.putExtra("fridaystatus",fridaystatus);
                                  intent.putExtra("satstatus",satstatus);
                                  intent.putExtra("sundaystatus",sundaystatus);
                                  intent.putExtra("redeem_head",redeem_head);
                                  intent.putExtra("term_head",term_head);
                                  intent.putExtra("redeem_text",redeem_text);
                                  intent.putExtra("t1",t1);
                                  intent.putExtra("t2",t2);
                                  intent.putExtra("t3",t3);
                                  intent.putExtra("t4",t4);
                                  intent.putExtra("coupon_head",coupon_head);
                                  intent.putExtra("coupon_text",coupon_text);
                                  startActivity(intent);
                              }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                      //  adapter.notifyDataSetChanged();
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
                params.put("latitude",current_latitude);
                params.put("longtitude",current_longnitude);
                Log.e("cabsparams",""+params);

                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        /*if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }*/
    }

}
