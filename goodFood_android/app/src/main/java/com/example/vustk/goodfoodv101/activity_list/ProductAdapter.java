package com.example.vustk.goodfoodv101.activity_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.models.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by vustk on 2018-04-03.
 */
//ADAPTER
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList; // image, cost, title 사용
    private DecimalFormat df = new DecimalFormat("#,##0");


    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }
    public void setProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //each data item is just a string in this case
        public TextView title, fee;
        public ImageView cover;

        public ViewHolder(View v) {
            super(v);
            cover = (ImageView) v.findViewById(R.id.card_cover);
            title = (TextView) v.findViewById(R.id.card_title);
            fee = (TextView) v.findViewById(R.id.card_fee);
        }
    }



    //Create new views (invoked by the layout manager)
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);

        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        Product product = productList.get(position);

        holder.title.setText(product.getName());
        holder.fee.setText(df.format(product.getCost())+ '원');
        Picasso.get().load(product.getCover()).into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}