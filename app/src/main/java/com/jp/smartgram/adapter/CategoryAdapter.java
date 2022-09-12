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
import com.jp.smartgram.ProductActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.helper.Constant;
import com.jp.smartgram.model.Category;


import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Category> categories;

    public CategoryAdapter(Activity activity, ArrayList<Category> categories) {
        this.activity = activity;
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.category_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Category category = categories.get(position);

        Glide.with(activity).load(category.getImage()).placeholder(R.drawable.logo).into(holder.imgCategory);
        holder.tvName.setText(category.getName());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductActivity.class);
                intent.putExtra(Constant.CATEGORY_ID,category.getId());
                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView imgCategory;
        final TextView tvName;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }
}
