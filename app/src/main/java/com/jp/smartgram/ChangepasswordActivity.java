package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangepasswordActivity extends AppCompatActivity {
    ImageView backimg;
    EditText etoldPassword,etNewpassword,etConfirmpassword;
    Button btnChange;
    Session session;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        activity = ChangepasswordActivity.this;
        session = new Session(activity);

        backimg = findViewById(R.id.backimg);
        etoldPassword = findViewById(R.id.etoldPassword);
        etNewpassword = findViewById(R.id.etNewpassword);
        etConfirmpassword = findViewById(R.id.etConfirmpassword);
        btnChange = findViewById(R.id.btnChange);

        backimg.setOnClickListener(view -> onBackPressed());
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etoldPassword.getText().toString().trim().equals("")){
                    etoldPassword.setError("empty");
                }
                else if (etNewpassword.getText().toString().trim().equals("")){
                    etNewpassword.setError("empty");
                }
                else if (etConfirmpassword.getText().toString().trim().equals("")){
                    etConfirmpassword.setError("empty");
                }
                else if (!etConfirmpassword.getText().toString().trim().equals(etNewpassword.getText().toString().trim())){
                    Toast.makeText(ChangepasswordActivity.this, "Password Does Not Match", Toast.LENGTH_SHORT).show();
                }
                else {
                    changePassword();
                }
            }
        });

    }

    private void changePassword()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.OLDPASSWORD,etoldPassword.getText().toString().trim());
        params.put(Constant.NEWPASSWORD,etNewpassword.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
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
        }, activity, Constant.CHANGE_PASSWORD_URL, params,true);



    }
}