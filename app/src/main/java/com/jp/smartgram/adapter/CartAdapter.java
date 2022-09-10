package com.jp.smartgram.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jp.smartgram.CartActivity;
import com.jp.smartgram.ProductActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.helper.ApiConfig;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.model.Cart;
import com.jp.smartgram.model.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Cart> carts;

    public CartAdapter(Activity activity, ArrayList<Cart> carts) {
        this.activity = activity;
        this.carts = carts;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.cart_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, @SuppressLint("RecyclerView") int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Cart cart = carts.get(position);

        Glide.with(activity).load(cart.getImage()).placeholder(R.drawable.logo).into(holder.imgProduct);
        holder.tvName.setText(cart.getProduct_name());
        holder.tvPrice.setText("₹ "+cart.getPrice());
        holder.tvQuantity.setText("("+cart.getQuantity()+")");
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCart(cart.getId(),position);
            }
        });

    }

    private void deleteCart(String id, int position)
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.CART_ID,id);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        carts.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, carts.size());
                        ((CartActivity)activity).cartList();
                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.DELETE_CART_URL, params,true);


    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView imgProduct;
        final TextView tvName,tvPrice,tvQuantity,tvDelete;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }
}
