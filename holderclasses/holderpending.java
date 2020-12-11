package com.ali.ssb.holderclasses;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelpending;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class holderpending extends RecyclerView.Adapter<holderpending.holder> {
    List<modelpending> list;
    Context context;

    public holderpending(List<modelpending> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutpending,parent,false);
        return new holderpending.holder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.price.setText("Rs. "+list.get(position).getGrandTotal());
        holder.status.setText(list.get(position).getStatus());
        holder.date.setText(list.get(position).getOrderTime());
        holder.id.setText("Order# "+list.get(position).get_id());

        holder.id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ID copied", list.get(position).get_id());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Order ID copied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView status,date,price,id;
        public holder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            status=itemView.findViewById(R.id.status);
            date=itemView.findViewById(R.id.date);
            price=itemView.findViewById(R.id.price);

        }
    }
}
