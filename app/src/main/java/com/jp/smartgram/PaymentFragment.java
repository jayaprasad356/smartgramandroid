package com.jp.smartgram;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PaymentFragment extends Fragment {


    View rootview;
    Button continuebtn;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_payment, container, false);

        continuebtn = rootview.findViewById(R.id.continuebtn);

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalletHistoryActivity.class);
                startActivity(intent);
            }
        });

        return rootview;
    }
}