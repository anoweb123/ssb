package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.Models.modelorderitems;
import com.ali.ssb.R;
import com.ali.ssb.Models.modelcompleted;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class holdercompleted extends RecyclerView.Adapter<holdercompleted.holder>{
    List<modelcompleted> modelcompleteds;
    Context context;
    List<modelorderitems> list;

    onreordersclicklistener monre;

    onitemsclicklistener monitemsclicklistener;

    public holdercompleted(List<modelcompleted> modelcompleteds, Context context) {
        this.modelcompleteds = modelcompleteds;
        this.context = context;
    }

    public void onitemsclicklistener(onitemsclicklistener listener){
        monitemsclicklistener=  listener;
    }
    public interface onitemsclicklistener{
        void onshowitems(String id);
    }


    public void onreordersclicklistener(onreordersclicklistener listener){
        monre=  listener;
    }
    public interface onreordersclicklistener{
        void onreorderitems(String id);
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

        holder.price.setText("Amount Paid: Rs. "+modelcompleteds.get(position).getGrandTotal());
        holder.status.setText("Status: "+modelcompleteds.get(position).getStatus());
        holder.date.setText("Ordered at: "+modelcompleteds.get(position).getOrderTime());
        holder.deldate.setText("Recieved at: "+modelcompleteds.get(position).getDeliveryTime());
        holder.id.setText("Order#: "+modelcompleteds.get(position).get_id());
        holder.name.setText("Customer: "+modelcompleteds.get(position).getName());
        holder.address.setText("Addresss: "+modelcompleteds.get(position).getAddress());

        holder.chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitemsclicklistener.onshowitems(modelcompleteds.get(position).get_id());
            }
        });

        holder.reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs=context.getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
                String Orderid=modelcompleteds.get(position).get_id();

                monre.onreorderitems(Orderid);

            }
        });

    }
    @Override
    public int getItemCount() {
        return modelcompleteds.size();
    }
    public class holder extends RecyclerView.ViewHolder{
        TextView status,date,deldate,price,id,name,address;
        Chip chip;
        ExtendedFloatingActionButton reorder;
        public holder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            id=itemView.findViewById(R.id.orderid);
            address=itemView.findViewById(R.id.address);
            reorder=itemView.findViewById(R.id.reorder);
            chip=itemView.findViewById(R.id.showitems);
            status=itemView.findViewById(R.id.status);
            date=itemView.findViewById(R.id.orderdate);
            deldate=itemView.findViewById(R.id.deldate);
            price=itemView.findViewById(R.id.amount);

        }
    }
}
