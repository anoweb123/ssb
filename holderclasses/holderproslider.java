package com.ali.ssb.holderclasses;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelproductslider;

import java.util.List;

public class holderproslider extends RecyclerView.Adapter<holderproslider.holder> {

    List<modelproductslider> list;
    Context context;
    onproclicklistener monproclicklistener;


    public holderproslider(List<modelproductslider> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setoncartclicklistener(onproclicklistener listener){
        monproclicklistener=  listener;
    }
    public interface onproclicklistener{
        void onproclickinslide(String title,String desc,String price,String discounted,int image,String color,String size,String days,String qtyleft);
    }
    @NonNull
    @Override
    public holderproslider.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productsliderlayout,parent,false);
        return new holderproslider.holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holderproslider.holder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.price.setText("Rs "+list.get(position).getPrice());
        holder.discount.setText("Rs "+list.get(position).getDiscounted());
        holder.img.setImageResource(list.get(position).getImg());
        holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (list.get(position).getTitle().length()>16){
            holder.title.setText(list.get(position).getTitle().substring(0,16).concat("..."));
        }


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                monproclicklistener.onproclickinslide(list.get(position).getTitle(),"This is good product",list.get(position).getPrice(),list.get(position).getDiscounted(),list.get(position).getImg(),"yellow","Small","5","4");
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public class holder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title,price,discount;
        public holder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            discount=itemView.findViewById(R.id.discountprice);
        }
    }
}
