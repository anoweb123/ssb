package com.ali.ssb.holderclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelcompleted;

import java.util.List;

public class holdercompleted extends RecyclerView.Adapter<holdercompleted.holder>{
    List<modelcompleted> modelcompleteds;
    Context context;

    public holdercompleted(List<modelcompleted> modelcompleteds, Context context) {
        this.modelcompleteds = modelcompleteds;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutcompletedorder,parent,false);
        return new holdercompleted.holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.price.setText(modelcompleteds.get(position).getPrice());
        holder.status.setText(modelcompleteds.get(position).getStatus());
        holder.date.setText(modelcompleteds.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return modelcompleteds.size();
    }
    public class holder extends RecyclerView.ViewHolder{
        TextView status,date,price;
        public holder(@NonNull View itemView) {
            super(itemView);

            status=itemView.findViewById(R.id.status);
            price=itemView.findViewById(R.id.price);
            date=itemView.findViewById(R.id.date);

        }
    }
}
