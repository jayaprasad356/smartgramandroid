package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.jp.smartgram.adapter.CategoryAdapter;
import com.jp.smartgram.adapter.OrderAdapter;
import com.jp.smartgram.adapter.ProductAdapter;
import com.jp.smartgram.model.Order;
import com.jp.smartgram.model.Product;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    TextView tvorder,tvitem,tvpaddy,tvplace,tvno,tvprocessed;
    RecyclerView orderRecycleView;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        tvorder = findViewById(R.id.tvorder);
        tvitem = findViewById(R.id.tvitem);
        tvpaddy = findViewById(R.id.tvpaddy);
        tvplace = findViewById(R.id.tvplace);
        tvno = findViewById(R.id.tvno);
        tvprocessed = findViewById(R.id.tvprocessed);
        orderRecycleView = findViewById(R.id.orderRecycleView);

        orderRecycleView.setLayoutManager(new LinearLayoutManager(OrdersActivity.this, LinearLayoutManager.VERTICAL, false));


        orderList();

    }

    private void orderList() {

        ArrayList<Order> orders = new ArrayList<>();
        Order ord1 = new Order("1","Order No. 01","3 item","Paddy","Order placed on 28-07-2022","1500.00","Processed");
        Order ord2 = new Order("2","Order No. 02","2 item","Urea","Order placed on 29-07-2022","1200.00","Processed");
        Order ord3 = new Order("3","Order No. 03","5 item","Npk","Order placed on 31-07-2022","1800.00","Processed");
        orders.add(ord1);
        orders.add(ord2);
        orders.add(ord3);
        orderAdapter = new OrderAdapter(OrdersActivity.this, orders);
        orderRecycleView.setAdapter(orderAdapter);
    }
}