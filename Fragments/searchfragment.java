package com.ali.ssb.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.ali.ssb.Models.modelallpro;
import com.ali.ssb.Models.modelslider;
import com.ali.ssb.R;
import com.ali.ssb.dbhandler;
import com.ali.ssb.holderclasses.adaperslider;
import com.ali.ssb.holderclasses.holderallpro;
import com.ali.ssb.interfacesapi.allproductsapi;
import com.ali.ssb.interfacesapi.shopsapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;


public class searchfragment extends Fragment implements holderallpro.onproclicklistener{

    EditText search;

    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<modelallpro> list;
    holderallpro adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_searchfragment, container, false);


        recyclerView=view.findViewById(R.id.rec);
        searchView=view.findViewById(R.id.searchview);
        searchView.requestFocus();



        list = new ArrayList<>();
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final allproductsapi api=retrofit.create(allproductsapi.class);
        Call<List<modelallpro>> listCall=api.listCall();

        listCall.enqueue(new Callback<List<modelallpro>>() {
            @Override
            public void onResponse(Call<List<modelallpro>> call, Response<List<modelallpro>> response) {
                Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){
                    list.addAll(response.body());
                    adapter = new holderallpro(list, getContext());
                    recyclerView.setHasFixedSize(true);
                    final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setoncartclicklistener(searchfragment.this);
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            if (adapter!=null){
                                adapter.getFilter().filter(newText);
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "work", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });
                    adapter.notifyDataSetChanged();
//                    adapter.setonshopclicklistener(mainDashboardFragment.this);
//                    load.setVisibility(View.INVISIBLE);
//                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                    frag.setAlpha((float) 1.0);

                }
            }
            @Override
            public void onFailure(Call<List<modelallpro>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                shopsapi();
            }
        });




        return view;
    }

    @Override
    public void onproclick(String title, String desc, String price, String discounted, String image, String color, String size, String days, String qtyleft, String proid, String delcharges,String shopid) {
        productfragment productfragment = new productfragment();
        FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("titlekey",title);
        bundle.putString("desckey",desc);
        bundle.putString("pricekey",price);
        bundle.putString("discountedkey",discounted);
        bundle.putString("colorkey",color);
        bundle.putString("sizekey",size);
        bundle.putString("dayskey",days);
        bundle.putString("qtyleftkey",qtyleft);
        bundle.putString("proid",proid);
        bundle.putString("imagekey",image);
        bundle.putString("shopid",shopid);
        bundle.putString("delcharges",delcharges);
        productfragment.setArguments(bundle);
        fragmentTransactionpro.replace(R.id.fragment, productfragment);
        fragmentTransactionpro.commit();
    }
}