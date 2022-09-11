package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jp.smartgram.adapter.CartAdapter;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.helper.Session;
import com.jp.smartgram.model.Cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    TextView tvName, tvAddress, tvMobile, tvSubtotal, tvDeliverycharges, tvGrandTotal;
    RecyclerView cartRecycleView;
    Activity activity;
    Session session;
    ImageView backimg;
    CartAdapter cartAdapter;
    Button btnProceed;
    RadioButton rdCOD,rdUpi,rdWallet;
    String DeliveryCharges = "";
    String Address = "";
    String Mobile = "";
    String GrandTotal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        activity = CheckoutActivity.this;
        session = new Session(activity);

        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvMobile = findViewById(R.id.tvMobile);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDeliverycharges = findViewById(R.id.tvDeliverycharges);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);
        cartRecycleView = findViewById(R.id.cartRecycleView);
        backimg = findViewById(R.id.backimg);
        btnProceed = findViewById(R.id.btnProceed);
        rdCOD = findViewById(R.id.rdCOD);
        rdUpi = findViewById(R.id.rdUpi);
        rdWallet = findViewById(R.id.rdWallet);

        rdWallet.setText("Wallet"+"(₹ "+session.getData(Constant.BALANCE)+")");

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        cartRecycleView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        cartList();

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderProduct();
            }
        });


    }

    private void orderProduct()
    {
        String Method = "";
        if (rdCOD.isChecked()){
            Method = "COD";

        }else if(rdWallet.isChecked()){
            Method = "Wallet";
        }
        else{
            Method = "UPI";
        }
        int bal = Integer.parseInt(session.getData(Constant.BALANCE));
        int gt = Integer.parseInt(GrandTotal);
        if (Method.equals("Wallet") && bal < gt){
            Toast.makeText(activity, "You Haven't Enough Balance", Toast.LENGTH_SHORT).show();
            return;

        }

        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        params.put(Constant.METHOD, Method);
        params.put(Constant.DELIVERY_CHARGES, DeliveryCharges);
        params.put(Constant.ADDRESS, DeliveryCharges);
        params.put(Constant.MOBILE, Mobile);
        params.put(Constant.GRAND_TOTAL, GrandTotal);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(activity,MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();


                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.PLACE_ORDER_URL, params, true);


    }

    public void cartList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        DeliveryCharges = jsonObject.getString(Constant.DELIVERY_CHARGES);
                        Mobile = jsonObject.getString(Constant.MOBILE);
                        Address = jsonObject.getString(Constant.NAME) + "," + jsonObject.getString(Constant.ADDRESS);
                        GrandTotal = jsonObject.getString(Constant.GRAND_TOTAL);
                        tvSubtotal.setText(jsonObject.getString(Constant.SUB_TOTAL));
                        tvDeliverycharges.setText(DeliveryCharges);
                        tvName.setText(jsonObject.getString(Constant.NAME));
                        tvAddress.setText(jsonObject.getString(Constant.ADDRESS));
                        tvMobile.setText(jsonObject.getString(Constant.MOBILE));
                        tvGrandTotal.setText(jsonObject.getString(Constant.GRAND_TOTAL));
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.CART_ITEMS);
                        Gson g = new Gson();
                        ArrayList<Cart> carts = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Cart group = g.fromJson(jsonObject1.toString(), Cart.class);
                                carts.add(group);
                            } else {
                                break;
                            }
                        }
                        cartAdapter = new CartAdapter(activity, carts,"checkout");
                        cartRecycleView.setAdapter(cartAdapter);

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CHECKOUT_URL, params, true);


    }
}