package com.deals.sine90.dealsinstreet;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deals.sine90.dealsinstreet.Fragments.Exclusive;
import com.deals.sine90.dealsinstreet.Fragments.Favourites;
import com.deals.sine90.dealsinstreet.Fragments.NearBy;
import com.deals.sine90.dealsinstreet.Fragments.Recent;
import com.deals.sine90.dealsinstreet.Fragments.Stores;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /*private TabLayout tabLayout;
    private ViewPager viewPager;*/
    ImageView homeimage,mapimage,wishlistimage,storeimage,expireimage;
    LinearLayout home,map,wishlist,store,expire;
    TextView hometext,maptext,wishlisttext,storetext,expiretext,username,user_mail;
    Resources res;
    RelativeLayout border;
    SessionManagement sessionManagement;
    ImageView prof_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* getSupportActionBar().setTitle  (" De");
        getSupportActionBar().setIcon(R.drawable.map_marker);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(Main2Activity.this,Location.class);
                startActivity(intent);
            }
        });*/
        sessionManagement=new SessionManagement(getApplicationContext());
        getSupportFragmentManager().beginTransaction().add(R.id.home_main,new Stores()).commit();


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
       //  prof_image=(ImageView)header.findViewById(R.id.imageView);
        user_mail=(TextView)header.findViewById(R.id.textView);
        username=(TextView)header.findViewById(R.id.username);
        if (sessionManagement.isLoggedIn())
        {
            String name=sessionManagement.getUserDetails().get("name");
           String email=sessionManagement.getUserDetails().get("email");
            String image=sessionManagement.getUserDetails().get("prof_image");
            user_mail.setText(email);
            username.setText(name);
           /* Picasso.with(this)
                    .load(image)
                    .placeholder(R.drawable.profile3)
                    .into(prof_image);*/
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.profile).setVisible(true);
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Main2Activity.this, MyProfile.class);
                    startActivity(intent);
                }
            });
            user_mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Main2Activity.this, MyProfile.class);
                    startActivity(intent);
                }
            });
           /* prof_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Main2Activity.this, MyProfile.class);
                    startActivity(intent);
                }
            });*/
        }
        else{
            navigationView.getMenu().findItem(R.id.logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.profile).setVisible(false);
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Main2Activity.this,Login.class);
                    startActivity(intent);
                }
            });
            user_mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Main2Activity.this,Login.class);
                    startActivity(intent);
                }
            });
            /*prof_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Main2Activity.this,Login.class);
                    startActivity(intent);
                }
            });*/
        }

        // for tab layout
       /* viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/
        //getting resources
        res = getApplicationContext().getResources();
        final int newColor = res.getColor(R.color.colorPrimary);
        //getting ids
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
        homeimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        hometext.setTextColor(newColor);

        //map on clicklistener
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
              Intent intent=new Intent(Main2Activity.this,MapsActivity.class);
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
                    Intent intent=new Intent(Main2Activity.this,WishList.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(Main2Activity.this,Login.class);
                    startActivity(intent);
                    Toast.makeText(Main2Activity.this, "Login to see wislist products", Toast.LENGTH_SHORT).show();
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
                    Intent intent=new Intent(Main2Activity.this,Mystore.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(Main2Activity.this,Login.class);
                    startActivity(intent);
                    Toast.makeText(Main2Activity.this, "Login to see wislist products", Toast.LENGTH_SHORT).show();
                }

            }
        });
        expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main2Activity.this,Expiring.class);
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
       /* viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

       adapter.addFragment(new Stores(), "Home");
        adapter.addFragment(new Recent(), "Nearby");
        adapter.addFragment(new Exclusive(), "Exclusive");
        adapter.addFragment(new NearBy(), "Recent");
        adapter.addFragment(new Favourites(), "Favourites");
        viewPager.setOffscreenPageLimit(4);

        viewPager.setAdapter(adapter);

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
         /*   case R.id.filter:
                //Creating the instance of PopupMenu
                View menuItemView = findViewById(R.id.filter);
                PopupMenu popup = new PopupMenu(getApplicationContext(), menuItemView);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String itemtext = item.getTitle().toString();
                        if(itemtext.equals("Food"))
                        {
                            Toast.makeText(Main2Activity.this, "Food clicked", Toast.LENGTH_SHORT).show();
                        }
                        if (itemtext.equals("Clothes"))
                        {
                            Toast.makeText(Main2Activity.this, "Clothes clicked", Toast.LENGTH_SHORT).show();
                        }
                        if (itemtext.equals("Computers"))
                        {
                            Toast.makeText(Main2Activity.this, "Computers clicked", Toast.LENGTH_SHORT).show();
                        }
                        if (itemtext.equals("Mobiles"))
                        {
                            Toast.makeText(Main2Activity.this, "Mobiles clicked", Toast.LENGTH_SHORT).show();
                        }


                        // Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
                break;*/
            case R.id.search:
            Intent intent=new Intent(Main2Activity.this,Search.class);
                startActivity(intent);

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*
        if (id == R.id.friends) {

        } */ if (id == R.id.profile) {
            Intent intent=new Intent(Main2Activity.this,MyProfile.class);
            startActivity(intent);

        } /*else if (id == R.id.explore_offers) {


        } else if (id == R.id.mycoupons) {


        }else if (id == R.id.dscredits) {


        }else if (id == R.id.dsDeals) {


        }*/else if (id == R.id.invite_earn) {
          // Handle the invite frnds action
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "To download the Deals in store app pls click on given click ";
            String shareSub = "Your subject here";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));

        }else if (id == R.id.privacy_policy) {
              Intent intent=new Intent(Main2Activity.this,PrivacyPolicy.class);
            startActivity(intent);

        }else if (id == R.id.terms_conditions) {
            Intent intent=new Intent(Main2Activity.this,Terams_Condtions.class);
            startActivity(intent);

        }else if (id == R.id.feedback) {
        Intent intent=new Intent(Main2Activity.this,Feedback.class) ;
            startActivity(intent);

        }else if (id == R.id.aboutus) {
            Intent intent=new Intent(Main2Activity.this,Aboutud.class);
            startActivity(intent);

        }else if (id == R.id.logout) {
//            Intent intent=new Intent(Main2Activity.this,Login.class);
//            startActivity(intent);
            sessionManagement.logoutUser();
            finish();
        }else if (id==R.id.notifications){

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main2,menu);
        /*inflater.inflate(R.menu.searchmenu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
*/
        return true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
        }
    }

    /*@Override
    protected void onPause() {
        final int newColor = res.getColor(R.color.colorPrimary);
        homeimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        hometext.setTextColor(newColor);
        expireimage.setColorFilter(null);
        expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
        super.onPause();
    }*/

    @Override
    protected void onRestart() {
        final int newColor = res.getColor(R.color.colorPrimary);
        homeimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        hometext.setTextColor(newColor);
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
