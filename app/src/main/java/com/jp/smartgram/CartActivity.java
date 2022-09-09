package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jp.smartgram.adapter.CartAdapter;
import com.jp.smartgram.adapter.OrderAdapter;
import com.jp.smartgram.model.Cart;
import com.jp.smartgram.model.Order;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    Button conbtn;
    ImageView imgpack;
    TextView tvurea,tvnumber,tvdiscount,tvpc;
    RecyclerView cartRecycleView;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        conbtn = findViewById(R.id.conbtn);
        imgpack = findViewById(R.id.imgpack);
        tvurea = findViewById(R.id.tvurea);
        tvnumber = findViewById(R.id.tvnumber);
        tvdiscount = findViewById(R.id.tvdiscount);
        tvpc = findViewById(R.id.tvpc);
        cartRecycleView = findViewById(R.id.cartRecycleView);

        cartRecycleView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));


        conbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });

        cartList();
    }

    private void cartList() {

        ArrayList<Cart> carts = new ArrayList<>();
        Cart crt1 = new Cart("1","NPK Urea","3000","3000","1 pc","https://images.newindianexpress.com/uploads/user/imagelibrary/2022/3/23/w1200X800/npk-.jpg");
        Cart crt2 = new Cart("2","NPK Urea","3000","3000","1 pc","https://images.newindianexpress.com/uploads/user/imagelibrary/2022/3/23/w1200X800/Urea-.jpg");
        carts.add(crt1);
        carts.add(crt2);
        cartAdapter = new CartAdapter(CartActivity.this, carts);
        cartRecycleView.setAdapter(cartAdapter);

    }
}