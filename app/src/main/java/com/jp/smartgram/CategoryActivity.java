package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jp.smartgram.adapter.CategoryAdapter;
import com.jp.smartgram.adapter.ProductAdapter;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.model.Category;
import com.jp.smartgram.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView categoryRecycleView;
    CategoryAdapter categoryAdapter;
    ImageView backbtn;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        activity = CategoryActivity.this;

        categoryRecycleView = findViewById(R.id.categoryRecycleView);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2);
        categoryRecycleView.setLayoutManager(gridLayoutManager);
        categorylist();



    }

    private void categorylist() {

        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Category> categories = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Category group = g.fromJson(jsonObject1.toString(), Category.class);
                                categories.add(group);
                            } else {
                                break;
                            }
                        }
                        categoryAdapter = new CategoryAdapter(CategoryActivity.this, categories);
                        categoryRecycleView.setAdapter(categoryAdapter);


                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CATEGORY_LIST_URL, params, true);


    }
}