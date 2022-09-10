package com.jp.smartgram.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jp.smartgram.ProductActivity;
import com.jp.smartgram.ProductDetailsActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.helper.Constant;
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

        Glide.with(productActivity).load(product.getImage()).placeholder(R.drawable.logo).into(holder.imgProduct);
        holder.tvName.setText(product.getProduct_name());
        holder.tvPrice.setText("₹ "+product.getPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(productActivity, ProductDetailsActivity.class);
                intent.putExtra(Constant.ID,product.getId());
                intent.putExtra(Constant.PRODUCT_NAME,product.getProduct_name());
                intent.putExtra(Constant.PRICE,product.getPrice());
                intent.putExtra(Constant.PRODUCT_DESCRIPTION,product.getDescription());
                intent.putExtra(Constant.PRODUCT_IMAGE,product.getImage());
                intent.putExtra(Constant.PRODUCT_BRAND,product.getBrand());
                productActivity.startActivity(intent);

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