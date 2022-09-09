package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jp.smartgram.helper.Constant;

public class ProductDetailsActivity extends AppCompatActivity {

    Button addbtn;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    ImageView imgproduct;
    TextView tv_productname,tvdescription,brand;
    String getproductname,getdescription,getbrand,getImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        addbtn = findViewById(R.id.addbtn);
        tv_productname = findViewById(R.id.tv_productname);
        tvdescription = findViewById(R.id.tvdescription);
        imgproduct = findViewById(R.id.imgproduct);
        brand = findViewById(R.id.brand);

        getproductname = getIntent().getStringExtra(Constant.PRODUCT_NAME);
        getdescription = getIntent().getStringExtra(Constant.PRODUCT_DESCRIPTION);
        getImage = getIntent().getStringExtra(Constant.PRODUCT_IMAGE);
        getbrand = getIntent().getStringExtra(Constant.PRODUCT_BRAND);


        tv_productname.setText(""+getproductname);
        tvdescription.setText(""+getdescription);
        brand.setText(""+getbrand);






        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });




    }
}