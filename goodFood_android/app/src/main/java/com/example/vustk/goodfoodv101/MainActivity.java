package com.example.vustk.goodfoodv101;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.botpager.*;
import com.example.vustk.goodfoodv101.network.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        setupViewPager(viewPager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navi_home:
                                viewPager.setCurrentItem(0);
                                return true;
                            case R.id.navi_categori:
                                viewPager.setCurrentItem(1);
                                return true;
                            case R.id.navi_search:
                                viewPager.setCurrentItem(2);
                                return true;
                            case R.id.navi_profile:
                                viewPager.setCurrentItem(3);
                                return true;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.navi_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.navi_categori);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.navi_search);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.navi_profile);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
    }

    private void setupViewPager(ViewPager viewPager) {
        BottomNavPagerAdapter adapter = new BottomNavPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NaviFragment1());
        adapter.addFragment(new NaviFragment2());
        adapter.addFragment(new NaviFragment3());
        adapter.addFragment(new NaviFragment4());
        viewPager.setAdapter(adapter);
    }
}