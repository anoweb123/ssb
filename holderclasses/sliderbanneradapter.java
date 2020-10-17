package com.ali.ssb.holderclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.ssb.Models.modelbanner;
import com.ali.ssb.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class sliderbanneradapter extends SliderViewAdapter<sliderbanneradapter.holder> {

    List<modelbanner> modelbanners;
    Context context;

    public sliderbanneradapter(List<modelbanner> modelbanners, Context context) {
        this.modelbanners = modelbanners;
        this.context = context;
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.bannerlayout, null);
        return new holder(inflate);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        holder.imageView.setImageResource(modelbanners.get(position).getImg());

    }

    @Override
    public int getCount() {
        return modelbanners.size();
    }

    public class holder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;
        public holder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
        }
    }
}
