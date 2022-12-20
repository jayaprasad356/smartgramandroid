package com.kapp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kapp.smartgram.helper.ApiConfig;
import com.kapp.smartgram.helper.Constant;
import com.kapp.smartgram.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    Button addbtn;
    ImageView backimg;
    ImageView imgproduct;
    TextView tv_productname,tvdescription,brand,tvQuantity;
    String getproductname,getdescription,getbrand,getImage;
    Activity activity;
    Button btnAddToCart;
    ImageButton imgAdd, imgMinus;
    boolean addedcart = false;
    String ProductId;
    String Price;
    Session session;
    TextView tvPrice,tvPincodeStatus;
    Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        activity = ProductDetailsActivity.this;
        session = new Session(activity);

        addbtn = findViewById(R.id.addbtn);
        tv_productname = findViewById(R.id.tv_productname);
        tvdescription = findViewById(R.id.tvdescription);
        imgproduct = findViewById(R.id.imgproduct);
        brand = findViewById(R.id.brand);
        backimg = findViewById(R.id.backimg);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        imgAdd = findViewById(R.id.btnAddQuantity);
        imgMinus = findViewById(R.id.btnMinusQuantity);
        tvPrice = findViewById(R.id.tvPrice);
        tvPincodeStatus = findViewById(R.id.tvPincodeStatus);
        btnChange = findViewById(R.id.btnChange);

        getproductname = getIntent().getStringExtra(Constant.PRODUCT_NAME);
        getdescription = getIntent().getStringExtra(Constant.PRODUCT_DESCRIPTION);
        getImage = getIntent().getStringExtra(Constant.PRODUCT_IMAGE);
        getbrand = getIntent().getStringExtra(Constant.PRODUCT_BRAND);
        ProductId = getIntent().getStringExtra(Constant.ID);
        Price = getIntent().getStringExtra(Constant.PRICE);

        imgMinus.setOnClickListener(v -> addQuantity(false));
        imgAdd.setOnClickListener(v -> addQuantity(true));
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedcart = true;
                addQuantity(true);
            }
        });
        tvQuantity.setText("0");
        Glide.with(activity).load(getImage).placeholder(R.drawable.logo).into(imgproduct);
        tv_productname.setText(""+getproductname);
        tvdescription.setText(""+getdescription);
        brand.setText(""+getbrand);
        tvPrice.setText(Price);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addedcart){
                    addtoCart();

                }
                else {
                    Toast.makeText(activity, "Add Quantity", Toast.LENGTH_SHORT).show();
                }

            }
        });
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addbtn.setEnabled(false);
        addbtn.setBackgroundTintList(activity.getResources().getColorStateList(R.color.gray));
        tvPincodeStatus.setTextColor(ContextCompat.getColor(activity, R.color.red));
        tvPincodeStatus.setText("Item is not deliver at "+session.getData(Constant.PINCODE));
        checkDeliverPincode();
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,UserUpdateActivity.class);
                startActivity(intent);
            }
        });




    }
    private void checkDeliverPincode()
    {
        Log.d("DELIVER_PIN",session.getData(Constant.PINCODE) + ProductId);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PINCODE,session.getData(Constant.PINCODE));
        params.put(Constant.PRODUCT_ID,ProductId);
        params.put(Constant.CHECK_DELIVERABILITY,"1");
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("DELIVER_RES",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        addbtn.setEnabled(true);
                        addbtn.setBackgroundTintList(activity.getResources().getColorStateList(R.color.primary));
                        tvPincodeStatus.setTextColor(ContextCompat.getColor(activity, R.color.green));
                        tvPincodeStatus.setText("Item is deliver at "+session.getData(Constant.PINCODE));
                    }
                    else {
                        addbtn.setEnabled(false);
                        addbtn.setBackgroundTintList(activity.getResources().getColorStateList(R.color.gray));
                        tvPincodeStatus.setTextColor(ContextCompat.getColor(activity, R.color.red));
                        tvPincodeStatus.setText("Item is not deliver at "+session.getData(Constant.PINCODE));
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CHECK_DELIVER_URL, params,true);


    }


    private void addtoCart()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.PRODUCT_ID,ProductId);
        params.put(Constant.QUANTITY,tvQuantity.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, "Product Added To Cart", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity,CartActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.ADD_TO_CART_URL, params,true);



    }

    public void addQuantity(boolean isAdd) {
        int count = Integer.parseInt(tvQuantity.getText().toString());
        if (isAdd) {
            count++;
            tvQuantity.setText("" + count);
        }else {
            count--;
            tvQuantity.setText("" + count);
        }
        if (count == 0) {
            btnAddToCart.setVisibility(View.VISIBLE);
        } else {
            btnAddToCart.setVisibility(View.GONE);
        }
    }
}