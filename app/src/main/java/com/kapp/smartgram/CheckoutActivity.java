package com.kapp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kapp.smartgram.adapter.CartAdapter;
import com.kapp.smartgram.helper.ApiConfig;
import com.kapp.smartgram.helper.Constant;
import com.kapp.smartgram.helper.Session;
import com.kapp.smartgram.model.Cart;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity implements PaymentStatusListener {
    TextView tvName, tvAddress, tvMobile, tvSubtotal, tvDeliverycharges, tvGrandTotal;
    RecyclerView cartRecycleView;
    Activity activity;
    Session session;
    ImageView backimg;
    CartAdapter cartAdapter;
    Button btnProceed;
    RadioButton rdCOD,rdUpi,rdWallet;
    String DeliveryCharges = "";
    String Address = "";
    String Mobile = "";
    String GrandTotal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        activity = CheckoutActivity.this;
        session = new Session(activity);

        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvMobile = findViewById(R.id.tvMobile);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDeliverycharges = findViewById(R.id.tvDeliverycharges);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);
        cartRecycleView = findViewById(R.id.cartRecycleView);
        backimg = findViewById(R.id.backimg);
        btnProceed = findViewById(R.id.btnProceed);
        rdCOD = findViewById(R.id.rdCOD);
        rdUpi = findViewById(R.id.rdUpi);
        rdWallet = findViewById(R.id.rdWallet);

        rdWallet.setText("Wallet"+"(₹ "+session.getData(Constant.BALANCE)+")");

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        cartRecycleView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        cartList();

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Method = "";
                if (rdCOD.isChecked()){
                    Method = "COD";

                }else if(rdWallet.isChecked()){
                    Method = "Wallet";
                }
                else{
                    Method = "UPI";
                }
                int bal = Integer.parseInt(session.getData(Constant.BALANCE));
                int gt = Integer.parseInt(GrandTotal);
                if (Method.equals("Wallet") && bal < gt){
                    Toast.makeText(activity, "You Haven't Enough Balance", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (Method.equals("UPI")){
                    try {
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
                        String transcId = df.format(c);
                        makePayment(""+Double.parseDouble(GrandTotal), Constant.UPI_ID_VAL, session.getData(Constant.NAME), "Amount", transcId);


                    }catch (Exception e){
                        Log.d("PAYMENT_GATEWAY",e.getMessage());

                    }

                }else {
                    orderProduct(Method);
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


    private void orderProduct(String method)
    {


        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        params.put(Constant.METHOD, method);
        params.put(Constant.DELIVERY_CHARGES, DeliveryCharges);
        params.put(Constant.ADDRESS, DeliveryCharges);
        params.put(Constant.MOBILE, Mobile);
        params.put(Constant.GRAND_TOTAL, GrandTotal);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(activity,MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();


                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.PLACE_ORDER_URL, params, true);


    }

    public void cartList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        DeliveryCharges = jsonObject.getString(Constant.DELIVERY_CHARGES);
                        Mobile = jsonObject.getString(Constant.MOBILE);
                        Address = jsonObject.getString(Constant.NAME) + "," + jsonObject.getString(Constant.ADDRESS);
                        GrandTotal = jsonObject.getString(Constant.GRAND_TOTAL);
                        tvSubtotal.setText(jsonObject.getString(Constant.SUB_TOTAL));
                        tvDeliverycharges.setText(DeliveryCharges);
                        tvName.setText(jsonObject.getString(Constant.NAME));
                        tvAddress.setText(jsonObject.getString(Constant.ADDRESS));
                        tvMobile.setText(jsonObject.getString(Constant.MOBILE));
                        tvGrandTotal.setText(jsonObject.getString(Constant.GRAND_TOTAL));
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.CART_ITEMS);
                        Gson g = new Gson();
                        ArrayList<Cart> carts = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Cart group = g.fromJson(jsonObject1.toString(), Cart.class);
                                carts.add(group);
                            } else {
                                break;
                            }
                        }
                        cartAdapter = new CartAdapter(activity, carts,"checkout");
                        cartRecycleView.setAdapter(cartAdapter);

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CHECKOUT_URL, params, true);


    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {

    }

    @Override
    public void onTransactionSuccess() {
        orderProduct("UPI");

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