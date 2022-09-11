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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jp.smartgram.adapter.CartAdapter;
import com.jp.smartgram.adapter.CategoryAdapter;
import com.jp.smartgram.adapter.OrderAdapter;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.helper.Session;
import com.jp.smartgram.model.Cart;
import com.jp.smartgram.model.Category;
import com.jp.smartgram.model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    Button conbtn;
    ImageView backimg;
    RecyclerView cartRecycleView;
    CartAdapter cartAdapter;
    Activity activity;
    Session session;
    RelativeLayout r1;
    ImageView imgEmpty;
    TextView tvQuantity,tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        activity = CartActivity.this;
        session = new Session(activity);
        conbtn = findViewById(R.id.conbtn);
        backimg = findViewById(R.id.backimg);
        imgEmpty = findViewById(R.id.imgEmpty);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        r1 = findViewById(R.id.r1);
        cartRecycleView = findViewById(R.id.cartRecycleView);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cartRecycleView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
        conbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,CheckoutActivity.class);
                startActivity(intent);
            }
        });

        cartList();
    }

    public void cartList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        tvTotalPrice.setText("₹ "+jsonObject.getString(Constant.TOTAL_PRICE));
                        tvQuantity.setText(jsonObject.getString(Constant.TOTAL_ITEMES)+" Items");
                        r1.setVisibility(View.VISIBLE);
                        imgEmpty.setVisibility(View.GONE);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
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
                        cartAdapter = new CartAdapter(activity, carts,"cart");
                        cartRecycleView.setAdapter(cartAdapter);

                    } else {
                        r1.setVisibility(View.GONE);
                        imgEmpty.setVisibility(View.VISIBLE);
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    r1.setVisibility(View.GONE);
                    imgEmpty.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CART_LIST_URL, params, true);


    }
}