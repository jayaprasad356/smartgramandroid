package com.jp.smartgram.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jp.smartgram.OrdersActivity;
import com.jp.smartgram.ProductActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.model.Category;
import com.jp.smartgram.model.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Order> orders;

    public OrderAdapter(Activity activity, ArrayList<Order> orders) {
        this.activity = activity;
        this.orders = orders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.order_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Order order = orders.get(position);

        holder.tvId.setText("Order No. "+order.getId());
        holder.tvQuantity.setText(order.getQuantity()+" items");
        holder.tvProductname.setText(order.getProduct_name());
        holder.tvTotal.setText(order.getTotal());
        holder.tvOrderdate.setText("Order placed on "+order.getOrder_date());
        holder.tvStatus.setText(order.getStatus());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final TextView tvId,tvQuantity,tvProductname,tvOrderdate;
        final TextView tvTotal,tvStatus;

        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvProductname = itemView.findViewById(R.id.tvProductname);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvOrderdate = itemView.findViewById(R.id.tvOrderdate);
            tvStatus = itemView.findViewById(R.id.tvStatus);

        }
    }
}

