package com.ali.ssb.holderclasses;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.ssb.R;
import com.ali.ssb.Models.modelcart;
import com.ali.ssb.dbhandler;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class holdercart extends RecyclerView.Adapter<holdercart.holder>
{
    List<modelcart> modelcarts;
    Context context;
    ondel monclicklistener;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public holdercart(List<modelcart> modelcarts, Context context) {
        this.modelcarts = modelcarts;
        this.context = context;
        viewBinderHelper.setOpenOnlyOne(true);
    }
    public interface ondel{
        public void onclicker(int position);
    }
    public void onclick(ondel listener){
        monclicklistener=listener;
    }

    @NonNull
    @Override
    public holdercart.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartlayout,parent,false);
        return new holdercart.holder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final holdercart.holder holder, final int position) {

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(modelcarts.get(position).getId()));
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, modelcarts.get(position).getImage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Picasso.get().load(modelcarts.get(position).getImage().replaceFirst("localhost",prefs.getString("ipv4","10.0.2.2"))).networkPolicy(NetworkPolicy.NO_STORE).into(holder.imageView);
        holder.title.setText(modelcarts.get(position).getTitle());
        holder.price.setText("Rs "+modelcarts.get(position).getPrice());
        if (modelcarts.get(position).getDiscounted().equals("0")){
            holder.discount.setVisibility(View.INVISIBLE);
        }
        else {
        holder.discount.setText("Rs "+modelcarts.get(position).getDiscounted());
        }
        holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.color.setText("Color: "+modelcarts.get(position).getColor());
        holder.size.setText("Size: "+modelcarts.get(position).getSize());
        holder.desc.setText(modelcarts.get(position).getDesc());
        holder.qty.setText(modelcarts.get(position).getQuantity());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhandler dbhandler1=new dbhandler(context);
                dbhandler1.deleteincart(modelcarts.get(position).getId());
                monclicklistener.onclicker(position);
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhandler dbhandler=new dbhandler(context);
                int quant=Integer.parseInt(holder.qty.getText().toString());
                if (quant<modelcarts.get(position).getLeftstock()){
                dbhandler.updateqty(String.valueOf(modelcarts.get(position).getId()),String.valueOf(quant+1));
                holder.qty.setText(String.valueOf(dbhandler.getqty(String.valueOf(modelcarts.get(position).getId()))));
                dbhandler.close();
                monclicklistener.onclicker(-2);
                }
                else {
                    Toast.makeText(context, "NO MORE AVAILABLE", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(holder.qty.getText().toString())==1){
                    dbhandler dbhandler1=new dbhandler(context);
                    dbhandler1.deleteincart(modelcarts.get(position).getId());
                    monclicklistener.onclicker(position);
                }
                else {
                dbhandler dbhandler=new dbhandler(context);
                int quant=Integer.valueOf(holder.qty.getText().toString());
                dbhandler.updateqty(String.valueOf(modelcarts.get(position).getId()),String.valueOf(quant-1));
                holder.qty.setText(String.valueOf(dbhandler.getqty(String.valueOf(modelcarts.get(position).getId()))));
                dbhandler.close();
                monclicklistener.onclicker(-2);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return modelcarts.size();
    }
    public class holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,price,discount,color,size,desc,qty;
        ImageView minus,add;
        RelativeLayout del;
        SwipeRevealLayout swipeRevealLayout;

        public holder(@NonNull View itemView) {
            super(itemView);

            del=itemView.findViewById(R.id.del);
            imageView=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
            swipeRevealLayout=itemView.findViewById(R.id.swipe);
            price=itemView.findViewById(R.id.price);
            discount=itemView.findViewById(R.id.discountprice);
            color=itemView.findViewById(R.id.color);
            size=itemView.findViewById(R.id.size);
            desc=itemView.findViewById(R.id.desc);
            qty=itemView.findViewById(R.id.quan);
            add=itemView.findViewById(R.id.add);
            minus=itemView.findViewById(R.id.minus);
        }
    }
}
