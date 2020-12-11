package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.Models.modelallpro;
import com.ali.ssb.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class holderallpro extends RecyclerView.Adapter<holderallpro.holder> implements Filterable {

    ArrayList<modelallpro> list;
    ArrayList<modelallpro> filterlistArray;
    onproclicklistener monproclicklistener;
    Context context;

    public holderallpro(ArrayList<modelallpro> list, Context context) {
        this.list = list;
        this.context = context;
        filterlistArray=new ArrayList<>(list);
    }

    public void setoncartclicklistener(onproclicklistener listener){
        monproclicklistener=  listener;
    }
    public interface onproclicklistener{
        void onproclick(String title,String desc,String price,String discounted,String image,String color,String size,String days,String qtyleft,String proid,String delcharges,String shopidd);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productondashboardlayout,parent,false);
        return new holderallpro.holder(itemView);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {


        Boolean promo = false;

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        holder.price.setText("Rs " + list.get(position).getPrice());

        holder.title.setText(list.get(position).getName());
        Picasso.get().load(list.get(position).getImage().replaceFirst("localhost", prefs.getString("ipv4", "10.0.2.2"))).networkPolicy(NetworkPolicy.NO_STORE).into(holder.imageView);
        holder.discount.setText("Rs " + list.get(position).getPromotionRate());
        holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.type.setText(list.get(position).getCategoryType());

        String price = list.get(position).getPrice();
        final String title = list.get(position).getName();
        String discount = list.get(position).getPromotionRate();
        final String desc = list.get(position).getDetail();
        final String image = list.get(position).getImage();
        final String color = list.get(position).getColor();
        final String size = list.get(position).getSize();
        final String qtyleft = list.get(position).getQuantity();
        final String days = list.get(position).getPromotionTill();


        if (list.get(position).getPromotionStatus().equals("accepted")) {
            if (list.get(position).getPromotionTill().isEmpty() || list.get(position).getPromotionTill().equals("N/A") || list.get(position).getPromotionTill().equals("none") || list.get(position).getPromotionTill().equals("")) {

            } else {

                LocalDate currentDate = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    currentDate = LocalDate.now(ZoneId.systemDefault());
                }

                LocalDate getDates = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    if (list.get(position).getPromotionTill().isEmpty() || list.get(position).getPromotionTill().equals("N/A") || list.get(position).getPromotionTill().equals("none") || list.get(position).getPromotionTill().equals("")) {
                    } else {
                        getDates = LocalDate.parse(list.get(position).getPromotionTill());
                    }
                }

                if (currentDate.minusDays(1).isBefore(getDates)) {
                    promo = true;
                }

            }
            if (promo) {
                holder.price.setText("Rs " + list.get(position).getPromotionRate());
                holder.discount.setText("Rs " + list.get(position).getPrice());
                price = list.get(position).getPromotionRate();
                discount = list.get(position).getPrice();
            } else {
                holder.discount.setVisibility(View.INVISIBLE);
                discount = "0";

            }
        } else {

            holder.discount.setVisibility(View.INVISIBLE);
            discount = "0";
        }

        if (list.get(position).getName().length() > 16) {
            holder.title.setText(list.get(position).getName().substring(0, 18).concat("..."));
        }
        final String finalDiscount = discount;
        String finalPrice = price;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monproclicklistener.onproclick(title,desc, finalPrice, finalDiscount,image,color,size,days,qtyleft,list.get(position).get_id(),list.get(position).getUserId().getDeliveryCharges(),list.get(position).getUserId().get_id());
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    private Filter listFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<modelallpro> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0)
            {
                filteredList.addAll(filterlistArray);
            }
            else
            {
                String filterInput=constraint.toString().toLowerCase().trim();
                for(modelallpro listModel:filterlistArray){
                    if(listModel.getName().toLowerCase().contains(filterInput))
                    {
                        filteredList.add(listModel);
                    }
                }        }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }    @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView price,title,discount,type;
        public holder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.type);
            discount = itemView.findViewById(R.id.discountprice);
        }
        }
}
