package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.Models.modelallpro;
import com.ali.ssb.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class holderallpro extends RecyclerView.Adapter<holderallpro.holder> implements Filterable {

    ArrayList<modelallpro> list;
    ArrayList<modelallpro> filterlistArray;
    Context context;

    public holderallpro(ArrayList<modelallpro> list, Context context) {
        this.list = list;
        this.context = context;
        filterlistArray=new ArrayList<>(list);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productondashboardlayout,parent,false);
        return new holderallpro.holder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        holder.price.setText("Rs "+list.get(position).getPrice());
        holder.title.setText(list.get(position).getName());
        Picasso.get().load(list.get(position).getImage().replaceFirst("localhost",prefs.getString("ipv4","10.0.2.2"))).networkPolicy(NetworkPolicy.NO_STORE).into(holder.imageView);
        holder.discount.setText("Rs "+list.get(position).getPromotionRate());
        holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
        final String price=list.get(position).getPrice();
        final String title=list.get(position).getName();
        String discount=list.get(position).getPromotionRate();
        final String desc=list.get(position).getDetail();
        final String image=list.get(position).getImage();
        final String color=list.get(position).getColor();
        final String size=list.get(position).getSize();
        final String qtyleft=list.get(position).getQuantity();
        final String days=list.get(position).getPromotionTill();
//
        if (discount.equals("none") || discount.equals("0")){
            holder.discount.setVisibility(View.INVISIBLE);
        }
        if (list.get(position).getName().length()>16){
            holder.title.setText(list.get(position).getName().substring(0,18).concat("..."));
        }
//        final String finalDiscount = discount;
//        holder.itemView.setOnClickListener(v ->
//                monproclicklistener.onproclick(title,desc,price, finalDiscount,image,color,size,days,qtyleft));

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
        TextView price,title,discount;
        public holder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.title);
            discount = itemView.findViewById(R.id.discountprice);
        }
        }
}
