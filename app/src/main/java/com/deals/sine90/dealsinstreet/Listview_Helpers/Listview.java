package com.deals.sine90.dealsinstreet.Listview_Helpers;

import java.util.ArrayList;

/**
 * Created by sine90 on 3/30/2017.
 */

public class Listview {
    private String  like_count,searchhistory,popularhistory,redeem_head,redeem_text,term_head,t1,t2,t3,t4,coupon_head,coupon_text,monday,tuesday,wednesday,thursday,friday,saturday,sunday,title,store_img,description,address,latitude,distance,longnitude,storelocator_id,name,city,img_icon,state,img,image,value,price,quantity,final_price,productid,wishlistid;
    private ArrayList<String> list;
    public Listview(){

    }
    public Listview(String title, String image, String value,String price,String final_price,String qty,
                 ArrayList<String> list,String productid,String wishlistid) {
        this.title = title;
        this.image = image;
        this.value = value;
        this.price = price;
        this.final_price = final_price;
        this.quantity=qty;
        this.list=list;
        this.productid=productid;
        this.wishlistid=wishlistid;
    }
    public String getCount() {return like_count;
    }
    public void setCount(String like_count) {
        this.like_count = like_count;
    }
    public String getRedeem_head() {
        return redeem_head;
    }

    public void setRedeem_head(String redeem_head) {
        this.redeem_head = redeem_head;
    }
    public String getRedeem_text() {
        return redeem_text;
    }

    public void setRedeem_text(String redeem_text) {
        this.redeem_text = redeem_text;
    }
    public String getTerm_head() {
        return term_head;
    }

    public void setTerm_head(String term_head) {
        this.term_head = term_head;
    }
    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }
    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }
    public String getT3() {
        return t3;
    }

    public void setT3(String t3) {
        this.t3 = t3;
    }
    public String getT4() {
        return t4;
    }

    public void setT4(String t4) {
        this.t4 = t4;
    }
    public String getCoupon_head() {
        return coupon_head;
    }

    public void setCoupon_head(String coupon_head) {
        this.coupon_head = coupon_head;
    }
    public String getCoupon_text() {
        return coupon_text;
    }

    public void setCoupon_text(String coupon_text) {
        this.coupon_text = coupon_text;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }
    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }
    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }
    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }
    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }
    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }
    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }
    public String getStore_img() {
        return store_img;
    }


    public void setStore_img(String store_img) {
        this.store_img = store_img;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongnitude() {
        return longnitude;
    }

    public void setLongnitude(String longnitude) {
        this.longnitude = longnitude;
    }
    public String getDistance() {
        return distance;
    }



    public void setDistance(String distance) {
        this.distance = distance;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public String  getImage()
    {
        return image;
    }
    public void setImage(String image)
    {
        this.image=image;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value=value;
    }
    public String getPrice()
    {
        return price;
    }
    public void setPrice(String price)
    {
        this.price=price;
    }
    public String getFinal_price()
    {
        return final_price;
    }
    public void setFinal_price(String final_price)
    {
        this.final_price=final_price;
    }
    public String  getQuantity()
    {
        return quantity;
    }
    public void setQuantity(String quantity)
    {
        this.quantity=quantity;
    }
    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
    public String getProductid()
    {
        return productid;
    }
    public void setProductid(String productid)
    {
        this.productid=productid;
    }
    public String getWishlistid()
    {
        return wishlistid;
    }
    public void setWishlistid(String wishlistid)
    {
        this.wishlistid=wishlistid;
    }
    public String getStorelocator_id() {
        return storelocator_id;
    }
    public void setStorelocator_id(String storelocator_id) {
        this.storelocator_id = storelocator_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {return city;}
    public void setCity(String city) {
        this.city = city;
    }

    public String getImg() {return img;}
    public void setImg(String img) {
        this.img = img;
    }

    public String getImg_icon() {return img_icon;}
    public void setImg_icon(String img_icon) {
        this.img_icon = img_icon;
    }

    public String getState() {return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getSearchhistory() {return searchhistory;
    }
    public void setSearchhistory(String searchhistory) {
        this.searchhistory = searchhistory;
    }
    public String getPopularhistory() {return popularhistory;
    }
    public void setPopularhistory(String popularhistory) {
        this.popularhistory = popularhistory;
    }
}
