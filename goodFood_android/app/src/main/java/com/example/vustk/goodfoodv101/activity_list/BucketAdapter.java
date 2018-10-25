package com.example.vustk.goodfoodv101.activity_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.models.Product;
import com.example.vustk.goodfoodv101.network.NetworkUtil;
import com.example.vustk.goodfoodv101.util.Config;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.vustk.goodfoodv101.tabPager.TabFragment4.setCharge;

/**
 * Created by vustk on 2018-04-03.
 */
//ADAPTER
public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.ViewHolder> {

    private Context context;

    private List<Product> productList;

    private NetworkUtil networkUtil;
    private String userID;

    private DecimalFormat df = new DecimalFormat("#,##0");


    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout listLayout;
        public TextView title, second, third, display;
        public ImageView cover, x_button;
        public Button increase, decrease;

        public ViewHolder(View v) {
            super(v);
            listLayout = (RelativeLayout) v.findViewById(R.id.bucket_layout);

            cover = (ImageView) v.findViewById(R.id.bucket_cover);
            title = (TextView) v.findViewById(R.id.bucket_title);
            second = (TextView) v.findViewById(R.id.bucket_second);
            third = (TextView) v.findViewById(R.id.bucket_third);

            x_button = (ImageView) v.findViewById(R.id.x_button);
            decrease = (Button) v.findViewById(R.id.decrement);
            display = (TextView) v.findViewById(R.id.display);
            increase = (Button) v.findViewById(R.id.increment);


            x_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    x_button.setEnabled(false); //비활성화
                    int pos = getAdapterPosition();
                    requestDelete_item(pos);
                }
            });
            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    decrease.setEnabled(false);
                    int pos = getAdapterPosition();
                    requestnum_item(pos, false, display);
                    decrease.setEnabled(true);
                }
            });
            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increase.setEnabled(false);
                    int pos = getAdapterPosition();
                    requestnum_item(pos, true, display);
                    increase.setEnabled(true);
                }
            });

        }
    }

    //Provide a suitable constructor
    public BucketAdapter(List<Product> productList, Context context, String userID) {
        this.productList = productList;
        this.context = context;
        this.userID = userID;
    }

    //Create new views (invoked by the layout manager)
    @Override
    public BucketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bucket, parent, false);

        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    //Replace the contents of a view (invoked by the layout manager
    @Override
    public void onBindViewHolder(BucketAdapter.ViewHolder holder, int position) {

        Product product = productList.get(position);

        holder.title.setText(product.getName());
        holder.second.setText(df.format(product.getCost()) + '원');
        holder.third.setText(product.getoid());
        Picasso.get().load(product.getCover()).into(holder.cover);

        holder.display.setText(String.valueOf(product.getNum()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public void requestDelete_item(int pos) {
        networkUtil = new NetworkUtil(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", productList.get(pos).getoid());
            jsonObject.put("user_id", userID);
            jsonObject.put("num", productList.get(pos).getNum());
            networkUtil.requestServer(Request.Method.POST, Config.MAIN_URL + Config.DELETE_PRODUCT_TO_CART, jsonObject, networkSuccessListener1(pos), networkErrorListener());
        } catch (JSONException e) {
            throw new IllegalStateException("Failed to convert the object to JSON");
        }
    }

    private Response.Listener<JSONObject> networkSuccessListener1(final int pos) {
        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    response.getBoolean("success");

                } catch (JSONException e) {
                    throw new IllegalArgumentException(e.toString());
                } finally {
                    setCharge(- productList.get(pos).getCost() * productList.get(pos).getNum());
                    productList.remove(pos);
                    notifyItemRemoved(pos);
                }
            }
        };
    }

    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };
    }


    public void requestnum_item(int pos, boolean positive, TextView tv) {
        networkUtil = new NetworkUtil(context);
        JSONObject jsonObject = new JSONObject();

        int num = productList.get(pos).getNum();

        if (positive) {
            ++num;
        } else {
            if(num <= 1)
            {
                Toast.makeText(context, "수량이 1보다 작을 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            --num;
        }


        try {
            jsonObject.put("product_id", productList.get(pos).getoid());
            jsonObject.put("user_id", userID);
            jsonObject.put("shoppingcart_id", productList.get(pos).getPackid());
            jsonObject.put("num", num);
            networkUtil.requestServer(Request.Method.POST, Config.MAIN_URL + Config.POST_NUM_TO_CART, jsonObject, networkSuccessListener(pos, positive, tv, num), networkErrorListener());
        } catch (JSONException e) {
            throw new IllegalStateException("Failed to convert the object to JSON");
        }

    }


    private Response.Listener<JSONObject> networkSuccessListener(final int pos, final boolean positive, final TextView tv, final int num) {
        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    response.getBoolean("success");

                } catch (JSONException e) {
                    throw new IllegalArgumentException(e.toString());
                } finally {
                    productList.get(pos).setNum(num);
                    tv.setText(String.valueOf(num));
                    if (positive) {
                        setCharge(productList.get(pos).getCost());

                    } else {
                        setCharge(-productList.get(pos).getCost());
                    }
                }
            }
        };
    }


}