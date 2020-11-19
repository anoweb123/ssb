package com.ali.ssb.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.ali.ssb.Models.modelbaner;
import com.ali.ssb.R;
import com.ali.ssb.dbhandler;
import com.ali.ssb.holderclasses.adaperslider;
import com.ali.ssb.holderclasses.holdercategory;
import com.ali.ssb.holderclasses.holderclassproducts;
import com.ali.ssb.holderclasses.holderproslider;
import com.ali.ssb.interfacesapi.allproductsapi;
import com.ali.ssb.interfacesapi.banerapi;
import com.ali.ssb.interfacesapi.shopsapi;
import com.ali.ssb.Models.modelbanner;
import com.ali.ssb.Models.modelcateg;
import com.ali.ssb.Models.modelproducts;
import com.ali.ssb.Models.modelproductslider;
import com.ali.ssb.Models.modelslider;
import com.ali.ssb.holderclasses.sliderbanneradapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nikartm.support.ImageBadgeView;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class mainDashboardFragment extends Fragment implements holderclassproducts.onproclicklistener,adaperslider.onshopclicklistener,holderproslider.onproclicklistener,holdercategory.oncatclicklistener,sliderbanneradapter.onbanerclicklistener {
    ImageView menu;
    ImageBadgeView cart,notification;
    ProgressBar load;

    String category="";
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    adaperslider adapter;
    List<modelslider> models;
    LinearLayout frag;

    RecyclerView recyclerViewcat;
    holdercategory holdercategory;
    List<modelcateg> modelcat;

    int count;
    Spinner categories;
//    EditText searchView;
    RecyclerView recpoduct;

    RecyclerView recsliderpro;
    holderproslider holderproslider;
    List<modelproductslider> list;

    holderclassproducts adapterproduct;
    List<modelproducts> modelpro;
    SliderView sliderView;

    Calendar calendar;
    Chip chip;
    Boolean isscrolling=false;
    com.ali.ssb.holderclasses.sliderbanneradapter sliderbanneradapter;
    List<modelbaner> modelsliders;
    int current_items,scrolled_items,total_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_dashboard, container, false);

        frag=view.findViewById(R.id.fragment);
        dbhandler dbhandler=new dbhandler(getContext());
        count=dbhandler.countitems();
        dbhandler.close();

        String dateStr = "04/05/2010";

        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy/MM/dd");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy MM, dd");

        String newDateStr = postFormater.format(dateObj);


//        chip=view.findViewById(R.id.chip);
//
//        chip.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onGlobalLayout() {
//                BadgeDrawable badgeDrawable = BadgeDrawable.create(getContext());
//                badgeDrawable.setNumber(4);
//
//                BadgeUtils.attachBadgeDrawable(badgeDrawable, chip, null);
//                chip.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });

        cart=view.findViewById(R.id.cart);
        notification=view.findViewById(R.id.notification);

//        dbhandler dbhandler1=new dbhandler(getContext());
//        dbhandler1.deleteallwishlist();
//        dbhandler1.close();

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifications notify = new notifications();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, notify);
                fragmentTransactionpro.commit();

            }
        });

        cart.setBadgeValue(count)
                .setBadgeOvalAfterFirst(true)
                .setMaxBadgeValue(999)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);
        notification.setBadgeValue(27)
                .setBadgeOvalAfterFirst(true)
                .setMaxBadgeValue(999)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        load=view.findViewById(R.id.load);
        load.setVisibility(View.VISIBLE);
//        to disable activity
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        frag.setAlpha((float) 0.5);



        recsliderpro=view.findViewById(R.id.recpro);
        list=new ArrayList<>();

        recyclerViewcat=view.findViewById(R.id.reccat);
        modelcat=new ArrayList<>();

        SliderView sliderView =view.findViewById(R.id.imageSlider);
        recpoduct=view.findViewById(R.id.productrec);

        cart=view.findViewById(R.id.cart);
        menu=view.findViewById(R.id.menu);

//        searchView=view.findViewById(R.id.search);
//        searchView.requestFocus();

            cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart cart = new cart();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, cart);
                fragmentTransactionpro.commit();
            }
        });
        recyclerView=view.findViewById(R.id.rec);
//shopss
        shopsapi();
//categriesondashboard
        modelcat = new ArrayList<>();
        modelcat.add(new modelcateg("Food",R.drawable.food));
        modelcat.add(new modelcateg("Clothing",R.drawable.clothes));
        modelcat.add(new modelcateg("Electronics",R.drawable.electro));
        modelcat.add(new modelcateg("Sports Fitness and Outdoors",R.drawable.sports));
        modelcat.add(new modelcateg("Health and safety",R.drawable.health));
        modelcat.add(new modelcateg("Grocery, Households nd Pets",R.drawable.groceries));
        modelcat.add(new modelcateg("Beauty and Health",R.drawable.beautyandhealth));
        modelcat.add(new modelcateg("Wholesales",R.drawable.wholesale));
        modelcat.add(new modelcateg("Entertainment",R.drawable.enetrtainment));
        modelcat.add(new modelcateg("Furniture",R.drawable.furniture));
        modelcat.add(new modelcateg("Home Decor",R.drawable.homedecor));
        modelcat.add(new modelcateg("Jewellery",R.drawable.jwellery));
        modelcat.add(new modelcateg("Toys and Videos",R.drawable.toys));
        modelcat.add(new modelcateg("Deal and More",R.drawable.deals));

        recyclerViewcat.setHasFixedSize(true);
        recyclerViewcat.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        holdercategory = new holdercategory(modelcat, getContext());
        recyclerViewcat.setAdapter(holdercategory);
        holdercategory.setoncatclicklistener(mainDashboardFragment.this);
        holdercategory.notifyDataSetChanged();

        SharedPreferences prefss = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

//bannerapi
        modelsliders = new ArrayList<>();
        Retrofit retrofitpro = new Retrofit.Builder()
                .baseUrl("http://"+prefss.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        banerapi apipro=retrofitpro.create(banerapi.class);
        Call<List<modelbaner>> listCallpro=apipro.listCall();

        listCallpro.enqueue(new Callback<List<modelbaner>>() {
            @Override
            public void onResponse(Call<List<modelbaner>> call, Response<List<modelbaner>> response) {
                if (response.isSuccessful()){

                    modelsliders=response.body();
                    sliderbanneradapter= new sliderbanneradapter(modelsliders,getContext());
                    sliderView.setSliderAdapter(sliderbanneradapter);
                    sliderbanneradapter.setonbanerclicklistener(mainDashboardFragment.this);

                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);//set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    sliderView.setIndicatorSelectedColor(Color.WHITE);
                    sliderView.setIndicatorUnselectedColor(Color.GRAY);
                    sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                    sliderView.startAutoCycle();
                }
            }

            @Override
            public void onFailure(Call<List<modelbaner>> call, Throwable t) {

            }
        });


//productsondashboardslider
        list.add(new modelproductslider(R.drawable.shirt,"Brochur","1500","2000"));
        list.add(new modelproductslider(R.drawable.shirt,"Brochur","1500","2000"));
        list.add(new modelproductslider(R.drawable.shirt,"nickie","1500","2000"));
        list.add(new modelproductslider(R.drawable.shirt,"nickie","1500","2000"));
        list.add(new modelproductslider(R.drawable.shirt,"Brochurenickienickienickie","1500","2000"));
        list.add(new modelproductslider(R.drawable.shirt,"Brochur","1500","2000"));

        recsliderpro.setHasFixedSize(true);
        recsliderpro.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        holderproslider = new holderproslider(list, getContext());
        recsliderpro.setAdapter(holderproslider);
        holderproslider.notifyDataSetChanged();
        holderproslider.setoncartclicklistener(this);
//discount_banner
//        modelsliders = new ArrayList<>();
//        modelsliders.add(new modelbanner(R.drawable.shop));
//        modelsliders.add(new modelbanner(R.drawable.shop));
//        modelsliders.add(new modelbanner(R.drawable.shop));
//        modelsliders.add(new modelbanner(R.drawable.shop));
//        modelsliders.add(new modelbanner(R.drawable.shop));
//        modelsliders.add(new modelbanner(R.drawable.shop));
//        modelsliders.add(new modelbanner(R.drawable.shop));
//        modelsliders.add(new modelbanner(R.drawable.shop));
//        modelsliders.add(new modelbanner(R.drawable.shop));
//        modelsliders.add(new modelbanner(R.drawable.shop));
//
//        sliderbanneradapter= new sliderbanneradapter(modelsliders,getContext());
//
//        sliderView.setSliderAdapter(sliderbanneradapter);
//
//        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);//set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
//        sliderView.setIndicatorSelectedColor(Color.WHITE);
//        sliderView.setIndicatorUnselectedColor(Color.GRAY);
//        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
//        sliderView.startAutoCycle();
        return view;
    }

    @Override
    public void onshopqclick(String id,String name,String cat,String delcharge,String promorate) {
        shop shop = new shop();
        FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("shopid",id);
        bundle.putString("shopname",name);
        bundle.putString("shopcat",cat);
        bundle.putString("delcharges",delcharge);
        bundle.putString("promorate",promorate);
        shop.setArguments(bundle);
        fragmentTransactionpro.replace(R.id.fragment,shop);
        fragmentTransactionpro.commit();
    }
    @Override
    public void onproclick(String title, String desc, String price, String discounted, String image, String color, String size, String days, String qtyleft) {
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
        bundle.putString("imagekey",image);
        productfragment.setArguments(bundle);
        fragmentTransactionpro.replace(R.id.fragment, productfragment);
        fragmentTransactionpro.commit();
    }
    @Override
    public void onproclickinslide(String title, String desc, String price, String discounted, int image, String color, String size, String days, String qtyleft) {
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
        bundle.putInt("imagekey",image);
        productfragment.setArguments(bundle);
        fragmentTransactionpro.replace(R.id.fragment, productfragment);
        fragmentTransactionpro.commit();
    }
    public void shopsapi() {

        models = new ArrayList<>();
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final shopsapi api=retrofit.create(shopsapi.class);
        Call<List<modelslider>> listCall=api.list();

        listCall.enqueue(new Callback<List<modelslider>>() {
            @Override
            public void onResponse(Call<List<modelslider>> call, Response<List<modelslider>> response) {
                if (response.isSuccessful()){
                    models=response.body();
                    adapter = new adaperslider(models, getContext());
                    recyclerView.setHasFixedSize(true);
                    final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setonshopclicklistener(mainDashboardFragment.this);
                    load.setVisibility(View.INVISIBLE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    frag.setAlpha((float) 1.0);

                }
                else {
                    shopsapi();
                }
                if (response.code()==500){
                    shopsapi();
                }
            }
            @Override
            public void onFailure(Call<List<modelslider>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                shopsapi();
            }
        });
    }

    @Override
    public void oncatclick(String cat) {


        models = new ArrayList<>();
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final shopsapi api=retrofit.create(shopsapi.class);
        Call<List<modelslider>> listCall=api.list();

        listCall.enqueue(new Callback<List<modelslider>>() {
            @Override
            public void onResponse(Call<List<modelslider>> call, Response<List<modelslider>> response) {
                if (response.isSuccessful()){
                    models=response.body();

                    load.setVisibility(View.VISIBLE);
                    List<modelslider> modelsliders=new ArrayList<>();

                    for (int i=0;i<models.size();i++) {
                        if (models.get(i).getShopCategory().equals(cat)) {
                            modelsliders.add(models.get(i));
                        }
                        else {
                        }
                    }
                    if (modelsliders.size()==0||modelsliders==null){
                        Toast.makeText(getContext(), "NO Products", Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.INVISIBLE);
                    }
                    else {

                        adapter = new adaperslider(modelsliders, getContext());
                        recyclerView.setHasFixedSize(true);
                        final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.setonshopclicklistener(mainDashboardFragment.this);
                        load.setVisibility(View.INVISIBLE);}

                }
            }
            @Override
            public void onFailure(Call<List<modelslider>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                shopsapi();
            }
        });



    }

    @Override
    public void onbanerclick(String id, String name, String cat, String type) {
        //here to continue
    }
}