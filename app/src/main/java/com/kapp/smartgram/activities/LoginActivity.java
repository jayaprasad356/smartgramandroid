package com.kapp.smartgram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kapp.smartgram.MainActivity;
import com.kapp.smartgram.R;
import com.kapp.smartgram.helper.ApiConfig;
import com.kapp.smartgram.helper.Constant;
import com.kapp.smartgram.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button loginbtn;
    TextView signupbtn;
    EditText etMobile,etPassword;
    Activity activity;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = LoginActivity.this;
        session =  new Session(activity);

        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.signupbtn);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        signupbtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        loginbtn.setOnClickListener(v -> {
            if (etMobile.getText().toString().trim().equals("")){
                etMobile.setError("Enter Mobile Number");
                etMobile.requestFocus();
            }
            else if (etPassword.getText().toString().trim().equals("")){
                etPassword.setError("Enter Password");
                etPassword.requestFocus();
            }
            else if (etMobile.getText().length() != 10){
                etMobile.setError("Invalid");
                etMobile.requestFocus();
            }
            else if (etPassword.getText().length() < 5){
                etPassword.setError("Weak Password");
                etPassword.requestFocus();
            }
            else{
                loginUser();
            }

        });
    }

    private void loginUser()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MOBILE,etMobile.getText().toString().trim());
        params.put(Constant.PASSWORD,etPassword.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("LOGIN_RES",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setBoolean("is_logged_in", true);
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
        }, activity, Constant.LOGIN_URL, params,true);



    }

}