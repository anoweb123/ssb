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
import com.ali.ssb.Models.modelcateg;

import java.util.List;

public class holdercategory extends RecyclerView.Adapter<holdercategory.holder> {
    List<modelcateg> list;
    Context context;

    public holdercategory(List<modelcateg> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorieslayout,parent,false);
        return new holdercategory.holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.img.setBackgroundResource(list.get(position).getIamge());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView img;
        public holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.title);
            img=itemView.findViewById(R.id.img);
        }
    }
}
