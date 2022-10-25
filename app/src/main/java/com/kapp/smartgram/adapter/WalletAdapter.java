package com.kapp.smartgram.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kapp.smartgram.R;
import com.kapp.smartgram.model.Wallet;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Wallet> wallets;

    public WalletAdapter(Activity activity, ArrayList<Wallet> wallets) {
        this.activity = activity;
        this.wallets = wallets;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.wallet_transaction_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Wallet wallet = wallets.get(position);
        if (wallet.getType().equals("credit")){
            holder.tvAmount.setTextColor(ContextCompat.getColor(activity, R.color.green));
            holder.tvAmount.setText("+"+wallet.getAmount());
            holder.tvDate.setText(wallet.getDate());
            holder.tvBalance.setText(wallet.getType());

        }
        else {
            holder.tvAmount.setTextColor(ContextCompat.getColor(activity, R.color.red));
            holder.tvAmount.setText("-"+wallet.getAmount());
            holder.tvDate.setText(wallet.getDate());
            holder.tvBalance.setText(wallet.getType());

        }



    }

    @Override
    public int getItemCount()
    {
        return wallets.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {
        final TextView tvAmount,tvDate,tvBalance;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvBalance = itemView.findViewById(R.id.tvBalance);
        }
    }
}
