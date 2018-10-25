package com.example.vustk.goodfoodv101.activity_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.models.Video;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> videoList;
    private DecimalFormat df = new DecimalFormat("#,##0");


    public VideoAdapter(List<Video> videoList) {
        this.videoList = videoList;
    }

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
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {

        final Video video = videoList.get(position);

        Picasso.get().load(video.getCover()).into(holder.cover);
        holder.title.setText(video.getVideo_name());
        holder.second.setText('['+video.getFarm_name()+']'+video.getProduct_name());
        holder.third.setText(df.format(video.getCost())+'원');
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
