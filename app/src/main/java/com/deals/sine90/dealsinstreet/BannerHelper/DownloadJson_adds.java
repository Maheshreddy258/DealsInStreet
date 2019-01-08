package com.deals.sine90.dealsinstreet.BannerHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.deals.sine90.dealsinstreet.Adapters.SlidingImage_Adapter;
import com.deals.sine90.dealsinstreet.Fragments.Homescreen_Banners;
import com.deals.sine90.dealsinstreet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sine90 on 3/20/2017.
 */

public class DownloadJson_adds extends AsyncTask<String, Void, Integer> {
    ProgressBar mProgressDialog;
    // ProgressBarHandler mProgressBarHandler;
    private static final String TAG = Homescreen_Banners.class.getSimpleName();
    HttpURLConnection urlConnection;
    Context context;
    View v;
    Button innerViewPagerDemo;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ProgressDialog pDialog;
    ArrayList<String> IMAGES = new ArrayList<String>();
    ArrayList<String> Category = new ArrayList<String>();
    private static AutoScrollViewPager mPager;

    public DownloadJson_adds(Context context, View v) {
        this.context = context;
        this.v = v;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       /* // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
*/
    }
    @Override
    protected Integer doInBackground(String... params) {
        Integer result = 0;
        StringBuilder response1 = new StringBuilder();

        try {

            // Create Apache HttpClient
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();

            // 200 represents HTTP OK
            if (statusCode == 200) {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    response1.append(line);
                }


                parseResult(response1.toString());
                result = 1; // Successful
            } else {
                result = 0; //"Failed
            }
        } catch (Exception e) {

        }
        return result;
    }
    @Override
    protected void onPostExecute(Integer result) {
        // Download complete. Lets update UI

        //  mProgressBarHandler.hide();
        //mProgressDialog.setVisibility(View.GONE);
    }


    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("data");


            Homescreen_Banners item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String imageurl = post.optString("url");
                IMAGES.add(imageurl);
            }
            mPager = (AutoScrollViewPager) v.findViewById(R.id.pager);
            mPager.setInterval(2000);
            mPager.startAutoScroll();
            mPager.setAdapter(new SlidingImage_Adapter(context, IMAGES, mPager, v, innerViewPagerDemo));

           /* //dismissing progressbar
            if (pDialog.isShowing())
                pDialog.dismiss();*/
            mPager.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent e) {
                    // How far the user has to scroll before it locks the parent vertical scrolling.
                    final int margin = 10;
                    final int fragmentOffset = v.getScrollX() % v.getWidth();

                    if (fragmentOffset > margin && fragmentOffset < v.getWidth() - margin) {
                        mPager.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
