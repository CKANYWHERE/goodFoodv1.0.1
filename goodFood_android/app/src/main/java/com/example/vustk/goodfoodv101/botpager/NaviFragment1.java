package com.example.vustk.goodfoodv101.botpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vustk.goodfoodv101.R;
import com.example.vustk.goodfoodv101.tabPager.*;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class NaviFragment1 extends Fragment {
    ViewPager viewPager;
    PageIndicator mIndicator;

    public NaviFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.bottom_fragment1, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        setupViewPager(viewPager);


        mIndicator = (TabPageIndicator) rootView.findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(new TabFragment1());
        adapter.addFragment(new TabFragment2());
        adapter.addFragment(new TabFragment3());
        adapter.addFragment(new TabFragment4());
        viewPager.setAdapter(adapter);
    }

}