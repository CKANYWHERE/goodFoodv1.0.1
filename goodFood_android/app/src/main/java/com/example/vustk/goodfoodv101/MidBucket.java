package com.example.vustk.goodfoodv101;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.activity_list.ProductAdapter;
import com.example.vustk.goodfoodv101.activity_list.RecyclerItemClickListener;
import com.example.vustk.goodfoodv101.models.Product;
import com.example.vustk.goodfoodv101.network.NetworkUtil;
import com.example.vustk.goodfoodv101.util.Config;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MidBucket extends AppCompatActivity {


    private JSONObject farm;
    private List<JSONObject> productMemory = new ArrayList<>();
    private String oid, detail_image;

    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private NetworkUtil networkUtil;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.mid_bucket);

/*
        try {
            farm = new JSONObject(intent.getStringExtra("farm"));

            oid = farm.getString("_id");
            detail_image = farm.getString("farmimg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

        mRecyclerView = (RecyclerView) findViewById(R.id.mid_recycle);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MidBucket.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

       // new StringTask().execute();
    }

/*
    //<입력, 진행되는작업자료형 ,결과
    class StringTask extends AsyncTask<Void, String, Void> {
        //백그라운드
        @Override
        protected Void doInBackground(Void... voids) {
            requestProduct();
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
            productAdapter = new ProductAdapter(productList);
            mRecyclerView.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }
    }


    public void requestProduct() {
        networkUtil = new NetworkUtil(this);
        networkUtil.requestServer(Config.MAIN_URL + Config.GET_FARM_DETAIL + oid, networkSuccessListener(), networkErrorListener());
    }


    public void getJsonArray(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jresponse = response.getJSONObject(i);
                productMemory.add(jresponse);

                productList.add(new Product(jresponse.getString("_id"),
                        jresponse.getString("coverimg"),
                        jresponse.getString("productname"),
                        jresponse.getInt("productcost"),
                        jresponse.getString("productimg")
                ));
                productAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: ");
        }
    }

    private Response.Listener<JSONArray> networkSuccessListener() {
        return new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                getJsonArray(response);
            }
        };
    }

    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Toast.makeText(MidBucket.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

*/


}
