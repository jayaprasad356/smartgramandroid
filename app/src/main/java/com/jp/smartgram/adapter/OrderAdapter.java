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

    final OrdersActivity ordersActivity;
    ArrayList<Order> orders;

    public OrderAdapter(OrdersActivity ordersActivity, ArrayList<Order> orders) {
        this.ordersActivity = ordersActivity;
        this.orders = orders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ordersActivity).inflate(R.layout.order_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Order order = orders.get(position);

        holder.tvorder.setText(order.getName1());
        holder.tvitem.setText(order.getName2());
        holder.tvpaddy.setText(order.getName3());
        holder.tvplace.setText(order.getName4());
        holder.tvno.setText(order.getName5());
        holder.tvprocessed.setText(order.getName6());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final TextView tvorder;
        final TextView tvitem;
        final TextView tvpaddy;
        final TextView tvplace;
        final TextView tvno;
        final TextView tvprocessed;

        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvorder = itemView.findViewById(R.id.tvorder);
            tvitem = itemView.findViewById(R.id.tvitem);
            tvpaddy = itemView.findViewById(R.id.tvpaddy);
            tvplace = itemView.findViewById(R.id.tvplace);
            tvno = itemView.findViewById(R.id.tvno);
            tvprocessed = itemView.findViewById(R.id.tvprocessed);

        }
    }
}

