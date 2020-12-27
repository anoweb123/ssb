package com.ali.ssb.holderclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.Models.modelcategoryinshop;
import com.ali.ssb.Models.modelslider;
import com.ali.ssb.R;

import java.util.ArrayList;
import java.util.List;

public class holderallcategories extends RecyclerView.Adapter<holderallcategories.holder> implements Filterable {

    List<modelcategoryinshop> filterlistArray;
    List<modelcategoryinshop> list;
    Context context;

    public holderallcategories(List<modelcategoryinshop> list, Context context) {
        this.list = list;
        this.context = context;
        filterlistArray=new ArrayList<>(list);
    }

    @NonNull
    @Override
    public holderallcategories.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutallcategories,parent,false);
        return new holderallcategories.holder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull holderallcategories.holder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.date.setText(list.get(position).getPromotionTill());
        holder.discountedrate.setText(list.get(position).getPromotionRate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private Filter listFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<modelcategoryinshop> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0)
            {
                filteredList.addAll(filterlistArray);
            }
            else
            {
                String filterInput=constraint.toString().toLowerCase().trim();
                for(modelcategoryinshop listModelss:filterlistArray){
                    if(listModelss.getName().toLowerCase().contains(filterInput))
                    {
                        filteredList.add(listModelss);
                    }
                }        }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;

        }

        @Override
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

    public class holder extends RecyclerView.ViewHolder{

        TextView name,date,discountedrate;
        public holder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            date=itemView.findViewById(R.id.till);
            discountedrate=itemView.findViewById(R.id.rate);
        }
    }
}
