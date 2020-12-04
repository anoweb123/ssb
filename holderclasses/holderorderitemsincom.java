package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.Models.modelorderitems;
import com.ali.ssb.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class holderorderitemsincom extends RecyclerView.Adapter<holderorderitemsincom.holder> {

    List<modelorderitems> list;
    Context context;

    public holderorderitemsincom(List<modelorderitems> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutorderitems,parent,false);
        return new holderorderitemsincom.holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        SharedPreferences sharedPreferences=context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        holder.title.setText(list.get(position).getProductName());
        holder.color.setText("Color: "+list.get(position).getProductId().getColor());
        holder.size.setText("Size:" +list.get(position).getProductId().getSize());
        holder.quan.setText("Qty: "+list.get(position).getQuantity());
        holder.discount.setText("Rs. "+list.get(position).getProductId().getPrice());
        holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.price.setText("Rs. "+list.get(position).getProductId().getPromotionRate());
        Picasso.get().load(list.get(position).getImage().replaceFirst("localhost",sharedPreferences.getString("ipv4",""))).networkPolicy(NetworkPolicy.NO_STORE).into(holder.img);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        TextView title,quan,price,discount,color,size;
        ImageView img;
        public holder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
            quan=itemView.findViewById(R.id.quan);
            discount=itemView.findViewById(R.id.discountprice);
            price=itemView.findViewById(R.id.price);
            discount=itemView.findViewById(R.id.discountprices);
            color=itemView.findViewById(R.id.color);
            size=itemView.findViewById(R.id.size);
        }
    }
}
