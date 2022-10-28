package com.kapp.smartgram.activities;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.Slider;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.kapp.smartgram.MainActivity;
import com.kapp.smartgram.OtpActivity;
import com.kapp.smartgram.R;
import com.kapp.smartgram.helper.ApiConfig;
import com.kapp.smartgram.helper.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class RegistrationActivity extends AppCompatActivity {


    ImageView backimg;
    Spinner spinGender,spinVillageName,spinDistrict;
    EditText etName,etMobile,etPassword,etConfirmPassword,etEmail,etAddress,etPincode,etOccupation;
    Button btnRegister;
    Activity activity;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private static final String TAG = "OTP";
    private FirebaseAuth mAuth;
    private String mVerificationId = "";
    private PhoneAuthProvider.ForceResendingToken mResendToken;

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
        mAuth = FirebaseAuth.getInstance();

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
                    showOtp();
                    //registerUser();
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
    private void showOtp() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.otp_bottom_layout);

        OtpTextView otpTextview = bottomSheetDialog.findViewById(R.id.otpTextview);
        TextView tvNumber = bottomSheetDialog.findViewById(R.id.tvNumber);
        Button btnContinue = bottomSheetDialog.findViewById(R.id.btnContinue);
        tvNumber.setText("OTP sent to "+etMobile.getText().toString().trim());

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpTextview.getOTP().length() == 6){
                    if (!mVerificationId.equals("")){
                        verifyPhoneNumberWithCode(mVerificationId,otpTextview.getOTP().toString());

                    }
                    else {
                        Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Toast.makeText(activity, "Enter OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;

            }
        };
        startPhoneNumberVerification("+91"+etMobile.getText().toString().trim());

    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
        // [END verify_with_code]
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            registerUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
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
                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(activity, LoginActivity.class));
                    finish();
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.SIGNUP_URL, params,true);


    }
}