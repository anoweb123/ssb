package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelproductbyshop;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class holderproductbyshop extends RecyclerView.Adapter<holderproductbyshop.holder> {
    List<modelproductbyshop> list;
    Context context;
    String disrate;
    onproinshopclicklistener monproclicklistener;

    String finalDiscount,finalprice;


    public holderproductbyshop(List<modelproductbyshop> list, Context context,String disrate) {
        this.list = list;
        this.disrate=disrate;
        this.context = context;
    }

    public void setonproinshopclicklistener(onproinshopclicklistener listener)
    {
        monproclicklistener=  listener;
    }

    public interface onproinshopclicklistener{
       public void onproclick(String proid,String title,String desc,String price,String discounted,String image,String color,String size,String days,String qtyleft);
    }

    @NonNull
    @Override
    public holderproductbyshop.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutproinshop,parent,false);
        return new holderproductbyshop.holder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull holder holder, final int position) {

        String title, desc, price, discount, image, color, size, days, qtyleft,proid;
        Boolean promo=false;

        holder.price.setText("Rs "+list.get(position).getPrice());
        holder.title.setText(list.get(position).getName());
        try{
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String path=list.get(position).getImage().replaceFirst("localhost",prefs.getString("ipv4","10.0.2.2"));
        Picasso.get().load(path).networkPolicy(NetworkPolicy.NO_STORE).into(holder.imageView);
        }
        catch (Exception e){
        }

        price=list.get(position).getPrice();
        title=list.get(position).getName();
        discount=list.get(position).getPromotionRate();
        desc=list.get(position).getDetail();
        image=list.get(position).getImage();
        color=list.get(position).getColor();
        size=list.get(position).getSize();
        qtyleft=list.get(position).getQuantity();
        days=list.get(position).getPromotionTill();
        proid=list.get(position).get_id();
        finalDiscount= discount;
        if (days.isEmpty()||days.equals("none")){}
        else {

            LocalDate currentDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDate = LocalDate.now(ZoneId.systemDefault());
            }
            Toast.makeText(context, String.valueOf(currentDate), Toast.LENGTH_SHORT).show();

            LocalDate getDates=null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                if (list.get(position).getPromotionTill().isEmpty()||list.get(position).getPromotionTill().equals("none")){}
                else {
                    getDates = LocalDate.parse(list.get(position).getPromotionTill());
                    Toast.makeText(context, String.valueOf(getDates), Toast.LENGTH_SHORT).show();
                }
            }

            if (currentDate.isBefore(getDates)){
                promo=true;
                Toast.makeText(context, "yes promo", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "no promo", Toast.LENGTH_SHORT).show();
            }

        }






//        if (disrate.equals("none")){
//        }
//        else {
//            int dis=Integer.valueOf(disrate)/Integer.valueOf(list.get(position).getPrice())*100;
//            discount=String.valueOf(dis);
//        }

        if (disrate.equals("none")||disrate.equals("0")){

            if (discount.equals("none")){
                holder.discount.setText("0");
                holder.discount.setVisibility(View.INVISIBLE);
                discount="0";
            }

            else {
                if (promo){

                holder.price.setText("Rs "+list.get(position).getPromotionRate());
                holder.discount.setText("Rs "+list.get(position).getPrice());
                holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                price=list.get(position).getPromotionRate();
                discount= list.get(position).getPrice();}


            else {
                holder.discount.setText("0");
                holder.discount.setVisibility(View.INVISIBLE);
                discount="0";
            }
            }
        }
        else {

            Float d=(Float.parseFloat(price)-(Float.valueOf(price)/100)*Float.parseFloat(disrate));
            holder.price.setText("Rs "+String.valueOf(d.intValue()));
            holder.discount.setText("Rs "+list.get(position).getPrice());
            holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            discount= list.get(position).getPrice();
            price=String.valueOf(d.intValue());
            //idr
        }

        //        }
//        else {
//            finalDiscount=String.valueOf(Integer.valueOf(disrate)/Integer.valueOf(list.get(position).getPrice())*100);
//            holder.discount.setText("Rs "+list.get(position).getPromotionRate());
//            holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        }




        String finalPrice = price;
        String finalDiscount1 = discount;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              monproclicklistener.onproclick(proid,title,desc, finalPrice, finalDiscount1,image,color,size,days,qtyleft);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{
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
