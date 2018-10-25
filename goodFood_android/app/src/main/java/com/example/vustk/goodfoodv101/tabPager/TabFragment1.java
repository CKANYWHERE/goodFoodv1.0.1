package com.example.vustk.goodfoodv101.tabPager;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.activity_list.ProductAdapter;
import com.example.vustk.goodfoodv101.models.Farm;
import com.example.vustk.goodfoodv101.models.Product;
import com.example.vustk.goodfoodv101.models.Video;
import com.example.vustk.goodfoodv101.network.NetworkUtil;
import com.example.vustk.goodfoodv101.util.Config;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vustk on 2018-04-02.
 */

public class TabFragment1 extends Fragment implements VideoRendererEventListener {

    private ViewPager mPager;
    private CirclePageIndicator mIndicator;
    private ForImageAdapter farmAdapter;

    private List<Product> productList = new ArrayList<>();
    private List<Farm> farmList = new ArrayList<>();
    private List<JSONObject> productMemory = new ArrayList<>();
    private List<JSONObject> farmMemory = new ArrayList<>();
    private Video video;


    private ProductAdapter productAdapter;
    private NetworkUtil networkUtil;

    RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;

    SnapHelper startSnapHelper = new StartSnapHelper();
    private boolean onceDataLoad1 = true;
    private boolean onceDataLoad2 = true;

    private static final String TAG = "Broading";
    private SimpleExoPlayer player;
    private SimpleExoPlayerView simpleExoPlayerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_main, container, false);
        mPager = (ViewPager) v.findViewById(R.id.main_pager);
        mIndicator = (CirclePageIndicator) v.findViewById(R.id.indicator2);

        recyclerView2 = (RecyclerView) v.findViewById(R.id.main_pager2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setMinimumWidth(50);

        recyclerView2.setLayoutManager(layoutManager2);
        startSnapHelper.attachToRecyclerView(recyclerView2);


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        simpleExoPlayerView = new SimpleExoPlayerView(getContext());
        simpleExoPlayerView = (SimpleExoPlayerView) v.findViewById(R.id.main_exo);
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setPlayer(player);
        new StringTask().execute();

        return v;

    }

    @Override
    public void onVideoEnabled(DecoderCounters counters) {

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

    }

    @Override
    public void onVideoInputFormatChanged(Format format) {

    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }


    //<입력, 진행되는작업자료형 ,결과
    class StringTask extends AsyncTask<Void, String, Void> {

        //백그라운드
        @Override
        protected Void doInBackground(Void... voids) {
            if (onceDataLoad1) {
                requestGetProduct();
            }

            if(onceDataLoad2){
                requestGetFarm();
            }

            requestGetVod();
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
            farmAdapter = new ForImageAdapter(getContext(), farmList);
            mPager.setAdapter(farmAdapter);
            mIndicator.setViewPager(mPager);

            productAdapter = new ProductAdapter(productList);
            recyclerView2.setAdapter(productAdapter);

            farmAdapter.notifyDataSetChanged();
            productAdapter.notifyDataSetChanged();


        }
    }


    public void requestGetProduct() {
        networkUtil = new NetworkUtil(getContext());
        networkUtil.requestServer(Config.MAIN_URL + Config.GET_PRODUCT_MAIN, networkProductSuccessListener(), networkErrorListener());
    }

    public void requestGetFarm() {
        networkUtil = new NetworkUtil(getContext());
        networkUtil.requestServer(Config.MAIN_URL + Config.GET_FARM_MAIN, networkFarmSuccessListener(), networkErrorListener());
    }


    public void requestGetVod() {
        networkUtil = new NetworkUtil(getContext());
        networkUtil.requestServer(Config.MAIN_URL + Config.GET_MAIN_VOD, networkVodSuccessListener(), networkErrorListener());
    }

    private Response.Listener<JSONArray> networkProductSuccessListener() {
        return new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                getProductJsonArray(response);
                onceDataLoad1 = false;
            }
        };
    }

    private Response.Listener<JSONArray> networkFarmSuccessListener() {
        return new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                getFarmJsonArray(response);
                onceDataLoad2 = false;
            }
        };
    }

    public void getProductJsonArray(JSONArray response) {
        Log.e("tab1PRODUCT", response.toString());
        try {
            JSONObject jresponse;
            for (int i = 0; i < response.length(); i++) {
                jresponse = response.getJSONObject(i);
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


    public void getFarmJsonArray(JSONArray response) {
        Log.e("tab1FARM", response.toString());
        try {
            JSONObject jresponse;
            for (int i = 0; i < response.length(); i++) {
                jresponse = response.getJSONObject(i);
                farmMemory.add(jresponse);
                farmList.add(new Farm(jresponse.getString("_id"),
                        jresponse.getString("coverimg"),
                        jresponse.getString("farmname"),
                        jresponse.getString("address"),
                        jresponse.getString("farmimg")
                ));
                farmAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: ");
        }
    }


    private Response.Listener<JSONArray> networkVodSuccessListener() {
        return new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                try {
                    Log.e("tab1VIEDO", response.toString());
                    JSONObject jresponse;
                    for (int i = 0; i < response.length(); i++) {
                        jresponse = response.getJSONObject(i);
                        video = new Video(
                                jresponse.getString("coverimg"),
                                jresponse.getString("stream_name"),
                                jresponse.getString("farm_name"),
                                jresponse.getString("product_name"),
                                jresponse.getInt("cost"),
                                jresponse.getString("productimg"),
                                jresponse.getString("stream_url"),
                                jresponse.getString("date")
                        );
                    }

                    Uri mp4VideoUri = Uri.parse(video.getVideo_url());
                    DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
                    DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "exoplayer2example"), bandwidthMeterA);
                    MediaSource videoSource = new HlsMediaSource(mp4VideoUri, dataSourceFactory, 1, null, null);
                    final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
                    player.prepare(loopingSource);
                    player.addListener(new ExoPlayer.EventListener() {
                        @Override
                        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                            Log.v(TAG, "Listener-onTimelineChanged...");
                        }

                        @Override
                        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                            Log.v(TAG, "Listener-onTracksChanged...");
                        }

                        @Override
                        public void onLoadingChanged(boolean isLoading) {
                            Log.v(TAG, "Listener-onLoadingChanged...isLoading:" + isLoading);
                        }

                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                            Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState);
                        }

                        @Override
                        public void onRepeatModeChanged(int repeatMode) {
                            Log.v(TAG, "Listener-onRepeatModeChanged...");
                        }

                        @Override
                        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                            Log.v(TAG, "Listener-onShuffleModeEnabledChanged...");
                        }

                        @Override
                        public void onPlayerError(ExoPlaybackException error) {
                            player.stop();
                            player.prepare(loopingSource);
                            player.setPlayWhenReady(true);
                        }

                        @Override
                        public void onPositionDiscontinuity(int reason) {
                            Log.v(TAG, "Listener-onPositionDiscontinuity...");
                        }

                        @Override
                        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                            Log.v(TAG, "Listener-onPlaybackParametersChanged...");
                        }

                        @Override
                        public void onSeekProcessed() {
                            Log.v(TAG, "Listener-onSeekProcessed...");
                        }
                    });



                } catch (JSONException e) {
                    throw new IllegalArgumentException("Failed to parse the String: ");
                }
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

    @Override
    public void onStop() {
        super.onStop();
        player.release();
        Log.v(TAG, "onStop()...");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart()...");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()...");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.v(TAG, "onDestroy()...");
        player.release();
    }

}
