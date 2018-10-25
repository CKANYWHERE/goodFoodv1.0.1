package com.example.vustk.goodfoodv101.models;

/**
 * Created by vustk on 2018-04-04.
 */

public class Farm {

    // Getter and Setter model for recycler view items
    private String oid, name, location,  cover, detail_image;

    public Farm(String oid, String cover, String name, String location, String detail_image ) {
        this.oid = oid;
        this.cover =  cover;
        this.name = name;
        this.location = location;
        this.detail_image = detail_image;

    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getoid() {
        return oid;
    }

    public String getCover() {
        return cover;
    }
}
