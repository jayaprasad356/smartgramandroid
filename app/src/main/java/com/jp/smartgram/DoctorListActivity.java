package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jp.smartgram.adapter.CategoryAdapter;
import com.jp.smartgram.adapter.DoctorListAdapter;
import com.jp.smartgram.model.Category;
import com.jp.smartgram.model.Item;

import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity {

    TextView tvName;
    Button btnmake;
    ImageView imglist;
    RecyclerView listRecycleView;
    DoctorListAdapter doctorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        listRecycleView= findViewById(R.id.listRecycleView);
        tvName= findViewById(R.id.tvName);
        btnmake= findViewById(R.id.btnmake);
        imglist= findViewById(R.id.imglist);

        listRecycleView.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this, LinearLayoutManager.VERTICAL, false));

        doctorList();
    }

    private void doctorList() {
        ArrayList<Item> item = new ArrayList<>();
        Item item1 = new Item("1","Darrell Steward","https://images.newindianexpress.com/uploads/user/imagelibrary/2022/3/23/w1200X800/Farmers-.jpg","Make an Appointment");
        Item item2 = new Item("2","Aravind","https://images.newindianexpress.com/uploads/user/imagelibrary/2022/3/23/w1200X800/Farmers-.jpg","Make an Appointment");
        Item item3 = new Item("3","JP Bro","https://images.newindianexpress.com/uploads/user/imagelibrary/2022/3/23/w1200X800/Farmers-.jpg","Make an Appointment");
        item.add(item1);
        item.add(item2);
        item.add(item3);
        doctorListAdapter = new DoctorListAdapter(DoctorListActivity.this, item);
        listRecycleView.setAdapter(doctorListAdapter);
    }
}