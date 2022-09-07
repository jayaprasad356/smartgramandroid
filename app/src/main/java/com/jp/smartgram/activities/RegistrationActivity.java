package com.jp.smartgram.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jp.smartgram.R;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {


    ImageView backimg;
    Spinner spinOccupation,spinGender;
    EditText etName,etMobile,etPassword,etConfirmPassword,etEmail,etAddress,etVillageName,etPincode,etDistrict;
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
        spinOccupation = findViewById(R.id.spinOccupation);
        spinGender = findViewById(R.id.spinGender);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etVillageName = findViewById(R.id.etVillageName);
        etPincode = findViewById(R.id.etPincode);
        etDistrict = findViewById(R.id.etDistrict);
        btnRegister = findViewById(R.id.btnRegister);

        backimg.setOnClickListener(v -> onBackPressed());

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
                else if (spinOccupation.getSelectedItemId() == 0){
                    Toast.makeText(RegistrationActivity.this, "Select Occupation", Toast.LENGTH_SHORT).show();
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

    private void registerUser()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.NAME,etName.getText().toString().trim());
        params.put(Constant.MOBILE,etMobile.getText().toString().trim());
        params.put(Constant.PASSWORD,etPassword.getText().toString().trim());
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
                        startActivity(new Intent(activity, LoginActivity.class));
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