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
import android.view.MenuItem;
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

public class RegisterActivity extends AppCompatActivity {
        EditText name,email,city,mobilenumber,otp,password;
    Button generateotp,register;
    private ProgressDialog nDialog,pDialog;
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        city=(EditText)findViewById(R.id.city);
        mobilenumber=(EditText)findViewById(R.id.mobile_num);
        otp=(EditText)findViewById(R.id.Otp);
        generateotp=(Button)findViewById(R.id.otp);
        register=(Button)findViewById(R.id.register);
        password=(EditText)findViewById(R.id.password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(( !name.getText().toString().equals(""))&&( !email.getText().toString().equals(""))&&( !city.getText().toString().equals(""))&&( !mobilenumber.getText().toString().equals(""))&&(!password.getText().toString().equals("")))
                {
                    NetAsync(v);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

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
            nDialog = new ProgressDialog(RegisterActivity.this);
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            //   nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){


/**
 * Gets current device state and checks for working internet connection by trying Google.
 **/
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
                if (nDialog.isShowing())
                    nDialog.dismiss();
                new ProcessRegister().execute();
            }
            else{
                if (nDialog.isShowing())
                    nDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Error in network connection", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {

        /**
         * Defining Process dialog
         **/


      //  String email,password,firstname,lastname,middlename,phno;
        String s_name,s_email,s_city,s_mobilenumber,s_password;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            s_name = name.getText().toString();
            s_city=city.getText().toString();
            s_email = email.getText().toString();
            s_mobilenumber=mobilenumber.getText().toString();
            s_password = password.getText().toString();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Registering ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {


            UserFunctions userFunction = new UserFunctions();
            // JSONObject json = userFunction.registerUser(fname, lname, email,phno, uname, password,fname, lname);
            JSONObject json = userFunction.registerUser(s_name, s_city, s_email,s_mobilenumber,s_password,"","");
            return json;


        }
        @Override
        protected void onPostExecute(JSONObject json) {
            /**
             * Checks for success message.
             **/
            Log.e("Mytag","Register response"+json);
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    String res = json.getString(KEY_SUCCESS);
                    Log.e("Mytag","Register response"+json);
                    String red = json.getString(KEY_ERROR);

                    if(Integer.parseInt(res) == 1){
                        pDialog.setTitle("Getting Data");
                        pDialog.setMessage("Loading Info");

                        Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                        if (pDialog.isShowing())
                            pDialog.dismiss();

                        //   Sqlhandler db = new Sqlhandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");

                        /**
                         * Removes all the previous data in the SQlite database
                         **/

                        UserFunctions logout = new UserFunctions();
                        //     logout.logoutUser(getApplicationContext());

                        //  db.addUser1( json_user.getString(KEY_EMAIL));
                        /**
                         * Stores registered data in SQlite Database
                         * Launch Registered screen
                         **/
                        Intent registered = new Intent(getApplicationContext(), Login.class);
                        /**
                         * Close all views before launching Registered screen
                         **/
                        registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(registered);
                        finish();
                    }

                    else if (Integer.parseInt(red) ==2){
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "User already exist", Toast.LENGTH_SHORT).show();
                    }
                    else if (Integer.parseInt(red) ==3){
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    }

                }

                else{
                    if (pDialog.isShowing())
                        pDialog.dismiss();

                    Toast.makeText(RegisterActivity.this, "Error occured in registration", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }}
    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
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
