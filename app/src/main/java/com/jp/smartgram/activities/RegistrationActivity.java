package com.jp.smartgram.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jp.smartgram.MainActivity;
import com.jp.smartgram.OtpActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {


    ImageView backimg;
    Spinner spinGender,spinVillageName,spinDistrict;
    EditText etName,etMobile,etPassword,etConfirmPassword,etEmail,etAddress,etPincode,etOccupation;
    Button btnRegister;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        activity = RegistrationActivity.this;

        backimg = findViewById(R.id.backimg);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etOccupation = findViewById(R.id.etOccupation);
        spinGender = findViewById(R.id.spinGender);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        spinVillageName = findViewById(R.id.spinVillageName);
        etPincode = findViewById(R.id.etPincode);
        spinDistrict = findViewById(R.id.spinDistrict);
        btnRegister = findViewById(R.id.btnRegister);

        backimg.setOnClickListener(v -> onBackPressed());
        defaultspinnerList();

        etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 6){
                    setSpinnerList();
                }
                else {
                    defaultspinnerList();

                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().trim().equals("")){
                    etName.setError("Enter Name");
                    etName.requestFocus();
                }
                else if (etMobile.getText().toString().trim().equals("")){
                    etMobile.setError("Enter Mobile");
                    etMobile.requestFocus();
                }
                else if (etPassword.getText().toString().trim().equals("")){
                    etPassword.setError("Enter Password");
                    etPassword.requestFocus();
                }
                else if (etConfirmPassword.getText().toString().trim().equals("")){
                    etConfirmPassword.setError("Enter Confirm Password");
                    etConfirmPassword.requestFocus();
                }
                else if (etOccupation.getText().toString().trim().equals("")){
                    etOccupation.setError("Enter Occupation");
                    etOccupation.requestFocus();
                }
                else if (spinGender.getSelectedItemId() == 0){
                    Toast.makeText(RegistrationActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
                }
                else if (etEmail.getText().toString().trim().equals("")){
                    etEmail.setError("Enter Email");
                    etEmail.requestFocus();
                }
                else if (etAddress.getText().toString().trim().equals("")){
                    etAddress.setError("Enter Address");
                    etAddress.requestFocus();
                }
                else if (spinVillageName.getSelectedItemId() == 0){
                    Toast.makeText(RegistrationActivity.this, "Select Village Name", Toast.LENGTH_SHORT).show();
                }
                else if (etPincode.getText().toString().trim().equals("")){
                    etPincode.setError("Enter Pincode");
                    etPincode.requestFocus();
                }
                else if (spinDistrict.getSelectedItemId() == 0){
                    Toast.makeText(RegistrationActivity.this, "Select District", Toast.LENGTH_SHORT).show();
                }
                else if (etMobile.getText().length() != 10){
                    etMobile.setError("Invaid");
                    etMobile.requestFocus();
                }
                else if (etPassword.getText().length() < 5){
                    etPassword.setError("Weak Password");
                    etPassword.requestFocus();
                }
                else if (!etPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())){
                    Toast.makeText(RegistrationActivity.this, "Password Does Not Match", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser();
                }
            }
        });
    }

    private void defaultspinnerList() {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("Select Village Name");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinVillageName.setAdapter(adapter);
        spinVillageName.setSelection(0);

        ArrayList<String> arr2 = new ArrayList<String>();
        arr2.add("Select District");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDistrict.setAdapter(adapter2);
        spinDistrict.setSelection(0);
    }

    private void setSpinnerList()
    {
        if (etPincode.getText().length() == 6){
            String pincodeurl = "http://www.postalpincode.in/api/pincode/"+etPincode.getText().toString().trim();
            Map<String, String> params = new HashMap<>();
            ApiConfig.GetVolleyRequest((result, response) -> {
                if (result) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("Status").equals("Success")) {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("PostOffice");
                            Gson g = new Gson();
                            ArrayList<String> villagename = new ArrayList<String>();
                            ArrayList<String> district = new ArrayList<String>();
                            villagename.add("Select Village Name");
                            district.add("Select District");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                String name_ = jsonArray.getJSONObject(i).getString("Name");
                                String district_ = jsonArray.getJSONObject(i).getString("District");



                                if (!district.contains(district_)) {

                                    district.add(district_);

                                }
                                if (!villagename.contains(name_)) {

                                    villagename.add(name_);

                                }
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, villagename);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinVillageName.setAdapter(adapter);
                            spinVillageName.setSelection(0);
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, district);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinDistrict.setAdapter(adapter2);
                            spinDistrict.setSelection(0);


                        }
                        else {
                            Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, activity, pincodeurl, params,true);


        }


    }

    private void registerUser()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.NAME,etName.getText().toString().trim());
        params.put(Constant.MOBILE,etMobile.getText().toString().trim());
        params.put(Constant.PASSWORD,etPassword.getText().toString().trim());
        params.put(Constant.OCCUPATION,etOccupation.getText().toString().trim());
        params.put(Constant.GENDER,spinGender.getSelectedItem().toString().trim());
        params.put(Constant.EMAIL,etEmail.getText().toString().trim());
        params.put(Constant.ADDRESS,etAddress.getText().toString().trim());
        params.put(Constant.VILLAGE,spinVillageName.getSelectedItem().toString().trim());
        params.put(Constant.PINCODE,etPincode.getText().toString().trim());
        params.put(Constant.DISTRICT,spinDistrict.getSelectedItem().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity, OtpActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.SIGNUP_URL, params,true);


    }
}