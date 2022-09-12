package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jp.smartgram.adapter.CartAdapter;
import com.jp.smartgram.adapter.WalletAdapter;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.helper.Session;
import com.jp.smartgram.model.Cart;
import com.jp.smartgram.model.Wallet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WalletHistoryActivity extends AppCompatActivity {

    Button walletbtn;
    TextView tvBalance;
    Session session;
    Activity activity;
    ImageView backimg;
    RecyclerView recyclerView;
    WalletAdapter walletAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);
        activity = WalletHistoryActivity.this;
        session = new Session(activity);

        walletbtn = findViewById(R.id.walletbtn);
        tvBalance = findViewById(R.id.tvBalance);
        backimg = findViewById(R.id.backimg);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));



        tvBalance.setText("₹ "+session.getData(Constant.BALANCE));
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        walletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WalletHistoryActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.wallet_layout, viewGroup, false);
                EditText etAmount = dialogView.findViewById(R.id.etAmount);
                Button btnRecharge = dialogView.findViewById(R.id.btnRecharge);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                btnRecharge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!etAmount.getText().toString().trim().equals("")){
                            rechargeWallet(alertDialog,etAmount.getText().toString().trim());
                        }


                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });


                alertDialog.show();
            }
        });
        walletList();
    }

    private void walletList()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Wallet> wallets = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Wallet group = g.fromJson(jsonObject1.toString(), Wallet.class);
                                wallets.add(group);
                            } else {
                                break;
                            }
                        }
                        walletAdapter = new WalletAdapter(activity, wallets);
                        recyclerView.setAdapter(walletAdapter);

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.WALLET_LIST, params, true);

    }

    private void rechargeWallet(AlertDialog alertDialog, String amt)
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.TYPE,"credit");
        params.put(Constant.AMOUNT,amt);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        alertDialog.dismiss();
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.BALANCE,jsonArray.getJSONObject(0).getString(Constant.BALANCE));
                        tvBalance.setText("₹ "+session.getData(Constant.BALANCE));
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.WALLET_URL, params,true);
    }

}