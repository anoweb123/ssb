package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelproducts;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class holderclassproducts extends RecyclerView.Adapter<holderclassproducts.holder> {
    List<modelproducts> modelproducts;
    onproclicklistener monproclicklistener;
    Context context;
    public holderclassproducts(List<modelproducts> modelproducts, Context context) {
        this.context=context;
        this.modelproducts=modelproducts;
    }
    public void setoncartclicklistener(onproclicklistener listener){
        monproclicklistener=  listener;
    }
    public interface onproclicklistener{
        void onproclick(String title,String desc,String price,String discounted,String image,String color,String size,String days,String qtyleft);
    }
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productondashboardlayout,parent,false);
        return new holderclassproducts.holder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        holder.price.setText("Rs "+modelproducts.get(position).getPrice());
        holder.title.setText(modelproducts.get(position).getName());
        Picasso.get().load(modelproducts.get(position).getImage().replaceFirst("localhost",prefs.getString("ipv4","10.0.2.2"))).networkPolicy(NetworkPolicy.NO_STORE).into(holder.imageView);
        holder.discount.setText("Rs "+modelproducts.get(position).getPromotionRate());
        holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        final String price=modelproducts.get(position).getPrice();
        final String title=modelproducts.get(position).getName();
        String discount=modelproducts.get(position).getPromotionRate();
        final String desc=modelproducts.get(position).getDetail();
        final String image=modelproducts.get(position).getImage();
        final String color=modelproducts.get(position).getColor();
        final String size=modelproducts.get(position).getSize();
        final String qtyleft=modelproducts.get(position).getQuantity();
        final String days=modelproducts.get(position).getPromotionTill();

        if (discount.equals("none") || discount.equals("0")){
            holder.discount.setVisibility(View.INVISIBLE);
        }
        if (modelproducts.get(position).getName().length()>14){
            holder.title.setText(modelproducts.get(position).getName().substring(0,18).concat("..."));
        }
        final String finalDiscount = discount;
        holder.itemView.setOnClickListener(v ->
                monproclicklistener.onproclick(title,desc,price, finalDiscount,image,color,size,days,qtyleft));
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public class holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView price,title,discount;
        public holder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.img);
            price=itemView.findViewById(R.id.price);
            title=itemView.findViewById(R.id.title);
            discount=itemView.findViewById(R.id.discountprice);
        }
    }
}
