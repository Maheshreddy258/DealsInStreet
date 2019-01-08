package com.deals.sine90.dealsinstreet.LoginHelpers;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sine90 on 3/20/2017.
 */

public class UserFunctions{
    private JSONParser jsonParser;

        private static String register_tag = "register";
        private static String login_tag = "login";
        private static String chgpass_tag = "chgpass";
        private static String forpass_tag = "forpass";
    private static String login="http://dealsinstores.in/voucher/web_service_deals/user_login/index.php";
        // constructor
        public UserFunctions(){

            jsonParser = new JSONParser();
        }

        public JSONObject registerUser(String name, String city, String email, String phno,String password,String middlename,String lastname){
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", register_tag));
            params.add(new BasicNameValuePair("firstname", name));
            params.add(new BasicNameValuePair("city", city));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("mobile_no",phno));
            params.add(new BasicNameValuePair("middlename",middlename));
            params.add(new BasicNameValuePair("lastname",lastname));
            JSONObject json = jsonParser.getJSONFromUrl(login,params);
            return json;
        }

    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        Log.e("params",""+params);
        JSONObject json = jsonParser.getJSONFromUrl(login, params);

        return json;
    }
    public JSONObject UpdateProfile(String name,String city,String email,String mobile,String image,String customerid,String mname,String lname){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "edit"));
        params.add(new BasicNameValuePair("firstname", name));
        params.add(new BasicNameValuePair("city", city));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("mobile_no",mobile));
        params.add(new BasicNameValuePair("image",image));
        params.add(new BasicNameValuePair("lastname",lname));
        params.add(new BasicNameValuePair("middlename",mname));
        params.add(new BasicNameValuePair("customer_id",customerid));
        Log.e("params",""+params);
        JSONObject json = jsonParser.getJSONFromUrl(login,params);
        return json;
    }
    public JSONObject FORGOTPASSWORD(String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "forpass"));
        params.add(new BasicNameValuePair("email", email));
        Log.e("params",""+params);
        JSONObject json = jsonParser.getJSONFromUrl(login,params);
        return json;
    }


}



