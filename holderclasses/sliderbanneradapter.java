package com.ali.ssb.holderclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;


import com.ali.ssb.Models.modelbaner;
import com.ali.ssb.Models.modelbanner;
import com.ali.ssb.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class sliderbanneradapter extends SliderViewAdapter<sliderbanneradapter.holder> {

    List<modelbaner> modelbaner;
    Context context;
    onbanerclicklistener monbanerclicklistener;


    public sliderbanneradapter(List<modelbaner> modelbanners, Context context) {
        this.modelbaner = modelbanners;
        this.context = context;
    }
    public void setonbanerclicklistener(onbanerclicklistener listener){
        monbanerclicklistener=  listener;
    }

    public interface onbanerclicklistener{
        void onbanerclick(String id,String name,String cat,String type);
    }


    @Override
    public holder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.bannerlayout, null);
        return new holder(inflate);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        SharedPreferences s=context.getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        Picasso.get().load(modelbaner.get(position).getImage().replaceFirst("localhost",s.getString("ipv4","10.0.2.2"))).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    monbanerclicklistener.onbanerclick(modelbaner.get(position).getShopId().get_id(),"","","");
                Toast.makeText(context, String.valueOf(modelbaner.get(position).getShopId().get_id()), Toast.LENGTH_SHORT).show();}
                catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getCount() {
        return modelbaner.size();
    }

    public class holder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;
        public holder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
        }
    }
}
