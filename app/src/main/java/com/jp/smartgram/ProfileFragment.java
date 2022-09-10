package com.jp.smartgram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.helper.Session;

public class ProfileFragment extends Fragment {

    View root;
    ImageView imgEdit;
    Activity activity;
    TextView tvName,tvMobile,tvNotification;
    Session session;
    LinearLayout logout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        imgEdit = root.findViewById(R.id.imgEdit);
        tvName = root.findViewById(R.id.tvName);
        tvMobile = root.findViewById(R.id.tvMobile);
        tvNotification = root.findViewById(R.id.tvNotification);
        logout = root.findViewById(R.id.logout);

        activity = getActivity();
        session = new Session(activity);
        tvName.setText(session.getData(Constant.NAME));
        tvMobile.setText("+91 "+session.getData(Constant.MOBILE));
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,UserUpdateActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser(activity);
            }
        });
        tvNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),NotificationActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}