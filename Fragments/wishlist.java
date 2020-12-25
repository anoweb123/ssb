package com.ali.ssb.Fragments;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.ssb.Models.modelsinglepro;
import com.ali.ssb.Models.modelwishlist;
import com.ali.ssb.R;
import com.ali.ssb.dbhandler;
import com.ali.ssb.holderclasses.holderwishlist;
import com.ali.ssb.interfacesapi.singleforwishapi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link wishlist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class wishlist extends Fragment implements holderwishlist.ondel,holderwishlist.oncart{

    RecyclerView recyclerView;
    List<modelwishlist> list;

    holderwishlist adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public wishlist() {
    }
    public static wishlist newInstance(String param1, String param2) {
        wishlist fragment = new wishlist();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        recyclerView=view.findViewById(R.id.rec);

        dbhandler dbhandler=new dbhandler(getContext());
        list=dbhandler.retrievewishlist();
        Toast.makeText(getContext(), String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
        dbhandler.close();

        recyclerView.hasFixedSize();
        adapter=new holderwishlist(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.onclick(this);
        adapter.oncartclick(this);
        adapter.notifyDataSetChanged();


        return view;
    }

    @Override
    public void onclicker(int id) {
        dbhandler dbhandler=new dbhandler(getContext());
        dbhandler.deleteinwish(id);
        list=dbhandler.retrievewishlist();
        dbhandler.close();
        recyclerView.hasFixedSize();

        adapter=new holderwishlist(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.onclick(this);
        adapter.oncartclick(this);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onclicker(String proid) {
            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/products/product/"+proid+"/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                singleforwishapi api = retrofit.create(singleforwishapi.class);
                Call<modelsinglepro> listCall = api.list();

                listCall.enqueue(new Callback<modelsinglepro>() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<modelsinglepro> call, Response<modelsinglepro> response) {
                        if (response.isSuccessful()){

                            dbhandler dbhandler=new dbhandler(getContext());
                            if (response.body().getPromotionStatus().equals("accepted")){
                                LocalDate currentDate = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    currentDate = LocalDate.now(ZoneId.systemDefault());
                                }

                                LocalDate getDates = LocalDate.parse(response.body().getPromotionTill());

                                if (currentDate.minusDays(1).isBefore(getDates)){
                                    if (response.body().getPromotionRate().equals("none")||response.body().getPromotionRate().equals("0")){
                                        dbhandler.addtocart(proid,response.body().getName(),response.body().getImage(),response.body().getDetail(),response.body().getPromotionRate(),"0",response.body().getColor(),response.body().getSize(),"1",Integer.valueOf(response.body().getQuantity()));
                                    }
                                    dbhandler.addtocart(proid,response.body().getName(),response.body().getImage(),response.body().getDetail(),response.body().getPromotionRate(),response.body().getPrice(),response.body().getColor(),response.body().getSize(),"1",Integer.valueOf(response.body().getQuantity()));
                                }
                                else {
                                    dbhandler.addtocart(proid,response.body().getName(),response.body().getImage(),response.body().getDetail(),response.body().getPrice(),"0",response.body().getColor(),response.body().getSize(),"1",Integer.valueOf(response.body().getQuantity()));
                                }
                            }
                            else {
                            dbhandler.addtocart(proid,response.body().getName(),response.body().getImage(),response.body().getDetail(),response.body().getPrice(),"0",response.body().getColor(),response.body().getSize(),"1",Integer.valueOf(response.body().getQuantity()));}
                            dbhandler.close();

                            Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), response.body().getPromotionRate(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<modelsinglepro> call, Throwable t) {
                    }
                });

    }
}