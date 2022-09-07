package com.jp.smartgram.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jp.smartgram.ProductActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.model.Category;
import com.jp.smartgram.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final ProductActivity productActivity;
    ArrayList<Product> products;

    public ProductAdapter(ProductActivity productActivity, ArrayList<Product> products) {
        this.productActivity = productActivity;
        this.products = products;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(productActivity).inflate(R.layout.product_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Product product = products.get(position);

        Glide.with(productActivity).load(product.getImage()).placeholder(R.drawable.npk).into(holder.imgnpk);
        holder.tvnpk.setText(product.getName());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView imgnpk;
        final TextView tvnpk;
        final TextView tvpohon;
        final TextView tvview;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            imgnpk = itemView.findViewById(R.id.imgnpk);
            tvnpk = itemView.findViewById(R.id.tvnpk);
            tvpohon = itemView.findViewById(R.id.tvpohon);
            tvview = itemView.findViewById(R.id.tvview);

        }
    }
}