package com.jp.smartgram;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DoctorFragment extends Fragment {

    View rootview;
    Button bookbtn;

    public DoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_doctor, container, false);

        bookbtn = rootview.findViewById(R.id.bookbtn);

        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DoctorListActivity.class);
                startActivity(intent);
            }
        });

        return rootview;
    }
}