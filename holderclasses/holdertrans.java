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
        holder.name.setText("Customer: "+modeltrans.get(position).getName());
        holder.orderid.setText("Order Id: "+modeltrans.get(position).get_id());
        holder.amount.setText("Amount: Rs "+modeltrans.get(position).getGrandTotal());
        holder.status.setText("Payment Status: "+modeltrans.get(position).getPaymentStatus());
        holder.method.setText("Payment Method: "+modeltrans.get(position).getPaymentMethod());


    }

    @Override
    public int getItemCount() {
        return modeltrans.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        TextView name,orderid,status,amount,method;
        public holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            method=itemView.findViewById(R.id.method);
            status=itemView.findViewById(R.id.status);
            amount=itemView.findViewById(R.id.amount);
            orderid=itemView.findViewById(R.id.orderid);
//            type=itemView.findViewById(R.id.type);
        }
    }
}
