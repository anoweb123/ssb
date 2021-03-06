package com.ali.ssb.Fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
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

import com.ali.ssb.Models.modelallpro;
import com.ali.ssb.Models.modelbaner;
import com.ali.ssb.R;
import com.ali.ssb.dashboardcustomer;
import com.ali.ssb.dbhandler;
import com.ali.ssb.holderclasses.adaperslider;
import com.ali.ssb.holderclasses.holdercategory;
import com.ali.ssb.holderclasses.holderclassproducts;
import com.ali.ssb.holderclasses.holderdiscountpro;
import com.ali.ssb.holderclasses.holderproslider;
import com.ali.ssb.holderclasses.holdertopshops;
import com.ali.ssb.interfacesapi.allproductsapi;
import com.ali.ssb.interfacesapi.apitopshops;
import com.ali.ssb.interfacesapi.banerapi;
import com.ali.ssb.interfacesapi.notificationcount;
import com.ali.ssb.interfacesapi.shopsapi;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class mainDashboardFragment extends Fragment implements holderclassproducts.onproclicklistener,adaperslider.onshopclicklistener,holderproslider.onproclicklistener,holdercategory.oncatclicklistener,sliderbanneradapter.onbanerclicklistener,holderdiscountpro.onproclicklistener,holdertopshops.ontopshopclicklistener {
    ImageView menu;
    String notify_count;
    ImageBadgeView cart,notification;
    ProgressBar load;
    NestedScrollView scrollView;

    public static String CHannel_ID="my id";

    String category="";
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    adaperslider adapter;
    List<modelslider> models;
    LinearLayout frag;

    RecyclerView recyclerViewcat;
    holdercategory holdercategory;
    List<modelcateg> modelcat;


    RecyclerView recyclerdiscountpro;
    holderdiscountpro holderdiscountpro;
    List<modelallpro> listallpro;

    int count;
    Spinner categories;
//    EditText searchView;
    RecyclerView recpoduct;

    RecyclerView recsliderpro;
    holderproslider holderproslider;
    List<modelproductslider> list;


    RecyclerView rectopshops;
    holdertopshops holdertopshops;
    List<modelslider> listtopshop;


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



        scrollView=view.findViewById(R.id.scrollView);



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

//countnotiapi
        SharedPreferences prefsss = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        Retrofit retrofitpros = new Retrofit.Builder()
                .baseUrl("http://"+prefsss.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notificationcount apipros=retrofitpros.create(notificationcount.class);
        Call<String> listCallpros=apipros.list("notifications/countCustomer");

        listCallpros.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                Toast.makeText(getContext(), String.valueOf(response.body()),Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences=getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
                notify_count= sharedPreferences.getString("notification","0");

                notification.setBadgeValue(Integer.valueOf(response.body())-Integer.valueOf(notify_count))
                        .setBadgeOvalAfterFirst(true)
                        .setMaxBadgeValue(999)
                        .setBadgeTextStyle(Typeface.NORMAL)
                        .setShowCounter(true)
                        .setBadgePadding(4);


                if (notify_count.equals("")){
                }
                else {
                    if (Integer.valueOf(notify_count)<Integer.valueOf(response.body())){

//                        notification.setBadgeValue(Integer.valueOf(response.body())-Integer.valueOf(notify_count))
//                                .setBadgeOvalAfterFirst(true)
//                                .setMaxBadgeValue(999)
//                                .setBadgeTextStyle(Typeface.NORMAL)
//                                .setShowCounter(true)
//                                .setBadgePadding(4);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel notificationChannel = new NotificationChannel(CHannel_ID, "personal", NotificationManager.IMPORTANCE_HIGH);
                            notificationChannel.setDescription("heloo");
                            NotificationManager notificationManager=(NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.createNotificationChannel(notificationChannel);
                        }

                                Intent mintent=new Intent(getContext(),dashboardcustomer.class);
                                mintent.putExtra("checkfornotify",1);
                                mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                PendingIntent pendingIntent=PendingIntent.getActivities(getActivity(),0, new Intent[]{mintent},PendingIntent.FLAG_ONE_SHOT);

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(),CHannel_ID)
                                .setSmallIcon(R.drawable.notification)
                                .setContentTitle("May have new notifications")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setAutoCancel(true)
                                .setContentText("Checkout the latest deals and discounts!");

                                mBuilder.setContentIntent(pendingIntent);

                        NotificationManager mNotificationManager = (NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(001, mBuilder.build());

                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });





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
//topsshops

        rectopshops=view.findViewById(R.id.rectopshops);

        listtopshop = new ArrayList<>();
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final apitopshops api=retrofit.create(apitopshops.class);
        Call<List<modelslider>> listCall=api.list();

        listCall.enqueue(new Callback<List<modelslider>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<modelslider>> call, Response<List<modelslider>> response) {
                if (response.isSuccessful()){
                    listtopshop=response.body();

                    holdertopshops = new holdertopshops(listtopshop, getContext());
                    rectopshops.setHasFixedSize(true);
                    rectopshops.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                    rectopshops.setAdapter(holdertopshops);
                    holdertopshops.notifyDataSetChanged();
                    holdertopshops.setontopshopclicklistener(mainDashboardFragment.this);

//                    load.setVisibility(View.INVISIBLE);
//                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                    frag.setAlpha((float) 1.0);

                }
                else {
//                    shopsapi();
                }
                if (response.code()==500){
//                    shopsapi();
                }
            }
            @Override
            public void onFailure(Call<List<modelslider>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                shopsapi();
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
//discountproducts
        listallpro = new ArrayList<>();
        recyclerdiscountpro=view.findViewById(R.id.recdiscount);

        SharedPreferences preferences = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofits = new Retrofit.Builder()
                .baseUrl("http://"+preferences.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final allproductsapi mapi=retrofits.create(allproductsapi.class);
        Call<List<modelallpro>> listCallm=mapi.listCall();

        listCallm.enqueue(new Callback<List<modelallpro>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<modelallpro>> call, Response<List<modelallpro>> response) {
                if (response.isSuccessful()){
                    listallpro.addAll(response.body());

                        List<modelallpro> list2=new ArrayList<>();
                    for (int i=0;i<listallpro.size();i++){

                        Boolean promo=false;
                        if (listallpro.get(i).getPromotionStatus().equals("accepted")) {
                            if (listallpro.get(i).getPromotionTill().isEmpty() || listallpro.get(i).getPromotionTill().equals("N/A") || listallpro.get(i).getPromotionTill().equals("none") || listallpro.get(i).getPromotionTill().equals("")) {

                            } else {

                                LocalDate currentDate = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    currentDate = LocalDate.now(ZoneId.systemDefault());
                                }

                                LocalDate getDates = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    if (listallpro.get(i).getPromotionTill().isEmpty() || listallpro.get(i).getPromotionTill().equals("N/A") || listallpro.get(i).getPromotionTill().equals("none") || listallpro.get(i).getPromotionTill().equals("")) {
                                    } else {
                                        getDates = LocalDate.parse(listallpro.get(i).getPromotionTill());
                                    }
                                }

                                if (currentDate.minusDays(1).isBefore(getDates)) {
                                    promo = true;
                                }

                            }
                            if (promo){
                                list2.add(listallpro.get(i));
                            }
                    }

                    holderdiscountpro = new holderdiscountpro(getContext(),list2);
                    recyclerdiscountpro.setHasFixedSize(true);
                    recyclerdiscountpro.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                    recyclerdiscountpro.setAdapter(holderdiscountpro);
                    holderdiscountpro.notifyDataSetChanged();
                    holderdiscountpro.setonproclicklistener(mainDashboardFragment.this);


                        load.setVisibility(View.INVISIBLE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        frag.setAlpha((float) 1.0);


//                    adapter.setonshopclicklistener(mainDashboardFragment.this);
//                    load.setVisibility(View.INVISIBLE);
//                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                    frag.setAlpha((float) 1.0);

                }
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<modelslider>> call, Response<List<modelslider>> response) {
                if (response.isSuccessful()){
                    models=response.body();
                    List<modelslider> modeloff = new ArrayList<>();
                    for (int i=0;i<response.body().size();i++){

                        Boolean promos=false;

                        String prostatus=models.get(i).getPromotionStatus();

                        if (models.get(i).getPromotionTill().isEmpty()||models.get(i).getPromotionTill().equals("")||models.get(i).getPromotionTill().equals("none")||models.get(i).getPromotionTill().equals("N/A")){}
                        else {

                            LocalDate currentDate = null;
                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                currentDate = LocalDate.now(ZoneId.systemDefault());
                            }

                            LocalDate getDates = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                if (models.get(i).getPromotionTill().isEmpty() || models.get(i).getPromotionTill().equals("none")) {
                                } else {
                                    getDates = LocalDate.parse(models.get(i).getPromotionTill());
                                }
                            }
                            if (currentDate.minusDays(1).isBefore(getDates)){
                                if (prostatus.equals("accepted")){
                                    promos=true;
                                }
                            }
                            else {
                                promos=false;
                            }
                        }
                        if (promos){
                                modeloff.add(response.body().get(i));
                                models.remove(i);
                        }
                    }

                    List<modelslider> shops=new ArrayList<>();
                    shops.addAll(modeloff);
                    shops.addAll(models);

                    adapter = new adaperslider(shops, getContext());
                    recyclerView.setHasFixedSize(true);
                    final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setonshopclicklistener(mainDashboardFragment.this);


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
                        Toast.makeText(getContext(), "NO shops avaiable", Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.INVISIBLE);
                    }
                    else {

                        adapter = new adaperslider(modelsliders, getContext());
                        recyclerView.setHasFixedSize(true);
                        final LinearLayoutManager manager=new GridLayoutManager(getContext(),2);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), cat, Toast.LENGTH_SHORT).show();
                        adapter.setonshopclicklistener(mainDashboardFragment.this);

                        scrollView.post(new Runnable() {
                            public void run() {
                                scrollView.smoothScrollTo(0, recyclerView.getTop());
                            }
                        });

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

    @Override
    public void onproclick(String title, String desc, String price, String discounted, String image, String color, String size, String days, String qtyleft, String proid, String delcharges, String shopidd) {

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
        bundle.putString("shopid",shopidd);
        bundle.putString("delcharges",delcharges);
        productfragment.setArguments(bundle);
        fragmentTransactionpro.replace(R.id.fragment, productfragment);
        fragmentTransactionpro.commit();

    }

    @Override
    public void ontopshopclick(String id, String name, String cat, String delcharges, String promorate) {

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
}