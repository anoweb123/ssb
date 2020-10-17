package com.ali.ssb.holderclasses;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelwishlist;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;
public class holderwishlist extends RecyclerView.Adapter<holderwishlist.holder> {
        List<modelwishlist> list;
        Context context;
        private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
        ondel monclicklistener;

    public holderwishlist(List<modelwishlist> list, Context context) {
        this.list = list;
        this.context = context;
//        viewBinderHelper.setOpenOnlyOne(true);
    }


    public interface ondel{
        public void onclicker(int position);
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

        holder.title.setText(list.get(position).getTitle());
        holder.price.setText("Rs "+list.get(position).getPrice());
        holder.discounted.setText("Rs "+list.get(position).getDiscounted());
        holder.color.setText("Color: "+list.get(position).getColor());
        holder.size.setText("Size: "+list.get(position).getSize());
        holder.discounted.setPaintFlags(holder.discounted.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.img.setImageResource(list.get(position).getImage());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monclicklistener.onclicker(list.get(position).getId());
            }
        });


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

        }
    }
}
