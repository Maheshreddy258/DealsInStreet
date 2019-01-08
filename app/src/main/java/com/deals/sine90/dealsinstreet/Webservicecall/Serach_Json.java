package com.deals.sine90.dealsinstreet.Webservicecall;

import android.content.Context;

import com.deals.sine90.dealsinstreet.SearchPojo.SuggestGetSet_search;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sine90 on 3/28/2017.
 */

public class Serach_Json {
    double current_latitude,current_longitude;
    private static Context context;
    public Serach_Json(){}
    public Serach_Json(Context context,double current_latitude, double current_longitude){
        this.current_latitude=current_latitude;
        this.current_longitude=current_longitude;
        this.context=context;
    }
    public List<SuggestGetSet_search> getParseJsonWCF(String sName)
    {
        List<SuggestGetSet_search> ListData = new ArrayList<SuggestGetSet_search>();
        try {
            String temp=sName.replace(" ", "%20");
            URL js = new URL("http://dealsinstores.in/voucher/web_service_deals/productsearch.php?p_name="+temp);
            URLConnection jc = js.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            String line = reader.readLine();
            JSONObject jsonResponse = new JSONObject(line);
            JSONArray jsonArray = jsonResponse.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject r = jsonArray.getJSONObject(i);
                ListData.add(new SuggestGetSet_search(r.getString("entity_id"),r.getString("p_name")));
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }
}
