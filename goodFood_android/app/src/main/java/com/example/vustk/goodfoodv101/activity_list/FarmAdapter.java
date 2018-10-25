package com.example.vustk.goodfoodv101.activity_list;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.models.Farm;
import com.squareup.picasso.Picasso;

public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.ViewHolder> {
    private List<Farm> farmList;

    public FarmAdapter(List<Farm> farmList) {
        this.farmList = farmList;
    }
    public void setFarmAdapter(List<Farm> farmList){this.farmList = farmList;}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout listLayout;
        public TextView title, second, third;
        public ImageView cover;

        public ViewHolder(View v) {
            super(v);
            listLayout = (RelativeLayout) v.findViewById(R.id.list_layout);

            cover = (ImageView) v.findViewById(R.id.list_cover);
            title = (TextView) v.findViewById(R.id.list_title);
            second = (TextView) v.findViewById(R.id.list_second);
            third = (TextView) v.findViewById(R.id.list_third);
        }
    }

    @Override
    public FarmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FarmAdapter.ViewHolder holder, int position) {

        Farm farm = farmList.get(position);

        holder.title.setText(farm.getName());
        holder.second.setText(farm.getLocation());
        holder.third.setText(farm.getoid());
        Picasso.get().load(farm.getCover()).into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return farmList.size();
    }
}
