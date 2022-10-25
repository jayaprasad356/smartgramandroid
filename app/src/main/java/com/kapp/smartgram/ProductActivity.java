package com.kapp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kapp.smartgram.adapter.ProductAdapter;
import com.kapp.smartgram.helper.ApiConfig;
import com.kapp.smartgram.helper.Constant;
import com.kapp.smartgram.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {


    RecyclerView productRecycleView;
    ProductAdapter productAdapter;
    Activity activity;
    String CategoryId;
    ImageView backimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        backimg = findViewById(R.id.backimg);
        productRecycleView = findViewById(R.id.productRecycleView);
        activity = ProductActivity.this;

        CategoryId = getIntent().getStringExtra(Constant.CATEGORY_ID);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2);
        productRecycleView.setLayoutManager(gridLayoutManager);
        productList();
    }

    private void productList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.CATEGORY_ID,CategoryId);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("PRODUCTS_RES",response);

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Product> products = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Product group = g.fromJson(jsonObject1.toString(), Product.class);
                                products.add(group);
                            } else {
                                break;
                            }
                        }
                        productAdapter = new ProductAdapter(ProductActivity.this, products);
                        productRecycleView.setAdapter(productAdapter);


                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.PRODUCT_LIST_URL, params, true);



    }
}