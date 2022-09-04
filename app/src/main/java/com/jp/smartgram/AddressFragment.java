package com.jp.smartgram;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class AddressFragment extends Fragment {

    View rootview;
    LinearLayout addlayout;

    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview =  inflater.inflate(R.layout.fragment_address, container, false);

        addlayout = rootview.findViewById(R.id.addlayout);

        addlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditAddressActivity.class);
                startActivity(intent);
            }
        });
        return rootview;
    }
}