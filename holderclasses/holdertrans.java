package com.ali.ssb.holderclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modeltran;

import java.util.List;

public class holdertrans extends RecyclerView.Adapter<holdertrans.holder> {
    List<modeltran> modeltrans;
    Context context;

    public holdertrans(List<modeltran> modeltrans, Context context) {
        this.modeltrans = modeltrans;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transactionhislayout,parent,false);
        return new holdertrans.holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.time.setText(modeltrans.get(position).getTime());
        holder.date.setText(modeltrans.get(position).getDate());
        holder.name.setText(modeltrans.get(position).getName());
        holder.price.setText(modeltrans.get(position).getPrice());
        holder.type.setText(modeltrans.get(position).getType());

    }

    @Override
    public int getItemCount() {
        return modeltrans.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        TextView name,price,time,date,type;
        public holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
            date=itemView.findViewById(R.id.date);
            price=itemView.findViewById(R.id.price);
            type=itemView.findViewById(R.id.type);
        }
    }
}
