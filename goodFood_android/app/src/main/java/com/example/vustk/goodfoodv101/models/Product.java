package com.example.vustk.goodfoodv101.models;

/**
 * Created by vustk on 2018-04-03.
 */
//MODEL
public class Product {

    private String oid, cover, name, detail_image;
    private int cost;

    private int num;
    private String packid;

    public Product(String oid, String cover, String name, int cost, String detail_image) {

        this.oid = oid;
        this.cover = cover;
        this.name = name;
        this.cost = cost;

        this.detail_image = detail_image;
    }

    //for bucket
    public Product(String oid, String cover, String name, int cost, String packid, int num) {

        this.oid = oid;
        this.cover = cover;
        this.name = name;
        this.cost = cost;

        this.packid = packid;
        this.num = num;
    }

    public String getPackid() {
        return packid;
    }

    public String getName() {
        return name;

    }

    public String getoid() {
        return oid;
    }

    public int getCost() {
        return cost;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCover() {
        return cover;
    }

}
