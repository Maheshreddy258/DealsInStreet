package com.deals.sine90.dealsinstreet;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.location.*;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deals.sine90.dealsinstreet.Adapters.CustomListAdapter;
import com.deals.sine90.dealsinstreet.Fragments.Exclusive;
import com.deals.sine90.dealsinstreet.Fragments.Favourites;
import com.deals.sine90.dealsinstreet.Fragments.Food;
import com.deals.sine90.dealsinstreet.Fragments.NearBy;
import com.deals.sine90.dealsinstreet.Fragments.Recent;
import com.deals.sine90.dealsinstreet.Fragments.Stores;
import com.deals.sine90.dealsinstreet.Listview_Helpers.Listview;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class Products extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String url="http://dealsinstores.in/voucher/web_service_deals/category_wise_product.php?cat=";
    private ProgressDialog pDialog;
    private List<Listview> list = new ArrayList<Listview>();
    private ListView listView;
    private CustomListAdapter adapter;
    String categoryid,categoryname;
    String ProductURL;
    ImageView homeimage,mapimage,wishlistimage,storeimage,expireimage;
    LinearLayout home,map,wishlist,store,expire;
    TextView hometext,maptext,wishlisttext,storetext,expiretext,username,user_mail;
    Resources res;
    Bundle bundle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SessionManagement sessionManagement;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    String latitude,longntude;
    TextView textview;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManagement = new SessionManagement(getApplicationContext());
        textview=(TextView)findViewById(R.id.textview);
        categoryid=getIntent().getStringExtra("category_id");
        if(categoryid.equals("97")){
            textview.setVisibility(View.VISIBLE);
        }
        res = getApplicationContext().getResources();
        final int newColor = res.getColor(R.color.colorPrimary);
        home=(LinearLayout)findViewById(R.id.home);
        homeimage=(ImageView)findViewById(R.id.homeimage);
        hometext=(TextView)findViewById(R.id.hometext);
        map=(LinearLayout)findViewById(R.id.maps);
        mapimage=(ImageView)findViewById(R.id.mapimage);
        maptext=(TextView)findViewById(R.id.maptext);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        wishlistimage=(ImageView)findViewById(R.id.wishlistimage);
        wishlisttext=(TextView)findViewById(R.id.wishlisttext);
        store=(LinearLayout)findViewById(R.id.mystore);
        storeimage=(ImageView)findViewById(R.id.mystoreimage);
        storetext=(TextView)findViewById(R.id.mystoretext);
        expire=(LinearLayout)findViewById(R.id.expiring);
        expireimage=(ImageView)findViewById(R.id.expiringimage);
        expiretext=(TextView)findViewById(R.id.expiringtext);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        categoryname=getIntent().getStringExtra("category_name");
        getSupportActionBar().setTitle(categoryname);

        /*homeimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        hometext.setTextColor(newColor);*/
     /*   listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, list);
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        categoryid=getIntent().getStringExtra("category_id");
        ProductURL=url+categoryid;*/
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapimage.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                maptext.setTextColor(newColor);
                hometext.setTextColor(getResources().getColor(R.color.light_blacck));
                homeimage.setColorFilter(null);
                wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
                wishlistimage.setColorFilter(null);
                storeimage.setColorFilter(null);
                storetext.setTextColor(getResources().getColor(R.color.light_blacck));
                expireimage.setColorFilter(null);
                expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
                Intent intent=new Intent(Products.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManagement.isLoggedIn())
                {
                    wishlistimage.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                    wishlisttext.setTextColor(newColor);
                    hometext.setTextColor(getResources().getColor(R.color.light_blacck));
                    homeimage.setColorFilter(null);
                    maptext.setTextColor(getResources().getColor(R.color.light_blacck));
                    mapimage.setColorFilter(null);
                    storeimage.setColorFilter(null);
                    storetext.setTextColor(getResources().getColor(R.color.light_blacck));
                    expireimage.setColorFilter(null);
                    expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
                    Intent intent=new Intent(Products.this,WishList.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(Products.this,Login.class);
                    startActivity(intent);
                    Toast.makeText(Products.this, "Login to see wislist products", Toast.LENGTH_SHORT).show();
                }


            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManagement.isLoggedIn()){
                    storetext.setTextColor(newColor);
                    storeimage.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                    hometext.setTextColor(getResources().getColor(R.color.light_blacck));
                    homeimage.setColorFilter(null);
                    maptext.setTextColor(getResources().getColor(R.color.light_blacck));
                    mapimage.setColorFilter(null);
                    wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
                    wishlistimage.setColorFilter(null);
                    expireimage.setColorFilter(null);
                    expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
                    Intent intent=new Intent(Products.this,Mystore.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(Products.this,Login.class);
                    startActivity(intent);
                    Toast.makeText(Products.this, "Login to see favourites products", Toast.LENGTH_SHORT).show();
                }

            }
        });
        expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Products.this,Expiring.class);
                startActivity(intent);
                expiretext.setTextColor(newColor);
                expireimage.setColorFilter(newColor,PorterDuff.Mode.SRC_ATOP);
                hometext.setTextColor(getResources().getColor(R.color.light_blacck));
                homeimage.setColorFilter(null);
                maptext.setTextColor(getResources().getColor(R.color.light_blacck));
                mapimage.setColorFilter(null);
                wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
                wishlistimage.setColorFilter(null);
                storeimage.setColorFilter(null);
                storetext.setTextColor(getResources().getColor(R.color.light_blacck));

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                hometext.setTextColor(newColor);
                maptext.setTextColor(getResources().getColor(R.color.light_blacck));
                mapimage.setColorFilter(null);
                wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
                wishlistimage.setColorFilter(null);
                storeimage.setColorFilter(null);
                storetext.setTextColor(getResources().getColor(R.color.light_blacck));
                expireimage.setColorFilter(null);
                expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
                Intent intent=new Intent(Products.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


    }
    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        android.location.Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            latitude=String.valueOf(location.getLatitude());
            longntude=String.valueOf(location.getLongitude());
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            setupViewPager(viewPager);
            //  Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {}
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */

    @Override
    public void onLocationChanged(android.location.Location location) {
        Log.e("onlocation","onlocation");
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();



    }
    private void setupViewPager(ViewPager viewPager) {
      ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        bundle =new Bundle();
        bundle.putString("categoryid", categoryid);
        bundle.putString("latitude",latitude);
        bundle.putString("longnitude",longntude);
        //for nearby
         Recent recent = new Recent();
            recent.setArguments(bundle);
            adapter.addFragment(recent, "Nearby");
            //for exclusivve
            Exclusive exclusive = new Exclusive();
            exclusive.setArguments(bundle);
            adapter.addFragment(exclusive, "Exclusive");
            //for recent
            NearBy nearBy = new NearBy();
            nearBy.setArguments(bundle);
            adapter.addFragment(nearBy, "Recent");
            //for favourites

            Favourites favourites = new Favourites();
            favourites.setArguments(bundle);
            adapter.addFragment(favourites, "Favourites");



            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(adapter);
        /*}*/

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);


        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
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
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main2,menu);

        return true;
    }*/
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }
    @Override
    protected void onRestart() {
        final int newColor = res.getColor(R.color.colorPrimary);
        homeimage.setColorFilter(null);
        hometext.setTextColor(getResources().getColor(R.color.light_blacck));
        expireimage.setColorFilter(null);
        expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
        wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
        wishlistimage.setColorFilter(null);
        storeimage.setColorFilter(null);
        storetext.setTextColor(getResources().getColor(R.color.light_blacck));
        maptext.setTextColor(getResources().getColor(R.color.light_blacck));
        mapimage.setColorFilter(null);
        super.onRestart();
    }
}
