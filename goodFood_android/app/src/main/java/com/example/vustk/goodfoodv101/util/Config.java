package com.example.vustk.goodfoodv101.util;

import android.net.Uri;

/**
 * Created by vustk on 2018-03-31.
 */

public class Config {
    public final static String MAIN_URL = "http://35.200.0.166:3000";

    public final static String POST_SIGNIN = "/api/User/loginMobile";
    public final static String POST_SIGNUP = "/api/User/success";
    public final static String GET_SHOPPINGCART = "/api/User/shoppingcart/";

    public final static String GET_FARM = "/api/Farm/";
    public final static String GET_FARM_MAIN = "/api/Farm/main";
    public final static String GET_FARM_DETAIL = "/api/Farm/detailFarm/";

    public final static String GET_PRODUCT = "/api/Product/";
    public final static String GET_PRODUCT_MAIN = "/api/Product/main";
    public final static String POST_PRODUCT_TO_CART = "/api/User/addcart";
    public final static String DELETE_PRODUCT_TO_CART = "/api/User/deletecart";

    public final static String POST_NUM_TO_CART = "/api/User/numcart";
    public final static String GET_LIVE_VIDEO = "/api/LiveStream";

    public final static String GET_MAIN_VOD = "/api/ReStream/main";


    public String encodingUrl(String url, String key, String value) {
        return Uri.parse(url).buildUpon().appendQueryParameter(key, value)
                .build().toString();
    }

    public String encodingUrl(String url, String segment) {
        return Uri.parse(url).buildUpon().appendPath(segment).build().toString();
    }

}
