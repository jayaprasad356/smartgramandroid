package com.kapp.smartgram;

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

import com.kapp.smartgram.activities.ContactUsActivity;
import com.kapp.smartgram.helper.Constant;
import com.kapp.smartgram.helper.Session;

public class ProfileFragment extends Fragment {

    View root;
    ImageView imgEdit;
    Activity activity;
    TextView tvName,tvMobile,tvNotification,tvWallet,tvchangePass;
    Session session;
    LinearLayout logout,contactus,lOrder;

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
        tvWallet = root.findViewById(R.id.tvWallet);
        tvchangePass = root.findViewById(R.id.tvchangePass);
        logout = root.findViewById(R.id.logout);
        lOrder = root.findViewById(R.id.lOrder);
        contactus = root.findViewById(R.id.contactus);

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
        tvWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WalletHistoryActivity.class);
                startActivity(intent);
            }
        });
        tvchangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ChangepasswordActivity.class);
                startActivity(intent);
            }
        });
        lOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),OrdersActivity.class);
                startActivity(intent);

            }
        });
        contactus.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ContactUsActivity.class);
            startActivity(intent);

        });

        return root;
    }
}