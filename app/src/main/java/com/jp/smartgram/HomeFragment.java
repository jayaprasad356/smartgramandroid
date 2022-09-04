package com.jp.smartgram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class HomeFragment extends Fragment {

    View rootview;
    TextView view_txt;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview =  inflater.inflate(R.layout.fragment_home, container, false);

        view_txt = rootview.findViewById(R.id.view_txt);

        view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ProductActivity.class);
                startActivity(intent);
            }
        });


        return rootview;
    }
}