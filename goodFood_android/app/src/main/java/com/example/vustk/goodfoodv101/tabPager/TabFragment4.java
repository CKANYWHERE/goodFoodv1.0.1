package com.example.vustk.goodfoodv101.tabPager;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.LoginActivity;
import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.activity_list.BucketAdapter;
import com.example.vustk.goodfoodv101.models.Product;
import com.example.vustk.goodfoodv101.network.NetworkUtil;
import com.example.vustk.goodfoodv101.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

//서버에서 foreach로 와서 num이랑 프로덕트 안맞음 // 서버 반응 느리면 토탈금액 뒤틀리는거 수정

public class TabFragment4 extends Fragment {

    private List<Product> productList = new ArrayList<>();
    private BucketAdapter bucketAdapter;
    private NetworkUtil networkUtil;
    private SharedPreferences getUser;
    private boolean isLoggedOn;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private String _id;

    private Button btnCharge;
    private static int charge;
    private static TextView txtCharge;
    private static DecimalFormat df = new DecimalFormat("#,##0");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Activity root = getActivity();


        getUser = root.getSharedPreferences("user", Context.MODE_PRIVATE);
        isLoggedOn = checkSessionIsOpen();

        View v = inflater.inflate(R.layout.bucket_bag, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        txtCharge = (TextView) v.findViewById(R.id.txtCharge);
        btnCharge = (Button) v.findViewById(R.id.btnCharge);


        if (!isLoggedOn) {
            startActivityForResult(new Intent(this.getContext(), LoginActivity.class), 1000);
        } else {
            new StringTask().execute();
        }
        return v;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                checkSessionIsOpen();
                new StringTask().execute();
            }
            if (resultCode == RESULT_CANCELED) {
                startActivityForResult(new Intent(this.getContext(), LoginActivity.class), 1000);
            }
        }
    }//onActivityResult


    //<입력, 진행되는작업자료형 ,결과
    class StringTask extends AsyncTask<Void, String, Void> {
        //백그라운드
        @Override
        protected Void doInBackground(Void... voids) {
            requestGetRegister();
            return null;
        }

        //백그라운드 처리 도중도중
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        //작업끝난뒤
        @Override
        protected void onPostExecute(Void aVoid) {
            bucketAdapter = new BucketAdapter(productList, getContext(), _id);
            mRecyclerView.setAdapter(bucketAdapter);
            bucketAdapter.notifyDataSetChanged();

            cancel(true);
        }
    }


    public boolean checkSessionIsOpen() {
        if (getUser.contains("kakao")) {
            _id = getUser.getString("kakao", "");
            return true;
        } else if (getUser.contains("userId")) {
            _id = getUser.getString("userId", "");
            return true;
        }
        return false;
    }


    public void requestGetRegister() {
        networkUtil = new NetworkUtil(getContext());
        networkUtil.requestServer(Config.MAIN_URL + Config.GET_SHOPPINGCART + _id, null, networkSuccessListener(), networkErrorListener());
        Log.e("ERRRRGET", _id);
    }


    public void getJsonObject(JSONObject response) {
        try {
            Log.e("Array", response.toString());

            productList.clear();
            charge = 0;

            JSONArray product = new JSONArray(response.get("product").toString());
            JSONArray num = new JSONArray(response.get("num").toString());
            JSONArray product_pack = new JSONArray(response.get("shoppingcart").toString());

            for (int i = 0; i < product.length(); i++) {
                JSONObject _num = num.getJSONObject(i);
                int _product_num = _num.getInt("num");

                JSONObject _product = product.getJSONObject(i);
                int _product_cost = _product.getInt("productcost");

                JSONObject _product_pack = product_pack.getJSONObject(i);

                productList.add(new Product(_product.getString("_id"),
                        _product.getString("coverimg"),
                        _product.getString("productname"),
                        _product_cost,
                        _product_pack.getString("_id"),
                        _product_num));

                bucketAdapter.notifyDataSetChanged();

                charge += _product_num * _product_cost;
            }
            txtCharge.setText(df.format(charge) + '원');

        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: ");
        }
    }

    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                getJsonObject(response);
                //onceDataLoad = false;
            }
        };
    }

    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public static void setCharge(int val) {
        charge += val;
        txtCharge.setText(df.format(charge) + '원');

    }

}