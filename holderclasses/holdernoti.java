package com.ali.ssb.holderclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.Models.modelnotification;
import com.ali.ssb.R;

import java.util.List;

public class holdernoti extends RecyclerView.Adapter<holdernoti.holder> {

    List<modelnotification> list;
    Context context;

    public holdernoti(List<modelnotification> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notilatout,parent,false);
        return new holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.body.setText(list.get(position).getDetail());
        holder.date.setText(list.get(position).getDate().substring(0,16));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView date,title,body;
        public holder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.title);
            body=itemView.findViewById(R.id.detail);
        }
    }
}
