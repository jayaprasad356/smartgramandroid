package com.kapp.smartgram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kapp.smartgram.helper.ApiConfig;
import com.kapp.smartgram.helper.Constant;
import com.kapp.smartgram.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserUpdateActivity extends AppCompatActivity {

    ImageView backimg;
    Spinner spinOccupation,spinGender;
    EditText etName,etMobile,etEmail,etAddress,etVillageName,etPincode,etDistrict;
    Button btnRegister;
    Activity activity;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        activity = UserUpdateActivity.this;
        session = new Session(activity);

        backimg = findViewById(R.id.backimg);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        spinOccupation = findViewById(R.id.spinOccupation);
        spinGender = findViewById(R.id.spinGender);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etVillageName = findViewById(R.id.etVillageName);
        etPincode = findViewById(R.id.etPincode);
        etDistrict = findViewById(R.id.etDistrict);
        btnRegister = findViewById(R.id.btnRegister);
        userDetails();

        backimg.setOnClickListener(v -> onBackPressed());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().trim().equals("")){
                    etName.setError("Enter Name");
                    etName.requestFocus();
                }
                else if (spinOccupation.getSelectedItemId() == 0){
                    Toast.makeText(activity, "Select Occupation", Toast.LENGTH_SHORT).show();
                }
                else if (spinGender.getSelectedItemId() == 0){
                    Toast.makeText(activity, "Select Gender", Toast.LENGTH_SHORT).show();
                }
                else if (etEmail.getText().toString().trim().equals("")){
                    etEmail.setError("Enter Email");
                    etEmail.requestFocus();
                }
                else if (etAddress.getText().toString().trim().equals("")){
                    etAddress.setError("Enter Address");
                    etAddress.requestFocus();
                }
                else if (etVillageName.getText().toString().trim().equals("")){
                    etVillageName.setError("Enter Village Name");
                    etVillageName.requestFocus();
                }

                else if (etPincode.getText().toString().trim().equals("")){
                    etPincode.setError("Enter Pincode");
                    etPincode.requestFocus();
                }
                else if (etDistrict.getText().toString().trim().equals("")){
                    etDistrict.setError("Enter District");
                    etDistrict.requestFocus();
                }
                else {
                    updateUser();
                }
            }
        });
    }

    private void userDetails()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        spinOccupation.setSelection(getIndex(spinOccupation, jsonArray.getJSONObject(0).getString(Constant.OCCUPATION)));
                        spinGender.setSelection(getIndex(spinGender, jsonArray.getJSONObject(0).getString(Constant.GENDER)));
                        etName.setText(jsonArray.getJSONObject(0).getString(Constant.NAME));
                        etEmail.setText(jsonArray.getJSONObject(0).getString(Constant.EMAIL));
                        etMobile.setText(jsonArray.getJSONObject(0).getString(Constant.MOBILE));
                        etAddress.setText(jsonArray.getJSONObject(0).getString(Constant.ADDRESS));
                        etVillageName.setText(jsonArray.getJSONObject(0).getString(Constant.VILLAGE));
                        etPincode.setText(jsonArray.getJSONObject(0).getString(Constant.PINCODE));
                        etDistrict.setText(jsonArray.getJSONObject(0).getString(Constant.DISTRICT));
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.USER_DETAILS_URL, params,true);



    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    private void updateUser()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.NAME,etName.getText().toString().trim());
        params.put(Constant.OCCUPATION,spinOccupation.getSelectedItem().toString().trim());
        params.put(Constant.GENDER,spinGender.getSelectedItem().toString().trim());
        params.put(Constant.EMAIL,etEmail.getText().toString().trim());
        params.put(Constant.ADDRESS,etAddress.getText().toString().trim());
        params.put(Constant.VILLAGE,etVillageName.getText().toString().trim());
        params.put(Constant.PINCODE,etPincode.getText().toString().trim());
        params.put(Constant.DISTRICT,etDistrict.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.ID,jsonArray.getJSONObject(0).getString(Constant.ID));
                        session.setData(Constant.NAME,jsonArray.getJSONObject(0).getString(Constant.NAME));
                        session.setData(Constant.MOBILE,jsonArray.getJSONObject(0).getString(Constant.MOBILE));
                        session.setData(Constant.PINCODE,jsonArray.getJSONObject(0).getString(Constant.PINCODE));
                        startActivity(new Intent(activity, MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.UPDATE_USER_URL, params,true);


    }
}