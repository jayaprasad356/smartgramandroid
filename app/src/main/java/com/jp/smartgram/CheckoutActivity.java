package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvMobile = findViewById(R.id.tvMobile);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDeliverycharges = findViewById(R.id.tvDeliverycharges);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);
        cartRecycleView = findViewById(R.id.cartRecycleView);

        activity = CheckoutActivity.this;
        session = new Session(activity);

        cartRecycleView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        cartList();


    }

    public void cartList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        tvSubtotal.setText(jsonObject.getString(Constant.SUB_TOTAL));
                        tvDeliverycharges.setText(jsonObject.getString(Constant.DELIVERY_CHARGES));
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
                        cartAdapter = new CartAdapter(activity, carts);
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