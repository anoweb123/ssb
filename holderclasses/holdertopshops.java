package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.Models.modelslider;
import com.ali.ssb.R;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class holdertopshops extends RecyclerView.Adapter<holdertopshops.holder> {

    List<modelslider> models;
    ontopshopclicklistener monshopclicklistener;
    Context context;

    public holdertopshops(List<modelslider> models, Context context) {
        this.models = models;
        this.context = context;
    }


    public void setontopshopclicklistener(ontopshopclicklistener listener){
        monshopclicklistener=  listener;
    }

    public interface ontopshopclicklistener{
        void ontopshopclick(String id,String name,String cat,String delcharges,String promorate);
    }

    @NonNull
    @Override
    public holdertopshops.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layouttopshops,parent,false);
        return new holdertopshops.holder(itemView);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull holdertopshops.holder holder, int position) {
        final String delcharge;
        String delcharge1;
        String promorate = null;

        Boolean promos= false;


        try {
            SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String path=models.get(position).getImage().replaceFirst("localhost",prefs.getString("ipv4","10.0.2.2"));
            Picasso.get().load(path).networkPolicy(NetworkPolicy.NO_STORE).into(holder.imageView);
        }
        catch (Exception e){
        }
        holder.title.setText(models.get(position).getShopName());
        holder.cat.setText(models.get(position).getShopCategory());

        if (models.get(position).getShopName().length()>16){
            holder.title.setText(models.get(position).getShopName().substring(0,15).concat("..."));
        }
        if (models.get(position).getShopCategory().length()>20){
            holder.cat.setText(models.get(position).getShopCategory().substring(0,20).concat("..."));

        }

        String prostatus=models.get(position).getPromotionStatus();

        if (models.get(position).getPromotionStatus().equals("accepted")){
        if (models.get(position).getPromotionTill().isEmpty()||models.get(position).getPromotionTill().equals("")||models.get(position).getPromotionTill().equals("none")||models.get(position).getPromotionTill().equals("N/A")){}
        else {

            LocalDate currentDate = null;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                currentDate = LocalDate.now(ZoneId.systemDefault());
            }

            LocalDate getDates = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                if (models.get(position).getPromotionTill().isEmpty() || models.get(position).getPromotionTill().equals("none")) {
                } else {
                    getDates = LocalDate.parse(models.get(position).getPromotionTill());
                }
            }

            if (currentDate.minusDays(1).isBefore(getDates)){
                if (prostatus.equals("accepted")){
                    promos=true;
                }
                else {
                    promos=false;
                }
            }
            else {
                promos=false;
            }
        }
            }
        else {
            promorate="0";
            holder.promo.setVisibility(View.INVISIBLE);
        }

        if (promos){

            promorate=models.get(position).getPromotionRate();
            if (promorate.equals("none")||promorate.equals("N/A")||promorate.equals(null)||promorate.equals("")){
                promorate="0";
                holder.promo.setVisibility(View.INVISIBLE);
            }
            else{
                holder.promo.setText("Flat "+promorate.concat("%")+" off");
            }


        }
        else {
            promorate="0";
            holder.promo.setVisibility(View.INVISIBLE);
        }

        delcharge1 =models.get(position).getDeliveryCharges();
        if (models.get(position).getDeliveryCharges().equals("N/A")){
            delcharge1 ="0";
        }

        if (promorate.equals("0")){}
        else {
            holder.promo.setVisibility(View.VISIBLE);
        }

        delcharge = delcharge1;
        String finalPromorate = promorate;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monshopclicklistener.ontopshopclick(String.valueOf(models.get(position).get_id()),models.get(position).getShopName(),models.get(position).getShopCategory(),delcharge, finalPromorate);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    public class holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,cat;
        Chip promo;
        public holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.name);
            promo=itemView.findViewById(R.id.promo);
            cat=itemView.findViewById(R.id.cat);
        }
    }
}
