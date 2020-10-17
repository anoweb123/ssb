package com.ali.ssb.holderclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelhistory;

import java.util.List;

public class holderhistory extends RecyclerView.Adapter<holderhistory.holder> {
    List<modelhistory> list;
    Context context;
    public holderhistory(List<modelhistory> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holderhistory.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layouthistory,parent,false);
        return new holderhistory.holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holderhistory.holder holder, int position) {
        holder.price.setText(list.get(position).getPrice());
        holder.date.setText(list.get(position).getDate());
        holder.status.setText(list.get(position).getStatus());
    }
    @Override
    public int getItemCount() {
        return list.size();
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
