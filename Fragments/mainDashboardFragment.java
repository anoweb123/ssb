package com.ali.ssb.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.ali.ssb.R;
import com.ali.ssb.dbhandler;
import com.ali.ssb.holderclasses.adaperslider;
import com.ali.ssb.holderclasses.holdercategory;
import com.ali.ssb.holderclasses.holderclassproducts;
import com.ali.ssb.holderclasses.holderproslider;
import com.ali.ssb.interfacesapi.allproductsapi;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nikartm.support.ImageBadgeView;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class mainDashboardFragment extends Fragment implements holderclassproducts.onproclicklistener,adaperslider.onshopclicklistener,holderproslider.onproclicklistener {
    ImageView menu;
    ImageBadgeView cart,notification;
    ProgressBar load;

    String category="";
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    adaperslider adapter;
    List<modelslider> models;

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

    Chip chip;
    Boolean isscrolling=false;
    com.ali.ssb.holderclasses.sliderbanneradapter sliderbanneradapter;
    List<modelbanner> modelsliders;
    int current_items,scrolled_items,total_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_dashboard, container, false);

        dbhandler dbhandler=new dbhandler(getContext());
        count=dbhandler.countitems();
        Toast.makeText(getContext(),String.valueOf(count), Toast.LENGTH_SHORT).show();
        dbhandler.close();

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

//        WifiManager wm = (WifiManager)getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
//        String ip = Formatter.formatIpAddress(wm.getDhcpInfo().dns1);
//        Toast.makeText(getContext(), ip.toString(), Toast.LENGTH_SHORT).show();

//        String head="Explore by categorieslayout";
//        SpannableString spannableString=new SpannableString(head);
//        ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(Color.BLUE);
//        spannableString.setSpan(foregroundColorSpan,2,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        final String[] spin1 = {String.valueOf(spannableString),"pents", "shirts"};

//        categories = (Spinner)view.findViewById(R.id.categor);
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spin1){
//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent)
//            {
//                View v = null;
//                v = super.getDropDownView(position, null, parent);
//                // If this is the selected item position
//                if (position == 0) {
//                    v.setBackgroundColor(Color.parseColor("#D3D3D3"));
//                }
//                else {
//                    // for other views
//                    v.setBackgroundColor(Color.WHITE);
//                }
//                return v;
//            }
//        };
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        categories.setAdapter(adapter1);

        load=view.findViewById(R.id.load);
        load.setVisibility(View.VISIBLE);
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
//slider
        shopsapi();

//categriesondashboard
        modelcat = new ArrayList<>();
        modelcat.add(new modelcateg("Food",R.drawable.food));
        modelcat.add(new modelcateg("Clothes",R.drawable.clothes));
        modelcat.add(new modelcateg("Electronics",R.drawable.electro));
        modelcat.add(new modelcateg("Fitness",R.drawable.sports));
        modelcat.add(new modelcateg("Health and safety",R.drawable.health));
        modelcat.add(new modelcateg("Groceries and households",R.drawable.groceries));

        recyclerViewcat.setHasFixedSize(true);
        recyclerViewcat.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        holdercategory = new holdercategory(modelcat, getContext());
        recyclerViewcat.setAdapter(holdercategory);
        holdercategory.notifyDataSetChanged();

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

//productsondashboard
        modelpro = new ArrayList<>();
        Retrofit retrofitpro = new Retrofit.Builder()
                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        allproductsapi apipro=retrofitpro.create(allproductsapi.class);
        Call<List<modelproducts>> listCallpro=apipro.listCall();

        listCallpro.enqueue(new Callback<List<modelproducts>>() {
            @Override
            public void onResponse(Call<List<modelproducts>> call, Response<List<modelproducts>> response) {
                modelpro=response.body();
                recpoduct.setHasFixedSize(true);
                final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
                recpoduct.setLayoutManager(manager);
                adapterproduct = new holderclassproducts(modelpro, getContext());
                recpoduct.setAdapter(adapterproduct);

                adapterproduct.notifyDataSetChanged();
                adapterproduct.setoncartclicklistener(mainDashboardFragment.this);

            }

            @Override
            public void onFailure(Call<List<modelproducts>> call, Throwable t) {

            }
        });

//        recpoduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                    isscrolling=true;
//                }
//            }
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                current_items=manager.getChildCount();
//                scrolled_items=manager.findFirstVisibleItemPosition();
//                total_items=manager.getItemCount();
//                if ((current_items+scrolled_items==total_items)){
//                    modelpro.add(new modelproducts("loren ipsum","1000",R.drawable.about,"2000"));
//                    modelpro.add(new modelproducts("loren ipsum","1000",R.drawable.banner,"2000"));
//                    modelpro.add(new modelproducts("loren ipsum","200",R.drawable.color,"1000"));
//                    modelpro.add(new modelproducts("loren ipsum","200",R.drawable.shop,"1000"));
//                    modelpro.add(new modelproducts("loren ipsum","200",R.drawable.color,"1000"));
//                    modelpro.add(new modelproducts("loren ipsum","200",R.drawable.color,"1000"));
//                    adapterproduct.notifyDataSetChanged();
//                }
//            }
//        });
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
        modelsliders = new ArrayList<>();
        modelsliders.add(new modelbanner(R.drawable.shop));
        modelsliders.add(new modelbanner(R.drawable.shop));
        modelsliders.add(new modelbanner(R.drawable.shop));
        modelsliders.add(new modelbanner(R.drawable.shop));
        modelsliders.add(new modelbanner(R.drawable.shop));
        modelsliders.add(new modelbanner(R.drawable.shop));
        modelsliders.add(new modelbanner(R.drawable.shop));
        modelsliders.add(new modelbanner(R.drawable.shop));
        modelsliders.add(new modelbanner(R.drawable.shop));
        modelsliders.add(new modelbanner(R.drawable.shop));

        sliderbanneradapter= new sliderbanneradapter(modelsliders,getContext());

        sliderView.setSliderAdapter(sliderbanneradapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);//set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        return view;
    }

    @Override
    public void onshopqclick(String id,String name,String cat) {
        shop shop = new shop();
        FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("shopid",id);
        bundle.putString("shopname",name);
        bundle.putString("shopcat",cat);
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
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setonshopclicklistener(mainDashboardFragment.this);
                    load.setVisibility(View.INVISIBLE);
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                        }
                    });

                }
            }
            @Override
            public void onFailure(Call<List<modelslider>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                shopsapi();
            }
        });
    }

}