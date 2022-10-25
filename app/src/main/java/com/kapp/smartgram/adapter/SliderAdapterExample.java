package com.kapp.smartgram.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kapp.smartgram.ProductActivity;
import com.kapp.smartgram.R;
import com.kapp.smartgram.helper.Constant;
import com.kapp.smartgram.model.Slide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private ArrayList<Slide> mSliderItems = new ArrayList<>();

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    public void renewItems(ArrayList<Slide> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

//    public void addItem(A sliderItem) {
//        this.mSliderItems.add(sliderItem);
//        notifyDataSetChanged();
//    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Slide sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getName());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImage())
                .fitCenter()
                .placeholder(R.drawable.logo)
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SLIDE_NAME",sliderItem.getName().toString());
                if (sliderItem.getName().trim().equals("Daily Needs")){
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra(Constant.CATEGORY_ID,"1");
                    context.startActivity(intent);

                }else if (sliderItem.getName().trim().equals("HealthCare")){
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra(Constant.CATEGORY_ID,"2");
                    context.startActivity(intent);

                }else if (sliderItem.getName().trim().equals("Fintech")){
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra(Constant.CATEGORY_ID,"3");
                    context.startActivity(intent);

                }


            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}