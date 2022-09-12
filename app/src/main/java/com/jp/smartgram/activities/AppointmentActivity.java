package com.jp.smartgram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jp.smartgram.DoctorListActivity;
import com.jp.smartgram.MainActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AppointmentActivity extends AppCompatActivity {
    EditText etName, etMobile, etAge, etDiseases, etPlace, etDescription, etHistory;
    Button btnBookappointment;
    Activity activity;
    String DoctorId;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        activity = AppointmentActivity.this;
        session = new Session(activity);
        DoctorId = getIntent().getStringExtra(Constant.DOCTOR_ID);
        btnBookappointment = findViewById(R.id.btnBookappointment);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etAge = findViewById(R.id.etAge);
        etDiseases = findViewById(R.id.etDiseases);
        etPlace = findViewById(R.id.etPlace);
        etDescription = findViewById(R.id.etDescription);
        etHistory = findViewById(R.id.etHistory);

        btnBookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().trim().equals("")) {
                    etName.setError("empty");
                    etName.requestFocus();


                } else if (etMobile.getText().toString().trim().equals("")) {
                    etMobile.setError("empty");
                    etMobile.requestFocus();

                } else if (etMobile.getText().length() != 10) {
                    etMobile.setError("Invaid");
                    etMobile.requestFocus();
                } else if (etAge.getText().toString().trim().equals("")) {
                    etAge.setError("empty");
                    etAge.requestFocus();

                } else if (etDiseases.getText().toString().trim().equals("")) {
                    etDiseases.setError("empty");
                    etDiseases.requestFocus();

                } else if (etPlace.getText().toString().trim().equals("")) {
                    etPlace.setError("empty");
                    etPlace.requestFocus();

                } else if (etDescription.getText().toString().trim().equals("")) {
                    etDescription.setError("empty");
                    etDescription.requestFocus();

                } else if (etHistory.getText().toString().trim().equals("")) {
                    etHistory.setError("empty");
                    etHistory.requestFocus();

                } else {
                    bookAppointment();

                }

            }
        });
    }
    public void bookAppointment()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        params.put(Constant.DOCTOR_ID, DoctorId);
        params.put(Constant.NAME, etName.getText().toString().trim());
        params.put(Constant.MOBILE, etMobile.getText().toString().trim());
        params.put(Constant.AGE, etAge.getText().toString().trim());
        params.put(Constant.DISEASE, etDiseases.getText().toString().trim());
        params.put(Constant.PLACE, etPlace.getText().toString().trim());
        params.put(Constant.DESCRIPTION, etDescription.getText().toString().trim());
        params.put(Constant.HISTORY, etHistory.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS))
                    {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, MainActivity.class);
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
}