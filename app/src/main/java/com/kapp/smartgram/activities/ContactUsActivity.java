package com.kapp.smartgram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kapp.smartgram.R;
import com.kapp.smartgram.helper.ApiConfig;
import com.kapp.smartgram.helper.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactUsActivity extends AppCompatActivity {
    TextView tvName,tvMobile,tvEmail,tvAddress;
    ImageView imgTwitter,imgFacebook,imgWhatsapp,imgInstagram,backimg;
    Activity activity;
    String twitter,whatsapp,facebook,instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        activity = ContactUsActivity.this;
        tvName = findViewById(R.id.tvName);
        tvMobile = findViewById(R.id.tvMobile);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        imgTwitter = findViewById(R.id.imgTwitter);
        imgFacebook = findViewById(R.id.imgFacebook);
        imgWhatsapp = findViewById(R.id.imgWhatsapp);
        imgInstagram = findViewById(R.id.imgInstagram);
        backimg = findViewById(R.id.backimg);
        contactList();
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void contactList()
    {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("LOGIN_RES",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        tvName.setText(jsonArray.getJSONObject(0).getString(Constant.NAME));
                        tvMobile.setText(jsonArray.getJSONObject(0).getString(Constant.MOBILE));
                        tvEmail.setText(jsonArray.getJSONObject(0).getString(Constant.EMAIL));
                        tvAddress.setText(jsonArray.getJSONObject(0).getString(Constant.ADDRESS));
                        twitter = jsonArray.getJSONObject(0).getString(Constant.TWITTER);
                        whatsapp = jsonArray.getJSONObject(0).getString(Constant.WHATSAPP);
                        facebook = jsonArray.getJSONObject(0).getString(Constant.FACEBOOK);
                        instagram = jsonArray.getJSONObject(0).getString(Constant.INSTAGRAM);
                        if (!twitter.isEmpty()){
                            imgTwitter.setVisibility(View.VISIBLE);
                        }
                        if (!whatsapp.isEmpty()){
                            imgWhatsapp.setVisibility(View.VISIBLE);
                        }
                        if (!facebook.isEmpty()){
                            imgFacebook.setVisibility(View.VISIBLE);
                        }
                        if (!instagram.isEmpty()){
                            imgInstagram.setVisibility(View.VISIBLE);
                        }
                        imgTwitter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try {

                                    open(twitter);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        imgFacebook.setOnClickListener(view -> {
                            try {

                                open(facebook);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        });
                        imgInstagram.setOnClickListener(view -> {
                            try {

                                open(instagram);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        });
                        imgWhatsapp.setOnClickListener(view -> {
                            try {

                                open(whatsapp);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        });
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.SETTINGS_LIST, params,true);

    }

    private void open(String url)
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }
}