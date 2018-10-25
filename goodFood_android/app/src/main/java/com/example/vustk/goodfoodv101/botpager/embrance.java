package com.example.vustk.goodfoodv101.botpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vustk.goodfoodv101.R;

public class embrance extends Fragment {

    public embrance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bucket, container, false);

    }
}



/*
*   <TextView
                android:id="@+id/textView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignTop="@+id/tv_rank"
                android:layout_toLeftOf="@+id/tv_rank"
                android:layout_toStartOf="@+id/tv_rank"
                android:text="# "
                android:textColor="@color/white"
                android:textSize="20sp"
                android:textStyle="italic" />

            <TextView
                android:id="@+id/tv_rank"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentTop="true"
                android:layout_alignParentRight="true"
                android:textColor="@color/white"
                android:text="1"
                android:textSize="20sp"
                android:textStyle="italic" />
*
* */