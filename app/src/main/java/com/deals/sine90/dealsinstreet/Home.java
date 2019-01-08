package com.deals.sine90.dealsinstreet;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    ImageView homeimage,mapimage,wishlistimage,storeimage,expireimage;
    LinearLayout home,map,wishlist,store,expire;
    TextView hometext,maptext,wishlisttext,storetext,expiretext;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        //default set color for home icon and text
        homeimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        hometext.setTextColor(newColor);
        //map on clicklistener
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapimage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                maptext.setTextColor(newColor);
                hometext.setTextColor(getResources().getColor(R.color.light_blacck));
                homeimage.setColorFilter(null);
                wishlisttext.setTextColor(getResources().getColor(R.color.light_blacck));
                wishlistimage.setColorFilter(null);
                storeimage.setColorFilter(null);
                storetext.setTextColor(getResources().getColor(R.color.light_blacck));
                expireimage.setColorFilter(null);
                expiretext.setTextColor(getResources().getColor(R.color.light_blacck));
               Intent intent=new Intent(Home.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent=new Intent(Home.this,WishList.class);
                startActivity(intent);

            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent=new Intent(Home.this,Mystore.class);
                startActivity(intent);
            }
        });
        expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Intent intent=new Intent(Home.this,Expiring.class);
                startActivity(intent);
            }
        });
        //home onclicklistener
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
            }
        });
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
