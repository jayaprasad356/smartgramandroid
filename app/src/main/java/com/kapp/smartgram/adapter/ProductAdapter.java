package com.kapp.smartgram.adapter;

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
import com.kapp.smartgram.ProductDetailsActivity;
import com.kapp.smartgram.R;
import com.kapp.smartgram.helper.Constant;
import com.kapp.smartgram.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Product> products;

    public ProductAdapter(Activity activity, ArrayList<Product> products) {
        this.activity = activity;
        this.products = products;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.product_item, parent, false);

        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Product product = products.get(position);

        Glide.with(activity).load(product.getImage()).placeholder(R.drawable.logo).into(holder.imgProduct);
        holder.tvName.setText(product.getProduct_name());
        holder.tvPrice.setText("₹ "+product.getPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ProductDetailsActivity.class);
                intent.putExtra(Constant.ID,product.getId());
                intent.putExtra(Constant.PRODUCT_NAME,product.getProduct_name());
                intent.putExtra(Constant.PRICE,product.getPrice());
                intent.putExtra(Constant.PRODUCT_DESCRIPTION,product.getDescription());
                intent.putExtra(Constant.PRODUCT_IMAGE,product.getImage());
                intent.putExtra(Constant.PRODUCT_BRAND,product.getBrand());
                activity.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView imgProduct;
        final TextView tvName,tvPrice;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }
    }
}