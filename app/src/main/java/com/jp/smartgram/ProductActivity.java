package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProductActivity extends AppCompatActivity {

    CardView cardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        cardview = findViewById(R.id.cardview);

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this,ProductDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}