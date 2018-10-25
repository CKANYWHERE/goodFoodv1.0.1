package com.example.vustk.goodfoodv101.tabPager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.activity_list.VideoAdapter;
import com.example.vustk.goodfoodv101.activity_list.RecyclerItemClickListener;
import com.example.vustk.goodfoodv101.models.Video;
import com.example.vustk.goodfoodv101.network.NetworkUtil;
import com.example.vustk.goodfoodv101.streaming.Broading;
import com.example.vustk.goodfoodv101.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vustk on 2018-04-02.
 */

public class TabFragment2 extends Fragment {
    private List<Video> videoList = new ArrayList<>();
    private List<JSONObject> videoMemory = new ArrayList<>();
    private VideoAdapter videoAdapter;
    private NetworkUtil networkUtil ;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private boolean onceDataLoad = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_recyclerview, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), Broading.class);
                intent.putExtra("video", videoMemory.get(position).toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }));


        new StringTask().execute();
        // Inflate the layout for this fragment
        return v;

    }


    //<입력, 진행되는작업자료형 ,결과
    class StringTask extends AsyncTask<Void, String, Void> {


        //백그라운드
        @Override
        protected Void doInBackground(Void... voids) {
            if (onceDataLoad) {
                getVideo();
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
            videoAdapter = new VideoAdapter(videoList);
            mRecyclerView.setAdapter(videoAdapter);
            videoAdapter.notifyDataSetChanged();
        }
    }

    public void getVideo() {
        networkUtil = new NetworkUtil(getContext());
        networkUtil.requestServer(Config.MAIN_URL + Config.GET_LIVE_VIDEO, networkSuccessListener(), networkErrorListener());
    }


    public void getJsonArray(JSONArray response) {
        try {
            JSONObject jresponse;
            for (int i = 0; i < response.length(); i++) {
                jresponse = response.getJSONObject(i);
                videoMemory.add(jresponse);

                videoList.add(new Video(jresponse.getString("coverimg"),
                        jresponse.getString("stream_name"),
                        jresponse.getString("farm_name"),
                        jresponse.getString("product_name"),
                        jresponse.getInt("cost"),
                        jresponse.getString("productimg"),
                        jresponse.getString("stream_url"),
                        jresponse.getString("chat_url")
                ));
                videoAdapter.notifyDataSetChanged();
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


}