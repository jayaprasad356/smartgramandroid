package com.kapp.smartgram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kapp.smartgram.R;
import com.kapp.smartgram.adapter.TimeSlotAdapter;
import com.kapp.smartgram.model.TimeSlotModel;

import java.util.ArrayList;

public class TimeSlotActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TimeSlotAdapter adapter;
    private ArrayList<TimeSlotModel> timeSlotModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
        recyclerView = findViewById(R.id.TimeSlotRecycler);
        adapter = new TimeSlotAdapter(this,TimeSlotData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<TimeSlotModel> TimeSlotData() {
        ArrayList<TimeSlotModel> timeSlotModelArrayList = new ArrayList<>();
        timeSlotModelArrayList.add(new TimeSlotModel("some date","2:00 AM","2:00Am"));
        timeSlotModelArrayList.add(new TimeSlotModel("some date","2:00 AM","2:00Am"));
        timeSlotModelArrayList.add(new TimeSlotModel("some date","2:00 AM","2:00Am"));
        timeSlotModelArrayList.add(new TimeSlotModel("some date","2:00 AM","2:00Am"));
        return  timeSlotModelArrayList;
    }
}