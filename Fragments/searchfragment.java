package com.ali.ssb.Fragments;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.ssb.Models.modelallpro;
import com.ali.ssb.Models.modelcategoryinshop;
import com.ali.ssb.Models.modelslider;
import com.ali.ssb.R;
import com.ali.ssb.holderclasses.adaperslider;
import com.ali.ssb.holderclasses.holderadaptersliderinsearch;
import com.ali.ssb.holderclasses.holderallcategories;
import com.ali.ssb.holderclasses.holderallpro;
import com.ali.ssb.interfacesapi.allcategoryapi;
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


public class searchfragment extends Fragment implements holderallpro.onproclicklistener,adaperslider.onshopclicklistener, AdapterView.OnItemSelectedListener,holderadaptersliderinsearch.onshopclicklistener {

    RecyclerView recshops;
    holderadaptersliderinsearch adapterslider;
    List<modelslider> modelsshop;

    RecyclerView reccat;
    holderallcategories adaptercat;
    List<modelcategoryinshop> listcat;

    ProgressBar bar;

    String filter;
    TextView nores;
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<modelallpro> list;
    String[] country;
    holderallpro adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_searchfragment, container, false);

        country = new String[]{"Filter by", "Products", "Shops", "categories"};
        Spinner spin = view.findViewById(R.id.spin);
        spin.setOnItemSelectedListener(this);

        bar=view.findViewById(R.id.bar);

        bar.setVisibility(View.INVISIBLE);

        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        recshops=view.findViewById(R.id.recshop);
        recshops.setVisibility(View.INVISIBLE);

        recyclerView=view.findViewById(R.id.rec);
        searchView=view.findViewById(R.id.searchview);
        searchView.requestFocus();

        reccat=view.findViewById(R.id.reccat);
//shops
//shopsapi();

//products

//        list = new ArrayList<>();
//        SharedPreferences prefss = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        Retrofit retrofits = new Retrofit.Builder()
//                .baseUrl("http://"+prefss.getString("ipv4","10.0.2.2")+":5000/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        final allproductsapi apis=retrofits.create(allproductsapi.class);
//        Call<List<modelallpro>> listCalls=apis.listCall();
//
//        listCalls.enqueue(new Callback<List<modelallpro>>() {
//            @Override
//            public void onResponse(Call<List<modelallpro>> call, Response<List<modelallpro>> response) {
//                Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                if (response.isSuccessful()){
//                    recyclerView.setVisibility(View.VISIBLE);//when cancel
//                    list.addAll(response.body());
//                    adapter = new holderallpro(list, getContext());
//                    recyclerView.setHasFixedSize(true);
//                    final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
//                    recyclerView.setLayoutManager(manager);
////                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                    adapter.setoncartclicklistener(searchfragment.this);
//                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                        @Override
//                        public boolean onQueryTextSubmit(String query) {
//                            if (adapter!=null){
//                                list=new ArrayList<>();
//                                list.addAll(response.body());
//                                recyclerView.setVisibility(View.VISIBLE);
//                                adapter = new holderallpro(list, getContext());
//                                recyclerView.setHasFixedSize(true);
//                                final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
//                                recyclerView.setLayoutManager(manager);
////                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                                adapter.setoncartclicklistener(searchfragment.this);
//                                adapter.getFilter().filter(query);
//                                recyclerView.setAdapter(adapter);
//                            }
//                            else
//                            {
//                                Toast.makeText(getContext(), "No products found", Toast.LENGTH_SHORT).show();
////                                nores.setVisibility(View.VISIBLE);
////                                adapter = new holderallpro(list, getContext());
////                                recyclerView.setHasFixedSize(true);
////                                final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
////                                recyclerView.setLayoutManager(manager);
////                                recyclerView.setAdapter(adapter);
////                                adapter.notifyDataSetChanged();
////                                adapter.setoncartclicklistener(searchfragment.this);
//
//                            }
//
//                            return false;
//
//                        }
//
//                        @Override
//                        public boolean onQueryTextChange(String newText) {
//                            if (newText.equals("")){
//                                recyclerView.setVisibility(View.INVISIBLE);
////                                list.addAll(response.body());
////                                nores.setVisibility(View.VISIBLE);
////                                adapter = new holderallpro(list, getContext());
////                                recyclerView.setHasFixedSize(true);
////                                final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
////                                recyclerView.setLayoutManager(manager);
////                                recyclerView.setAdapter(adapter);
////                                adapter.notifyDataSetChanged();
////                                adapter.setoncartclicklistener(searchfragment.this);
//                            }
//
//                            return false;
//                        }
//                    });
////                    adapter.notifyDataSetChanged();
////                    adapter.setonshopclicklistener(mainDashboardFragment.this);
////                    load.setVisibility(View.INVISIBLE);
////                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
////                    frag.setAlpha((float) 1.0);
//
//                }
//            }
//            @Override
//            public void onFailure(Call<List<modelallpro>> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
////                shopsapi();
//            }
//        });




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

    public void shopsapi() {

        recyclerView.setVisibility(View.INVISIBLE);
        recshops.setVisibility(View.INVISIBLE);
        reccat.setVisibility(View.INVISIBLE);

        bar.setVisibility(View.VISIBLE);

        modelsshop = new ArrayList<>();
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final shopsapi api=retrofit.create(shopsapi.class);
        Call<List<modelslider>> listCall=api.list();

        listCall.enqueue(new Callback<List<modelslider>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<modelslider>> call, Response<List<modelslider>> response) {
                if (response.isSuccessful()){
                    modelsshop=response.body();

                    adapterslider = new holderadaptersliderinsearch(getContext(),modelsshop);
                    recyclerView.setHasFixedSize(true);
                    final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapterslider);
                    adapterslider.notifyDataSetChanged();
                    bar.setVisibility(View.INVISIBLE);


//                    adapterslider.setonshopclicklistener(searchfragment.this);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            if (adapterslider!=null){

                                modelsshop=new ArrayList<>();
                                modelsshop.addAll(response.body());
                                recyclerView.setVisibility(View.VISIBLE);
                                adapterslider = new holderadaptersliderinsearch(getContext(),modelsshop);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapterslider);
                                final LinearLayoutManager manager=new LinearLayoutManager(getContext());
                                recshops.setLayoutManager(manager);
                                if (recyclerView.getChildCount()<0){
                                    Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
                                }
//                                recyclerView.setAdapter(adapter);
//                                adapterslider.notifyDataSetChanged();
                                adapterslider.setonshopclicklistener(searchfragment.this);
                                adapterslider.getFilter().filter(query);

                                bar.setVisibility(View.INVISIBLE);
//                                recyclerView.setAdapter(adapterslider);
                            }
                            else {
                                Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            if (newText.equals("")){
                                recyclerView.setVisibility(View.INVISIBLE);
//                                list.addAll(response.body());
//                                nores.setVisibility(View.VISIBLE);
//                                adapter = new holderallpro(list, getContext());
//                                recyclerView.setHasFixedSize(true);
//                                final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
//                                recyclerView.setLayoutManager(manager);
//                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                                adapter.setoncartclicklistener(searchfragment.this);
                            }
                            return false;
                        }
                    });
                }
                if (response.code()==500){
                    shopsapi();
                }
            }
            @Override
            public void onFailure(Call<List<modelslider>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                shopsapi();
            }
        });

    }

    public void productsapi(){

        bar.setVisibility(View.VISIBLE);

        recshops.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        reccat.setVisibility(View.INVISIBLE);

        list = new ArrayList<>();
        SharedPreferences prefss = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofits = new Retrofit.Builder()
                .baseUrl("http://"+prefss.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final allproductsapi apis=retrofits.create(allproductsapi.class);
        Call<List<modelallpro>> listCalls=apis.listCall();

        listCalls.enqueue(new Callback<List<modelallpro>>() {
            @Override
            public void onResponse(Call<List<modelallpro>> call, Response<List<modelallpro>> response) {
                Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){
//                    recyclerView.setVisibility(View.VISIBLE);//when cancel
                    list.addAll(response.body());
                    adapter = new holderallpro(list, getContext());
                    recyclerView.setHasFixedSize(true);
                    final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
                    recyclerView.setLayoutManager(manager);
//                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setoncartclicklistener(searchfragment.this);
                    bar.setVisibility(View.INVISIBLE);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            if (adapter!=null){
                                list=new ArrayList<>();
                                list.addAll(response.body());
                                recyclerView.setVisibility(View.VISIBLE);
                                adapter = new holderallpro(list, getContext());
                                recyclerView.setHasFixedSize(true);
                                final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
                                recyclerView.setLayoutManager(manager);
//                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                adapter.setoncartclicklistener(searchfragment.this);
                                adapter.getFilter().filter(query);
                                recyclerView.setAdapter(adapter);

                                bar.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                Toast.makeText(getContext(), "No products found", Toast.LENGTH_SHORT).show();
//                                nores.setVisibility(View.VISIBLE);
//                                adapter = new holderallpro(list, getContext());
//                                recyclerView.setHasFixedSize(true);
//                                final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
//                                recyclerView.setLayoutManager(manager);
//                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                                adapter.setoncartclicklistener(searchfragment.this);

                            }

                            return false;

                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            if (newText.equals("")){
                                recyclerView.setVisibility(View.INVISIBLE);
//                                list.addAll(response.body());
//                                nores.setVisibility(View.VISIBLE);
//                                adapter = new holderallpro(list, getContext());
//                                recyclerView.setHasFixedSize(true);
//                                final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
//                                recyclerView.setLayoutManager(manager);
//                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                                adapter.setoncartclicklistener(searchfragment.this);
                            }
                            return false;
                        }
                    });
//                    adapter.notifyDataSetChanged();
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

    }

    public void allcategory(){

        recshops.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        reccat.setVisibility(View.INVISIBLE);

        bar.setVisibility(View.VISIBLE);

        listcat = new ArrayList<>();
        SharedPreferences prefss = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofits = new Retrofit.Builder()
                .baseUrl("http://"+prefss.getString("ipv4","10.0.2.2")+":5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final allcategoryapi apis=retrofits.create(allcategoryapi.class);
        Call<List<modelcategoryinshop>> listCalls=apis.list();

        listCalls.enqueue(new Callback<List<modelcategoryinshop>>() {
            @Override
            public void onResponse(Call<List<modelcategoryinshop>> call, Response<List<modelcategoryinshop>> response) {
                if (response.isSuccessful()){
//                    recyclerView.setVisibility(View.VISIBLE);//when cancel
                    listcat.addAll(response.body());
                    adaptercat = new holderallcategories(listcat, getContext());
                    recyclerView.setHasFixedSize(true);
                    final LinearLayoutManager manager=new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(manager);
//                    recyclerView.setAdapter(adapter);
                    adaptercat.notifyDataSetChanged();
                    bar.setVisibility(View.INVISIBLE);

//                    adaptercat.setoncartclicklistener(searchfragment.this);
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            if (adaptercat!=null){
                                listcat=new ArrayList<>();
                                listcat.addAll(response.body());
                                recyclerView.setVisibility(View.VISIBLE);
                                adaptercat = new holderallcategories(listcat, getContext());
                                recyclerView.setHasFixedSize(true);
                                final LinearLayoutManager manager=new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(manager);
//                                recyclerView.setAdapter(adapter);
                                adaptercat.notifyDataSetChanged();
                                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();

//                                adaptercat.setoncartclicklistener(searchfragment.this);
                                adaptercat.getFilter().filter(query);

                                recyclerView.setAdapter(adaptercat);

                            }
                            else
                            {
                                Toast.makeText(getContext(), "No products found", Toast.LENGTH_SHORT).show();
//                                nores.setVisibility(View.VISIBLE);
//                                adapter = new holderallpro(list, getContext());
//                                recyclerView.setHasFixedSize(true);
//                                final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
//                                recyclerView.setLayoutManager(manager);
//                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                                adapter.setoncartclicklistener(searchfragment.this);

                            }
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            if (newText.equals("")){
                                recyclerView.setVisibility(View.INVISIBLE);
                            }
                            return false;
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<modelcategoryinshop>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                shopsapi();
            }
        });

    }



    @Override
    public void onshopqclick(String id, String name, String cat, String delcharges, String promorate) {
        shop shop = new shop();
        FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("shopid",id);
        bundle.putString("shopname",name);
        bundle.putString("shopcat",cat);
        bundle.putString("delcharges",delcharges);
        bundle.putString("promorate",promorate);
        shop.setArguments(bundle);
        fragmentTransactionpro.replace(R.id.fragment,shop);
        fragmentTransactionpro.commit();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        filter=country[position];
        if (filter=="Shops"){
            shopsapi();
        }
        else if (filter=="Products")
            productsapi();
        else if (filter=="categories")
            allcategory();
        else {

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}