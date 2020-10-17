package com.ali.ssb.holderclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modellastrec;

import java.util.List;

public class holderreclast extends RecyclerView.Adapter<holderreclast.holder> {
    List<modellastrec> modellastrecs;
    Context context;

    public holderreclast(List<modellastrec> modellastrecs, Context context) {
        this.modellastrecs = modellastrecs;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sumreclayout,parent,false);
        return new holderreclast.holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.img.setImageResource(modellastrecs.get(position).getImage());
        holder.price.setText("Rs. "+modellastrecs.get(position).getPrice());
        holder.qty.setText("Qty:"+modellastrecs.get(position).getQty());
    }

    @Override
    public int getItemCount() {
        return modellastrecs.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView price,qty;
        public holder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.pic);
            qty=itemView.findViewById(R.id.qty);
            price=itemView.findViewById(R.id.price);
        }
    }
}
