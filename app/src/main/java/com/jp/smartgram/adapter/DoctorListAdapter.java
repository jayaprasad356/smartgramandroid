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
import com.jp.smartgram.CartActivity;
import com.jp.smartgram.DoctorListActivity;
import com.jp.smartgram.R;
import com.jp.smartgram.model.Doctor;
import com.jp.smartgram.model.Item;

import java.util.ArrayList;

public class DoctorListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Doctor> doctors;

    public DoctorListAdapter(Activity activity, ArrayList<Doctor> doctors) {
        this.activity = activity;
        this.doctors = doctors;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.doctor_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Doctor doctor = doctors.get(position);

        Glide.with(activity).load(doctor.getImage()).placeholder(R.drawable.logo).into(holder.imgDoctor);
        holder.tvName.setText(doctor.getName());
        holder.tvRole.setText(doctor.getRole());
        holder.tvExperience.setText(doctor.getExperience());
        holder.tvFees.setText(doctor.getFees());
        holder.btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DoctorListActivity)activity).bookAppointment(doctor.getId());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return doctors.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {
        final ImageView imgDoctor;
        final TextView tvName,tvRole,tvExperience,tvFees;
        final Button btnAppointment;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvExperience = itemView.findViewById(R.id.tvExperience);
            tvFees = itemView.findViewById(R.id.tvFees);
            imgDoctor = itemView.findViewById(R.id.imgDoctor);
            btnAppointment = itemView.findViewById(R.id.btnAppointment);
        }
    }
}
