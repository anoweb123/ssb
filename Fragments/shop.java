package com.ali.ssb.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.ssb.R;
import com.ali.ssb.holderclasses.holdercategoryinshop;
import com.ali.ssb.holderclasses.holderproductbyshop;
import com.ali.ssb.interfacesapi.categoryinshopapi;
import com.ali.ssb.interfacesapi.productsbycatinshopapi;
import com.ali.ssb.Models.modelcategoryinshop;
import com.ali.ssb.Models.modelproductbyshop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link shop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class shop extends Fragment implements holderproductbyshop.onproinshopclicklistener,holdercategoryinshop.oncatinshopclicklistener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    holderproductbyshop adapterproduct;
    ImageView back;
    String idd,namee,catt,delcharges,promorate;
    TextView catname,cat,name;
    holdercategoryinshop adaptershop;
    List<modelproductbyshop> modelpro;
    List<modelcategoryinshop> modellist;
    RecyclerView recyclercat;


    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public shop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment shop.
     */
    // TODO: Rename and change types and number of parameters
    public static shop newInstance(String param1, String param2) {
        shop fragment = new shop();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        recyclerView = view.findViewById(R.id.recshop);
        recyclercat = view.findViewById(R.id.reccatinshop);
        catname = view.findViewById(R.id.catname);

        back = view.findViewById(R.id.back);
        name = view.findViewById(R.id.name);
        cat = view.findViewById(R.id.cat);
        modelpro = new ArrayList<>();
        modellist = new ArrayList<>();

        idd=getArguments().getString("shopid");
        namee=getArguments().getString("shopname");
        catt=getArguments().getString("shopcat");
        delcharges=getArguments().getString("delcharges");
        promorate=getArguments().getString("promorate");

        cat.setText(catt);
        name.setText(namee);

//        Retrofit retrofitpro = new Retrofit.Builder()
//                .baseUrl("http://192.168.43.19:5000/products/allproducts/"+idd+"/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        productsbyshop apipro = retrofitpro.create(productsbyshop.class);
//        Call<List<modelproductbyshop>> listCallpro = apipro.list();


//        modelpro.add(new modelproducts("Brochur","1000",R.drawable.shirt,"2000"));
//        modelpro.add(new modelproducts("loren ipsum","1000",R.drawable.jacket,"2000"));
//        modelpro.add(new modelproducts("Brochur","1000",R.drawable.shirt,"2000"));
//        modelpro.add(new modelproducts("loren ipsum","1000",R.drawable.jacket,"2000"));
//        modelpro.add(new modelproducts("Brochur","1000",R.drawable.shirt,"2000"));
//        modelpro.add(new modelproducts("loren ipsum","1000",R.drawable.jacket,"2000"));
//        modelpro.add(new modelproducts("Brochur","1000",R.drawable.shirt,"2000"));
//        modelpro.add(new modelproducts("loren ipsum","1000",R.drawable.jacket,"2000"));
//        modelpro.add(new modelproducts("Brochur","1000",R.drawable.shirt,"2000"));
//        modelpro.add(new modelproducts("loren ipsum","1000",R.drawable.jacket,"2000"));

//        listCallpro.enqueue(new Callback<List<modelproductbyshop>>() {
//            @Override
//            public void onResponse(Call<List<modelproductbyshop>> call, Response<List<modelproductbyshop>> response) {
//                modelpro = response.body();
//                adapterproduct = new holderproductbyshop(modelpro, getContext());
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//                recyclerView.setAdapter(adapterproduct);
//                adapterproduct.notifyDataSetChanged();
//                adapterproduct.setonproinshopclicklistener(shop.this);
//            }
//            @Override
//            public void onFailure(Call<List<modelproductbyshop>> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });


        SharedPreferences prefss = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        Retrofit retrofitcat = new Retrofit.Builder()
                .baseUrl("http://"+prefss.getString("ipv4","10.0.2.2")+":5000/categories/"+idd+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        categoryinshopapi apicat = retrofitcat.create(categoryinshopapi.class);
        Call<List<modelcategoryinshop>> listCall = apicat.list();


        listCall.enqueue(new Callback<List<modelcategoryinshop>>() {
            @Override
            public void onResponse(Call<List<modelcategoryinshop>> call, Response<List<modelcategoryinshop>> response) {
                if (response.isSuccessful()) {

                    modellist = response.body();
                    adaptershop = new holdercategoryinshop(modellist,getContext(),promorate);
                    recyclercat.setHasFixedSize(true);
                    recyclercat.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                    recyclercat.setAdapter(adaptershop);
                    adaptershop.notifyDataSetChanged();
                    adaptershop.setonshopclicklistener(shop.this);

                }
            }
            @Override
            public void onFailure(Call<List<modelcategoryinshop>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("colorposition",0);
        editor.apply();


        return view;
    }

    @Override
    public void onproclick(String proid,String title, String desc, String price, String discounted, String image, String color, String size, String days, String qtyleft) {
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
        bundle.putString("shopid",idd);
        bundle.putString("delcharges",delcharges);
        productfragment.setArguments(bundle);
        fragmentTransactionpro.replace(R.id.fragment, productfragment);
        fragmentTransactionpro.commit();
    }

    @Override
    public void onshopqclick(String id, String name, final String offrate) {
        //cat in shop
        catname.setText(name);

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        Retrofit retrofitcatinshop = new Retrofit.Builder()
                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/products/productbycat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productsbycatinshopapi api=retrofitcatinshop.create(productsbycatinshopapi.class);
        Call<List<modelproductbyshop>> listCall = api.response(idd,name);


        listCall.enqueue(new Callback<List<modelproductbyshop>>() {
            @Override
            public void onResponse(Call<List<modelproductbyshop>> call, Response<List<modelproductbyshop>> response) {
                if (response.isSuccessful()){
                modelpro=response.body();
                if (modelpro.isEmpty()){
                    Toast.makeText(getContext(), "No products", Toast.LENGTH_SHORT).show();
                }
                adapterproduct = new holderproductbyshop(modelpro, getContext(),offrate);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setAdapter(adapterproduct);
                adapterproduct.notifyDataSetChanged();
                adapterproduct.setonproinshopclicklistener(shop.this);
            }}

            @Override
            public void onFailure(Call<List<modelproductbyshop>> call, Throwable t) {

            }
        });

    }
}