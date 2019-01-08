package com.deals.sine90.dealsinstreet.SearchPojo;

/**
 * Created by sine90 on 3/28/2017.
 */

public class SuggestGetSet_search {
    String id,p_name;
    public SuggestGetSet_search(String id, String p_name){

        this.setId(id);

        this.setP_name(p_name);

    }

    public String getP_name() {

        return p_name;

    }

    public void setP_name(String p_name) {

        this.p_name = p_name;

    }





    public String getId() {

        return id;
    }

    public void setId(String id)

    {
        this.id = id;

    }
}
