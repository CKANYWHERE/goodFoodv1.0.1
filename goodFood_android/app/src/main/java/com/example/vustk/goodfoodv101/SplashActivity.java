package com.example.vustk.goodfoodv101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.vustk.goodfoodv101.network.NetworkUtil;

public class SplashActivity extends Activity {

    private NetworkUtil networkUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
