package com.example.vustk.goodfoodv101.botpager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.DetailProduct;
import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.models.Product;
import com.example.vustk.goodfoodv101.activity_list.RecyclerItemClickListener;
import com.example.vustk.goodfoodv101.activity_list.ProductAdapter;
import com.example.vustk.goodfoodv101.network.NetworkUtil;
import com.example.vustk.goodfoodv101.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NaviFragment3 extends Fragment {

    private List<Product> productList = new ArrayList<>();
    private List<JSONObject> productMemory = new ArrayList<>();

    private ProductAdapter productAdapter;
    private NetworkUtil networkUtil;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private boolean onceDataLoad = true;


    private EditText editSearch;
    private List<Product> searchList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_recyclerview, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        editSearch = (EditText) v.findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        final Activity root = getActivity();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(root, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(root, DetailProduct.class);
                intent.putExtra("product", productMemory.get(position).toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }));
        new StringTask().execute();

        return v;
    }

    //<입력, 진행되는작업자료형 ,결과
    class StringTask extends AsyncTask<Void, String, Void> {


        //백그라운드
        @Override
        protected Void doInBackground(Void... voids) {
            if (onceDataLoad) {
                requestGetProduct();
            }
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


    public void requestGetProduct() {
        networkUtil = new NetworkUtil(getContext());
        networkUtil.requestServer(Config.MAIN_URL + Config.GET_PRODUCT, networkSuccessListener(), networkErrorListener());
    }


    public void getJsonArray(JSONArray response) {
        try {
            JSONObject jresponse;
            for (int i = 0; i < response.length(); i++) {
                jresponse = response.getJSONObject(i);
                productMemory.add(jresponse);
              //  JSONArray mainimg = new JSONArray(jresponse.get("imgpath").toString());

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
                onceDataLoad = false;
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

    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        searchList.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            searchList.addAll(productList);
            productAdapter.setProductAdapter(productList);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < productList.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.

                if (productList.get(i).getName().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    searchList.add(productList.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        productAdapter.setProductAdapter(searchList);
        productAdapter.notifyDataSetChanged();
    }
}