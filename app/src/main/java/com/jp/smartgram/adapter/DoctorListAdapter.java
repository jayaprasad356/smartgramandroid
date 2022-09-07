package com.jp.smartgram.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jp.smartgram.DoctorListActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.model.Category;
import com.jp.smartgram.model.Item;

import java.util.ArrayList;

public class DoctorListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final DoctorListActivity doctorListActivity;
    ArrayList<Item> items;

    public DoctorListAdapter(DoctorListActivity doctorListActivity, ArrayList<Item> items) {
        this.doctorListActivity = doctorListActivity;
        this.items = items;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(doctorListActivity).inflate(R.layout.list_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Item item = items.get(position);

        Glide.with(doctorListActivity).load(item.getImage()).placeholder(R.drawable.logo).into(holder.imglist);
        holder.tvName.setText(item.getName());


    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView imglist;
        final TextView tvName;
        final Button btnmake;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            imglist = itemView.findViewById(R.id.imglist);
            tvName = itemView.findViewById(R.id.tvName);
            btnmake = itemView.findViewById(R.id.btnmake);

        }
    }
}
