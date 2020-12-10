package com.ali.ssb.holderclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelcolor;

import java.util.List;

public class holderforcolor extends RecyclerView.Adapter<holderforcolor.holder> {
    List<modelcolor> modelcolors;
    Context context;

    public holderforcolor(List<modelcolor> modelcolors, Context context) {
        this.modelcolors = modelcolors;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutcolor,parent,false);
        return new holderforcolor.holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.chip.setText(modelcolors.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return modelcolors.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        Button chip;
        public holder(@NonNull View itemView) {
            super(itemView);
            chip=itemView.findViewById(R.id.chip);
        }
    }
}
