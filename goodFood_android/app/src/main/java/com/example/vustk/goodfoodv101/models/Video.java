package com.example.vustk.goodfoodv101.models;

/**
 * Created by vustk on 2018-04-04.
 */

public class Video {

    // Getter and Setter model for recycler view items
    private String cover, video_name, farm_name, product_name, product_image, video_url, chat_url;
    private int cost;


    public Video(String cover, String video_name, String farm_name, String product_name, int cost, String product_image, String video_url, String chat_url) {
        this.cover = cover;
        this.video_name = video_name;
        this.farm_name = farm_name;
        this.product_name = product_name;
        this.cost = cost;
        this.product_image = product_image;
        this.video_url = video_url;
        this.chat_url = chat_url;
    }

    public String getCover() {
        return cover;
    }

    public String getVideo_name() {
        return video_name;
    }

    public String getFarm_name() {
        return farm_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getChat_url() {
        return chat_url;
    }

    public int getCost() {
        return cost;
    }
}