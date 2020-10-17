package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.Models.modelslider;
import com.ali.ssb.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class adaperslider extends RecyclerView.Adapter<adaperslider.holder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<modelslider> models;
    onshopclicklistener monshopclicklistener;



    public adaperslider(List<modelslider> models, Context context) {
        this.models =models;
        this.context = context;
    }

    public void setonshopclicklistener(onshopclicklistener listener){
        monshopclicklistener=  listener;
    }

    public interface onshopclicklistener{
        void onshopqclick(String id,String name,String cat);
    }


    @NonNull
    @Override
    public adaperslider.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sliderlayout,parent,false);
        return new holder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final adaperslider.holder holder, final int position) {
//models.get(position).get_id();
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, models.get(position).get_id().toString(), Toast.LENGTH_SHORT).show();
                monshopclicklistener.onshopqclick(String.valueOf(models.get(position).get_id()),models.get(position).getShopName(),models.get(position).getShopCategory());
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
        public holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.name);
            cat=itemView.findViewById(R.id.cat);
        }
    }
}
