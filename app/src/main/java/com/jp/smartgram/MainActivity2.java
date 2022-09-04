package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity2 extends AppCompatActivity {

    TabLayout tablayout;
    TabItem address, checkout, payment;
    PagerAdapter pagerAdapter;
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        address = findViewById(R.id.address);
        checkout = findViewById(R.id.checkout);
        payment = findViewById(R.id.payment);
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tablayout.getTabCount());
        viewpager.setAdapter(pagerAdapter);

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());

                if (tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2)
                {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }
}

