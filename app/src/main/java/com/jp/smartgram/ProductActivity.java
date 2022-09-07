package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jp.smartgram.adapter.CategoryAdapter;
import com.jp.smartgram.adapter.ProductAdapter;
import com.jp.smartgram.model.Category;
import com.jp.smartgram.model.Product;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    TextView tvnpk;
    TextView tvpohon;
    TextView tvview;
    ImageView imgnpk;
    RecyclerView productRecycleView;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tvnpk = findViewById(R.id.tvnpk);
        tvpohon = findViewById(R.id.tvpohon);
        tvview = findViewById(R.id.tvview);
        imgnpk = findViewById(R.id.imgnpk);
        productRecycleView = findViewById(R.id.productRecycleView);

        productRecycleView.setLayoutManager(new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.VERTICAL, false));

        productList();
    }

    private void productList() {
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