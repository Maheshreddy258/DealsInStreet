package com.deals.sine90.dealsinstreet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deals.sine90.dealsinstreet.LoginHelpers.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ForgotPassword extends AppCompatActivity {
    EditText email;
    Button forgot_password;
    String str_email;
    private ProgressDialog pDialog;
    private static String TAG = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        email=(EditText)findViewById(R.id.username);
        forgot_password=(Button)findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_email=email.getText().toString();
                if (str_email.equals("")){
                    Toast.makeText(ForgotPassword.this, "Please enter your mail id", Toast.LENGTH_SHORT).show();
                }else
                {
                    NetAsync(v);
                }
            }
        });

    }
    public void NetAsync(View view){
        new NetCheck().execute();
    }
    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }
        @Override
        protected Boolean doInBackground(String... args){



            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){

                new ProcessForgot().execute();
            }
            else{

                Toast.makeText(ForgotPassword.this,"Error in Network Connection",Toast.LENGTH_LONG).show();

            }
        }
    }
    private class ProcessForgot extends AsyncTask<String, String, JSONObject> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialog = new ProgressDialog(ForgotPassword.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.FORGOTPASSWORD(str_email);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Log.e("Mytag","DATA in forgot response"+json);
            try {
                if (json.getString(TAG) != null) {

                    String res = json.getString(TAG);
                    if(Integer.parseInt(res) == 1){
                        pDialog.setMessage("Loading User Space");
                        pDialog.setTitle("Getting Data");

                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Toast.makeText(ForgotPassword.this, "Your password was sent to your Mail", Toast.LENGTH_SHORT).show();
                        // SqlHandler db = new SqlHandler(getApplicationContext());
                        Intent upanel = new Intent(getApplicationContext(), Login.class);
                        // Create login session
                        pDialog.dismiss();
                        startActivity(upanel);
                        finish();
                    }else{
                        Toast.makeText(ForgotPassword.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        email.setText("");
                        Toast.makeText(ForgotPassword.this, json.getString("error_msg"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
