package com.deals.sine90.dealsinstreet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;



import java.util.List;

public class PrivacyPolicy extends AppCompatActivity  {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://dealsinstores.in/voucher/web_service_deals/private_policy.php");

    }

    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

    }
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
