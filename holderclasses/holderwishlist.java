package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.Models.modelsinglepro;
import com.ali.ssb.R;
import com.ali.ssb.Models.modelwishlist;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.stripe.model.Card;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class holderwishlist extends RecyclerView.Adapter<holderwishlist.holder> {
        List<modelwishlist> list;
        Context context;
        private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
        ondel monclicklistener;
        oncart moncart;

    public holderwishlist(List<modelwishlist> list, Context context) {
        this.list = list;
        this.context = context;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    public interface oncart{
        public void onclicker(String proid);
    }
    public void oncartclick(oncart listener){
        moncart=listener;
    }

    public interface ondel{
        public void onclicker(int id);
    }
    public void onclick(ondel listener){
        monclicklistener=listener;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutwishlist,parent,false);
        return new holderwishlist.holder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull holder holder, final int position) {

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(list.get(position).getId()));

        SharedPreferences prefs =context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        holder.title.setText(list.get(position).getTitle());
        holder.price.setText("Rs "+list.get(position).getPrice());
        holder.discounted.setText("Rs "+list.get(position).getDiscounted());
        holder.color.setText("Color: "+list.get(position).getColor());
        holder.size.setText("Size: "+list.get(position).getSize());
        holder.discounted.setPaintFlags(holder.discounted.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Picasso.get().load(list.get(position).getImage().replaceFirst("localhost",prefs.getString("ipv4","10.0.2.2"))).into(holder.img);

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monclicklistener.onclicker(Integer.valueOf(list.get(position).getId()));
            }
        });
        holder.addtocacrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moncart.onclicker(list.get(position).getProid());
            }
        });
//
//        holder.addtocacrt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                String a=list.get(position).getTitle();
////                String b=list.get(position).getPrice();
////                String c=list.get(position).getDiscounted();
////                String d=list.get(position).getDesc();
////                String e=list.get(position).getId();
////                String f=list.get(position).getImage();
////                String g=list.get(position).getColor();
////                String h=list.get(position).getSize();
////                moncart.onclicker(a,b,c,h,g,f,d,e);
//            }
//        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView title,price,discounted,size,color;
        ImageView img;
        SwipeRevealLayout swipeRevealLayout;
        RelativeLayout del;
        CardView buynow,addtocacrt;

        public holder(@NonNull View itemView) {
            super(itemView);

            swipeRevealLayout=itemView.findViewById(R.id.swipe);
            del=itemView.findViewById(R.id.del);
            img=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            color=itemView.findViewById(R.id.color);
            size=itemView.findViewById(R.id.size);
            discounted=itemView.findViewById(R.id.discount);
            buynow=itemView.findViewById(R.id.buynow);
            addtocacrt=itemView.findViewById(R.id.addtocart);

        }
    }
}
