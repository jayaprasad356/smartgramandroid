package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class ProductDetailsActivity extends AppCompatActivity {

    Button addbtn;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        addbtn = findViewById(R.id.addbtn);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });



        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);

        viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager);
    }
}