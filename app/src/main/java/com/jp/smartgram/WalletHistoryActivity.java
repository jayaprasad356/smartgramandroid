package com.jp.smartgram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WalletHistoryActivity extends AppCompatActivity {

    Button walletbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);

        walletbtn = findViewById(R.id.walletbtn);


        walletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletHistoryActivity.this,OrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}