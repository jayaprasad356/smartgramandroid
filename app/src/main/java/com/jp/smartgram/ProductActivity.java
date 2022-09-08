package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jp.smartgram.adapter.CategoryAdapter;
import com.jp.smartgram.adapter.ProductAdapter;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.model.Category;
import com.jp.smartgram.model.Product;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    TextView tvnpk;
    TextView tvpohon;
    TextView tvview;
    ImageView imgnpk;
    RecyclerView productRecycleView;
    ProductAdapter productAdapter;
    Activity activity;
    String CategoryId;
    ImageView backimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tvnpk = findViewById(R.id.tvnpk);
        tvpohon = findViewById(R.id.tvpohon);
        tvview = findViewById(R.id.tvview);
        imgnpk = findViewById(R.id.imgnpk);
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

        ArrayList<Product> products = new ArrayList<>();
        Product pro1 = new Product("1","Pupuk NPK","https://images.newindianexpress.com/uploads/user/imagelibrary/2022/3/23/w1200X800/npk-.jpg");
        Product pro2 = new Product("2","Pupuk Urea","https://images.newindianexpress.com/uploads/user/imagelibrary/2022/3/23/w1200X800/urea-.jpg");
        Product pro3 = new Product("3","Pupuk NPK","https://images.newindianexpress.com/uploads/user/imagelibrary/2022/3/23/w1200X800/npk-.jpg");
        products.add(pro1);
        products.add(pro2);
        products.add(pro3);
        productAdapter = new ProductAdapter(ProductActivity.this, products);
        productRecycleView.setAdapter(productAdapter);

    }
}