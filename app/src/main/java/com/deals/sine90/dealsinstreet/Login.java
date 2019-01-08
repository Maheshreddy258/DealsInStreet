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
import android.widget.TextView;
import android.widget.Toast;

import com.deals.sine90.dealsinstreet.LoginHelpers.UserFunctions;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {
        TextView register,forgot_password;
    Button login;
    String mobile,passwrd;
    EditText phne,pwd;
    SessionManagement session;
    private ProgressDialog nDialog;
    private ProgressDialog pDialog;
    private static String KEY_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        session = new SessionManagement(getApplicationContext());
         phne=(EditText)findViewById(R.id.number);
        pwd=(EditText)findViewById(R.id.pwd);
        login=(Button)findViewById(R.id.btnLogin);
       register=(TextView)findViewById(R.id.register);
        forgot_password=(TextView)findViewById(R.id.forgot_password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile=phne.getText().toString();
                passwrd=pwd.getText().toString();
                if (mobile.equals(""))
                {
                    Toast.makeText(Login.this, "Enter username", Toast.LENGTH_SHORT).show();
                }
                else if (passwrd.equals(""))
                {
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   /* session.createLoginSession(mobile, mobile, mobile, mobile, mobile, mobile, mobile, mobile, mobile, mobile, mobile, mobile);
                    Intent intent=new Intent(Login.this, Main2Activity.class);
                    startActivity(intent);
                    finish();*/
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
            nDialog = new ProgressDialog(Login.this);
            nDialog.setTitle("Checking Network");
            nDialog.setMessage("Loading..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }
        /**
         * Gets current device state and checks for working internet connection by trying Google.
         **/
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
                if (nDialog.isShowing())
                    nDialog.dismiss();
                new ProcessLogin().execute();
            }
            else{
                if (nDialog.isShowing())
                    nDialog.dismiss();
                Toast.makeText(Login.this,"Error in Network Connection",Toast.LENGTH_LONG).show();

            }
        }
    }
    private class ProcessLogin extends AsyncTask<String, String, JSONObject> {


        String email,password1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            email = phne.getText().toString();
            password1 = pwd.getText().toString();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.loginUser(email, password1);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Log.e("Mytag","DATA in login response"+json);
            try {
                if (json.getString(KEY_SUCCESS) != null) {

                    String res = json.getString(KEY_SUCCESS);
                    Log.e("Mytag","DATA in login response"+json);
                    if(Integer.parseInt(res) == 1){
                        pDialog.setMessage("Loading User Space");
                        pDialog.setTitle("Getting Data");

                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        // SqlHandler db = new SqlHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");
                        String web_email=json_user.getString("email");
                        String web_name=json_user.getString("fname");
                        String mobilenum=json_user.getString("mobile_no");
                        String city=json_user.getString("city");
                        String customerid=json_user.getString("entity_id");



                        /*SessionManager logout = new SessionManager(Login.this);
                       logout.logoutUser();*/

                        //  db.addUser1(json_user.getString(KEY_EMAIL));
                        //  Log.e("loginemail",json_user.getString(KEY_EMAIL));


                        Intent  upanel = new Intent(getApplicationContext(), Main2Activity.class);
                        // Create login session
                        session.setLogin(true);
                        session.createLoginSession(web_name, web_email, mobilenum, city,customerid,"");                        //  upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pDialog.dismiss();
                        startActivity(upanel);
                        /**
                         * Close Login Screen
                         **/
                        finish();
                    }else{
                        Toast.makeText(Login.this, "Incorrect username/password", Toast.LENGTH_SHORT).show();
                        //   loginErrorMsg.setText("Incorrect username/password");
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        pwd.setText("");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
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
