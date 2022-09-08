package com.jp.smartgram.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jp.smartgram.CartActivity;
import com.jp.smartgram.ProductActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.model.Cart;
import com.jp.smartgram.model.Product;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final CartActivity cartActivity;
    ArrayList<Cart> carts;

    public CartAdapter(CartActivity cartActivity, ArrayList<Cart> carts) {
        this.cartActivity = cartActivity;
        this.carts = carts;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cartActivity).inflate(R.layout.cart_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Cart cart = carts.get(position);

        Glide.with(cartActivity).load(cart.getImage()).placeholder(R.drawable.npk).into(holder.imgpack);
        holder.tvurea.setText(cart.getName1());
        holder.tvnumber.setText(cart.getName2());
        holder.tvdiscount.setText(cart.getName3());
        holder.tvpc.setText(cart.getName4());

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView imgpack;
        final TextView tvurea;
        final TextView tvnumber;
        final TextView tvdiscount;
        final TextView tvpc;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            imgpack = itemView.findViewById(R.id.imgpack);
            tvurea = itemView.findViewById(R.id.tvurea);
            tvnumber = itemView.findViewById(R.id.tvnumber);
            tvdiscount = itemView.findViewById(R.id.tvdiscount);
            tvpc = itemView.findViewById(R.id.tvpc);

        }
    }
}
