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
import com.jp.smartgram.R;
import com.kapp.smartgram.ProductDetailsActivity;
import com.kapp.smartgram.helper.Constant;
import com.kapp.smartgram.model.Product;
import com.kapp.smartgram.model.TimeSlotModel;

import java.util.ArrayList;

public class TimeSlotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<TimeSlotModel> timeSlotModelArrayList ;

    public TimeSlotAdapter(Activity activity, ArrayList<TimeSlotModel> timeSlotModelArrayList) {
        this.activity = activity;
        this.timeSlotModelArrayList = timeSlotModelArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.time_slot_model_lyt, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final TimeSlotModel product = timeSlotModelArrayList.get(position);
        holder.tvDate.setText(product.getDate());
        holder.tvStartTime.setText(product.getStart_time());
        holder.tvEndTime.setText(product.getEnd_time());
    }

    @Override
    public int getItemCount() {
        return timeSlotModelArrayList.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final TextView tvStartTime,tvEndTime,tvDate;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.date);
            tvStartTime = itemView.findViewById(R.id.StartTime);
            tvEndTime = itemView.findViewById(R.id.endTime);

        }
    }
}