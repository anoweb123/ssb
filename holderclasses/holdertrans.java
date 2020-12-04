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
        holder.name.setText("Paid by: "+modeltrans.get(position).getName());
        holder.discount.setText("Discount: Rs."+modeltrans.get(position).getDiscount());
        holder.shipping.setText("Shipping fee: Rs."+modeltrans.get(position).getShipping());
        holder.price.setText("Total Amount: Rs."+modeltrans.get(position).getTotal());
        holder.trantype.setText("Payment Method: "+modeltrans.get(position).getPaymentMethod());
        holder.status.setText("Order Status: "+modeltrans.get(position).getStatus());
        holder.idd.setText("Order ID: "+modeltrans.get(position).get_id());
    }

    @Override
    public int getItemCount() {
        return modeltrans.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        TextView name,price,shipping,discount,idd,trantype,status;
        public holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            idd=itemView.findViewById(R.id.idd);
            shipping=itemView.findViewById(R.id.shipping);
            discount=itemView.findViewById(R.id.discount);
            price=itemView.findViewById(R.id.price);
            status=itemView.findViewById(R.id.status);
            trantype=itemView.findViewById(R.id.type);
        }
    }
}
