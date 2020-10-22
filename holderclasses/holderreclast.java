package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modellastrec;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

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
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Picasso.get().load(modellastrecs.get(position).getImage().replaceFirst("localhost",prefs.getString("ipv4","10.0.2.2"))).into(holder.img);

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
