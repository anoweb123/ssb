package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelcategoryinshop;
import com.google.android.material.chip.Chip;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

public class holdercategoryinshop extends RecyclerView.Adapter<holdercategoryinshop.holder> {


    String[] color=new String[]{"#2d555e","#dc3203","#28bd90","#4ac1e8","#2bf728","#dc0303","#f5e93d","#1c8bb0","#32b898","#bc15cf","#ae6af7","#11edb8","#368f33","#34bfed","#6495a1","#52caf2","#8642cf","#08799e"};

    List<modelcategoryinshop> list;
    Context context;
    String shoppromo;
    oncatinshopclicklistener moncatinshopclicklistener;

    public holdercategoryinshop(List<modelcategoryinshop> list, Context context,String shoppromo) {
        this.list = list;
        this.context = context;
        this.shoppromo = shoppromo;

    }

    public void setonshopclicklistener(oncatinshopclicklistener listener){
        moncatinshopclicklistener=  listener;
    }

    public interface oncatinshopclicklistener{
        void onshopqclick(String id,String name,String offrate);
    }
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categoryinshoplayout,parent,false);
        return new holder(itemView);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final holder holder, final int position) {
        String catoffrate;
        holder.chip.setText(list.get(position).getName());
        holder.rate.setText(list.get(position).getPromotionRate()+"%");
        catoffrate=list.get(position).getPromotionRate();


        String promos = "0";

        String prostatus=list.get(position).getPromotionStatus();

        if (list.get(position).getPromotionTill().isEmpty()||list.get(position).getPromotionTill().equals("")||list.get(position).getPromotionTill().equals("none")||list.get(position).getPromotionTill().equals("N/A")){}
        else {

            LocalDate currentDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDate = LocalDate.now(ZoneId.systemDefault());
            }

            LocalDate getDates = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                if (list.get(position).getPromotionTill().isEmpty() || list.get(position).getPromotionTill().equals("none")) {
                } else {
                    getDates = LocalDate.parse(list.get(position).getPromotionTill());
                }
            }

        if (currentDate.isBefore(getDates)){
            promos="true";
        }

        else {
            promos="false";
        }
        }

        if (shoppromo.equals("0")||shoppromo.equals("none")) {

            if (promos.equals("true")) {
                if (list.get(position).getPromotionRate().equals("none") || list.get(position).getPromotionRate().equals("N/A")) {
                    catoffrate = "0";
                    holder.linearLayout.setVisibility(View.INVISIBLE);
                }
            } else {
                catoffrate = "0";
                holder.linearLayout.setVisibility(View.INVISIBLE);
            }

            if (position == 0) {
                moncatinshopclicklistener.onshopqclick(list.get(0).get_id(), list.get(0).getName(), catoffrate);
            }
        }
        else {
            holder.linearLayout.setVisibility(View.INVISIBLE);

            if (position==0){
                moncatinshopclicklistener.onshopqclick(list.get(0).get_id(),list.get(0).getName(),shoppromo);
            }
        }

        Random rnd = new Random();
        int ran= rnd.nextInt(17);
        holder.chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor(color[ran])));
        String finalCatoffrate = catoffrate;
        holder.chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moncatinshopclicklistener.onshopqclick(list.get(position).get_id(),list.get(position).getName(), finalCatoffrate);
            }
        });
//        holder.itemView.setSelected(pos==position);
//        holder.chip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                notifyDataSetChanged();
//                pos = position;
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        Chip chip;
        LinearLayout linearLayout;
        TextView rate;
        public holder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linear);
            chip=itemView.findViewById(R.id.chip);
            rate=itemView.findViewById(R.id.rate);
        }
    }
}
