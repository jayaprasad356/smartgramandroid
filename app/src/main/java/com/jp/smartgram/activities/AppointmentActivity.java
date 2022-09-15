package com.jp.smartgram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jp.smartgram.DoctorListActivity;
import com.jp.smartgram.MainActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.helper.Session;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AppointmentActivity extends AppCompatActivity implements PaymentStatusListener {
    EditText etName, etMobile, etAge, etDiseases, etPlace, etDescription, etHistory;
    Button btnBookappointment;
    Activity activity;
    String DoctorId;
    Session session;
    TextView tvDate,tvTime;
    String AppointmentDate = "",AppointmentTime = "",Fees = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        activity = AppointmentActivity.this;
        session = new Session(activity);
        DoctorId = getIntent().getStringExtra(Constant.DOCTOR_ID);
        Fees = getIntent().getStringExtra(Constant.FEES);
        btnBookappointment = findViewById(R.id.btnBookappointment);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etAge = findViewById(R.id.etAge);
        etDiseases = findViewById(R.id.etDiseases);
        etPlace = findViewById(R.id.etPlace);
        etDescription = findViewById(R.id.etDescription);
        etHistory = findViewById(R.id.etHistory);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });



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

                } else if (AppointmentDate.equals("")) {
                    Toast.makeText(activity, "Select Date", Toast.LENGTH_SHORT).show();

                } else if (AppointmentTime.equals("")) {
                    Toast.makeText(activity, "Select Time", Toast.LENGTH_SHORT).show();

                }
                else {
                    try {
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
                        String transcId = df.format(c);
                        makePayment(""+Double.parseDouble(Fees), Constant.UPI_ID_VAL, session.getData(Constant.NAME), "Amount", transcId);


                    }catch (Exception e){
                        Log.d("PAYMENT_GATEWAY",e.getMessage());

                    }


                }

            }
        });
    }
    private void makePayment(String amount, String upi, String name, String desc, String transactionId) {
        // on below line we are calling an easy payment method and passing
        // all parameters to it such as upi id,name, description and others.
        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(this)
                // on below line we are adding upi id.
                .setPayeeVpa(upi)
                // on below line we are setting name to which we are making oayment.
                .setPayeeName(name)
                // on below line we are passing transaction id.
                .setTransactionId(transactionId)
                // on below line we are passing transaction ref id.
                .setTransactionRefId(transactionId)
                // on below line we are adding description to payment.
                .setDescription(desc)
                // on below line we are passing amount which is being paid.
                .setAmount(amount)
                // on below line we are calling a build method to build this ui.
                .build();
        // on below line we are calling a start
        // payment method to start a payment.
        easyUpiPayment.startPayment();

        // on below line we are calling a set payment
        // status listener method to call other payment methods.
        easyUpiPayment.setPaymentStatusListener(this);
    }


    private void showTimePicker()
    {
        int mHour,mMinute;
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        AppointmentTime = hourOfDay + ":" + minute;

                        tvTime.setText(AppointmentTime);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void showDatePicker()
    {
        int mYear,mMonth,mDay;
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                    monthOfYear = monthOfYear + 1;
                    AppointmentDate = year + "-"+convertTwodigit(monthOfYear)+"-"+ convertTwodigit(dayOfMonth);
                    tvDate.setText(AppointmentDate);

                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private String convertTwodigit(int s)
    {
        long val = (long) s;
        String format = "%1$02d";
        return (String.format(format,val));
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
        params.put(Constant.APPOINTMENT_DATE, AppointmentDate);
        params.put(Constant.APPOINTMENT_TIME, AppointmentTime);
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

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {

    }

    @Override
    public void onTransactionSuccess() {
        bookAppointment();

    }

    @Override
    public void onTransactionSubmitted() {

    }

    @Override
    public void onTransactionFailed() {
        Toast.makeText(activity, "Payment Failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTransactionCancelled() {

    }

    @Override
    public void onAppNotFound() {

    }
}