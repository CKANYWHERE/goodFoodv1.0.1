package com.example.vustk.goodfoodv101;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.network.NetworkUtil;
import com.example.vustk.goodfoodv101.util.Config;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

//그림 받아와서 뿌리는거 해야함
public class DetailProduct extends AppCompatActivity {

    private JSONObject product;
    String oid, detail_image;
    private SharedPreferences getUser;
    private NetworkUtil networkUtil;
    private boolean isLoggedon;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.detail_product);
        Button add_cart = (Button) findViewById(R.id.add_cart);
        ImageView detail_product = (ImageView) findViewById(R.id.detail_product);

        try {
            product = new JSONObject(intent.getStringExtra("product"));

            oid = product.getString("_id");
            detail_image = product.getString("productimg");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        add_cart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getUser = getSharedPreferences("user", MODE_PRIVATE);
                isLoggedon = getSession();

                if (isLoggedon) {
                    requestPostRegister();
                } else {
                    startActivity(new Intent(DetailProduct.this, LoginActivity.class));
                }
            }
        });
        Picasso.get().load(detail_image).into(detail_product);
    }


    public void requestPostRegister() {
        networkUtil = new NetworkUtil(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", oid);
            jsonObject.put("user_id", user_id);
            networkUtil.requestServer(Request.Method.POST, Config.MAIN_URL + Config.POST_PRODUCT_TO_CART, jsonObject, networkSuccessListener(), networkErrorListener());
        } catch (JSONException e) {
            throw new IllegalStateException("Failed to convert the object to JSON");
        }
    }

    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    response.getBoolean("success");

                } catch (JSONException e) {
                    throw new IllegalArgumentException(e.toString());
                } finally {
                    Toast.makeText(getApplicationContext(), "장바구니에 넣기 완료", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Toast.makeText(DetailProduct.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public boolean getSession() {
        if (getUser.contains("kakao") == true) {
            user_id = getUser.getString("kakao", "");
            return true;
        } else if (getUser.contains("userId") == true) {
            user_id = getUser.getString("userId", "");
            return true;
        }
        return false;
    }
}
