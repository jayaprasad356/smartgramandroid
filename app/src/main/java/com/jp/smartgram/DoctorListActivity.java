package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jp.smartgram.adapter.CartAdapter;
import com.jp.smartgram.adapter.CategoryAdapter;
import com.jp.smartgram.adapter.DoctorListAdapter;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.helper.Session;
import com.jp.smartgram.model.Cart;
import com.jp.smartgram.model.Category;
import com.jp.smartgram.model.Doctor;
import com.jp.smartgram.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorListActivity extends AppCompatActivity {


    RecyclerView listRecycleView;
    DoctorListAdapter doctorListAdapter;
    Activity activity;
    String Name,Mobile,Age,Disease,Place,Description,History;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        activity = DoctorListActivity.this;
        session = new Session(activity);
        listRecycleView= findViewById(R.id.listRecycleView);
        Name = getIntent().getStringExtra(Constant.NAME);
        Mobile = getIntent().getStringExtra(Constant.MOBILE);
        Age = getIntent().getStringExtra(Constant.AGE);
        Disease = getIntent().getStringExtra(Constant.DISEASE);
        Place = getIntent().getStringExtra(Constant.PLACE);
        Description = getIntent().getStringExtra(Constant.DESCRIPTION);
        History = getIntent().getStringExtra(Constant.HISTORY);

        listRecycleView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        doctorList();
    }
    public void bookAppointment(String doctor_id)
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        params.put(Constant.DOCTOR_ID, doctor_id);
        params.put(Constant.NAME, Name);
        params.put(Constant.MOBILE, Mobile);
        params.put(Constant.AGE, Age);
        params.put(Constant.DISEASE, Disease);
        params.put(Constant.PLACE, Place);
        params.put(Constant.DESCRIPTION, Description);
        params.put(Constant.HISTORY, History);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS))
                    {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity,MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.ADD_APPOINTMENT_URL, params, true);

    }

    private void doctorList() {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Doctor> doctors = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Doctor group = g.fromJson(jsonObject1.toString(), Doctor.class);
                                doctors.add(group);
                            } else {
                                break;
                            }
                        }
                        doctorListAdapter = new DoctorListAdapter(activity, doctors);
                        listRecycleView.setAdapter(doctorListAdapter);

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.DOCTOR_LIST_URL, params, true);





    }
}