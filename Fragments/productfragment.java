package com.ali.ssb.Fragments;

import android.animation.Animator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.ssb.R;
import com.ali.ssb.dbhandler;
import com.dk.animation.circle.CircleAnimationUtil;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;

import ru.nikartm.support.ImageBadgeView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.Fragments.profilecustomer.MY_PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link productfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class productfragment extends Fragment {

    TextView discountedview,priceview,descview,daysleftview,leftinstockview,colorview,sizeview,titleview;
    ImageView imageView;
    CardView addtocart,addtowish,buynow;

    public static String MY_PREFS_forcart="MY_PREFS_forcart";
    ImageBadgeView cart;
    String deliverycharges;
    int count;
//    Button addtocart,buynow,addtowish;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String title,price,discount,desc,image,leftinstoke,daysleft,shopid,color,size,proid;

    public productfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment productfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static productfragment newInstance(String param1, String param2) {
        productfragment fragment = new productfragment();
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
                View view= inflater.inflate(R.layout.fragment_productfragment, container, false);

                addtocart=view.findViewById(R.id.addtocart);
                addtowish=view.findViewById(R.id.addtowishlist);
                buynow=view.findViewById(R.id.buynow);

                discountedview=view.findViewById(R.id.discount);
                priceview=view.findViewById(R.id.price);
                daysleftview=view.findViewById(R.id.daysleft);
                titleview=view.findViewById(R.id.title);
                descview=view.findViewById(R.id.desc);
                colorview=view.findViewById(R.id.color);
                sizeview=view.findViewById(R.id.size);
                leftinstockview=view.findViewById(R.id.leftinstock);
                imageView=view.findViewById(R.id.img);
                cart=view.findViewById(R.id.cart);

                SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

                dbhandler dbhandler=new dbhandler(getContext());
                count=dbhandler.countitems();
                dbhandler.close();


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

        cart.setBadgeValue(count)
                .setBadgeOvalAfterFirst(true)
                .setMaxBadgeValue(999)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true);

                title=getArguments().getString("titlekey");
                price=getArguments().getString("pricekey");
                discount=getArguments().getString("discountedkey");
                desc=getArguments().getString("desckey");
                image=getArguments().getString("imagekey");
                leftinstoke=getArguments().getString("qtyleftkey");
                daysleft=getArguments().getString("dayskey");
                color=getArguments().getString("colorkey");
                size=getArguments().getString("sizekey");
                proid=getArguments().getString("proid");
                shopid=getArguments().getString("shopid");
                deliverycharges=getArguments().getString("delcharges");

    if (daysleft.equals("")||daysleft.equals(null)||daysleft.equals("none")||daysleft.equals("0")){
        daysleftview.setText("Sale before: No sale ");
    }
    else {

        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentDate = LocalDate.now(ZoneId.systemDefault());
        }

        LocalDate getDates = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getDates = LocalDate.parse(daysleft);
        }

        if (currentDate.minusDays(1).isBefore(getDates)) {
            daysleftview.setText("Sale before "+daysleft);
        } else {
//            daysleftview.setVisibility(View.INVISIBLE);
            daysleftview.setText("Sale before: No sale ");
        }
    }
                if (discount.equals("0")||discount.equals("none")){
                    discountedview.setVisibility(View.INVISIBLE);
                }
                else {
                    discountedview.setText("Rs "+discount);
                }
                discountedview.setPaintFlags(discountedview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                titleview.setText(title);
                priceview.setText("Rs "+price);
                descview.setText(desc);
                leftinstockview.setText(leftinstoke);

                colorview.setText(color);
                sizeview.setText(size);
                Picasso.get().load(image.replaceFirst("localhost",prefs.getString("ipv4","10.0.2.2"))).into(imageView);

                buynow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Integer.parseInt(leftinstoke)>0){


                        dbhandler dbhandler=new dbhandler(getContext());
                        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE);
                        String shopidd= prefs.getString("shopincartid","");
                        if(shopidd.equals(shopid)||shopidd.equals("")){

                            SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE).edit();
                            editor.putString("shopincartid", shopid);
                            editor.putString("deliverycharges", deliverycharges);
                            editor.apply();

                            String a=dbhandler.addtocart(proid,title,image,desc,price,discount,color,size,"1",Integer.valueOf(leftinstoke));
                            dbhandler.close();
                            if (a.equals("duplicateexists")||a=="duplicateexists"){
                                cart cart = new cart();
                                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                                Bundle bundle=new Bundle();
                                cart.setArguments(bundle);
                                fragmentTransactionpro.replace(R.id.fragment, cart);
                                fragmentTransactionpro.commit();
                                Toast.makeText(getContext(), "Already in cart you can add in quantity", Toast.LENGTH_LONG).show();
                            }
                            else {

                                new CircleAnimationUtil().attachActivity(getActivity()).setTargetView(imageView).setMoveDuration(500).setDestView(cart).setAnimationListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        dbhandler dbhandlers=new dbhandler(getContext());
                                        count=dbhandlers.countitems();
                                        dbhandlers.close();

                                        cart.setBadgeValue(count)
                                                .setBadgeOvalAfterFirst(true)
                                                .setMaxBadgeValue(999)
                                                .setBadgeTextStyle(Typeface.NORMAL)
                                                .setShowCounter(true);
                                        imageView.setVisibility(View.VISIBLE);

                                        cart cart = new cart();
                                        FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                                        Bundle bundle=new Bundle();
                                        cart.setArguments(bundle);
                                        fragmentTransactionpro.replace(R.id.fragment, cart);
                                        fragmentTransactionpro.commit();



                                    }
                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                }).startAnimation();

                            }
                        }
                        else {
                            CardView cardView = view.findViewById(R.id.cardview);
                            cardView.setAlpha((float) 0.5);
                            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = inflater.inflate(R.layout.popupanothershop, null);

                            // create the popup window
                            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                            boolean focusable = true; // lets taps outside the popup also dismiss it
                            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    cardView.setAlpha((float) 1.0);
                                }
                            });
                            popupWindow.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                            Button ok = popupView.findViewById(R.id.ok);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dbhandler dbhandler1 = new dbhandler(getContext());
                                    dbhandler1.deleteallincart();

                                    cart.setBadgeValue(dbhandler1.countitems())
                                            .setBadgeOvalAfterFirst(true)
                                            .setMaxBadgeValue(999)
                                            .setBadgeTextStyle(Typeface.NORMAL)
                                            .setShowCounter(true);
                                    dbhandler1.close();

                                    SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE).edit();
                                    editor.putString("shopincartid", shopid);
                                    editor.putString("deliverycharges", deliverycharges);
                                    editor.apply();

                                    popupWindow.dismiss();

                                    new CircleAnimationUtil().attachActivity(getActivity()).setTargetView(imageView).setMoveDuration(500).setDestView(cart).setAnimationListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            dbhandler dbhandlers = new dbhandler(getContext());
                                            dbhandlers.addtocart(proid, title, image, desc, price, discount, color, size, "1", Integer.valueOf(leftinstoke));
                                            count = dbhandlers.countitems();
                                            dbhandlers.close();

                                            cart.setBadgeValue(count)
                                                    .setBadgeOvalAfterFirst(true)
                                                    .setMaxBadgeValue(999)
                                                    .setBadgeTextStyle(Typeface.NORMAL)
                                                    .setShowCounter(true);
                                            imageView.setVisibility(View.VISIBLE);
                                            Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                                            cart cart = new cart();
                                            FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                                            Bundle bundle = new Bundle();
                                            cart.setArguments(bundle);
                                            fragmentTransactionpro.replace(R.id.fragment, cart);
                                            fragmentTransactionpro.commit();


                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    }).startAnimation();


                                }
                            });

                            Button cancel=popupView.findViewById(R.id.cancel);
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    popupWindow.dismiss();
                                }
                            });
                        }
                        }
                        else {
                            Snackbar snack = Snackbar.make(
                                    (getActivity().findViewById(android.R.id.content)),
                                    "Out of stock", Snackbar.LENGTH_SHORT);
                            snack.show();
                        }
                    }
                });



        addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(leftinstoke)>0){

                        dbhandler dbhandler=new dbhandler(getContext());
                        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE);
                        String shopidd= prefs.getString("shopincartid","");
                        if(shopidd.equals(shopid)||shopidd.equals("")){

                        SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE).edit();
                        editor.putString("shopincartid", shopid);
                        editor.putString("deliverycharges", deliverycharges);
                        editor.apply();

                            String a=dbhandler.addtocart(proid,title,image,desc,price,discount,color,size,"1",Integer.valueOf(leftinstoke));
                            dbhandler.close();
                            if (a.equals("duplicateexists")||a=="duplicateexists"){
                                cart cart = new cart();
                                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                                Bundle bundle=new Bundle();
                                cart.setArguments(bundle);
                                fragmentTransactionpro.replace(R.id.fragment, cart);
                                fragmentTransactionpro.commit();
                                Toast.makeText(getContext(), "Already in cart you can add in quantity", Toast.LENGTH_LONG).show();
                            }
                            else {

                                new CircleAnimationUtil().attachActivity(getActivity()).setTargetView(imageView).setMoveDuration(500).setDestView(cart).setAnimationListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        dbhandler dbhandlers=new dbhandler(getContext());
                                        count=dbhandlers.countitems();
                                        dbhandlers.close();

                                        cart.setBadgeValue(count)
                                                .setBadgeOvalAfterFirst(true)
                                                .setMaxBadgeValue(999)
                                                .setBadgeTextStyle(Typeface.NORMAL)
                                                .setShowCounter(true);
                                        imageView.setVisibility(View.VISIBLE);
                                        Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                                    }
                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                }).startAnimation();

                        }
                        }
                        else {
                            CardView cardView=view.findViewById(R.id.cardview);
                            cardView.setAlpha((float) 0.5);
                            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = inflater.inflate(R.layout.popupanothershop, null);

                            // create the popup window
                            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                            boolean focusable = true; // lets taps outside the popup also dismiss it
                            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    cardView.setAlpha((float) 1.0);
                                }
                            });
                            popupWindow.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                            Button ok=popupView.findViewById(R.id.ok);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dbhandler dbhandler1=new dbhandler(getContext());
                                    dbhandler1.deleteallincart();

                                    cart.setBadgeValue(dbhandler1.countitems())
                                            .setBadgeOvalAfterFirst(true)
                                            .setMaxBadgeValue(999)
                                            .setBadgeTextStyle(Typeface.NORMAL)
                                            .setShowCounter(true);
                                    dbhandler1.close();

                                    SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE).edit();
                                    editor.putString("shopincartid", shopid);
                                    editor.putString("deliverycharges", deliverycharges);
                                    editor.apply();

                                    popupWindow.dismiss();

                                    new CircleAnimationUtil().attachActivity(getActivity()).setTargetView(imageView).setMoveDuration(500).setDestView(cart).setAnimationListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                        }
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            dbhandler dbhandlers=new dbhandler(getContext());
                                            dbhandlers.addtocart(proid,title,image,desc,price,discount,color,size,"1",Integer.valueOf(leftinstoke));
                                            count=dbhandlers.countitems();
                                            dbhandlers.close();


                                            cart.setBadgeValue(count)
                                                    .setBadgeOvalAfterFirst(true)
                                                    .setMaxBadgeValue(999)
                                                    .setBadgeTextStyle(Typeface.NORMAL)
                                                    .setShowCounter(true);
                                            imageView.setVisibility(View.VISIBLE);
                                            Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                                        }
                                        @Override
                                        public void onAnimationCancel(Animator animation) {
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    }).startAnimation();



                                }
                            });

                            Button cancel=popupView.findViewById(R.id.cancel);
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    popupWindow.dismiss();
                                }
                            });
                        }
                    }
                        else {
                            Snackbar snack = Snackbar.make(
                                    (getActivity().findViewById(android.R.id.content)),
                                    "Out of stock", Snackbar.LENGTH_SHORT);
                            snack.show();
                        }
                    }
                });
                addtowish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(leftinstoke)>0){
                        dbhandler dbhandler=new dbhandler(getContext());
                        long a=dbhandler.addtowishlist(proid,title,desc,color,size,leftinstoke,discount,image,price);
                        dbhandler.close();
                        Toast.makeText(getContext(), "Added to Wishlist", Toast.LENGTH_SHORT).show();
                    }
                        else {
                            Snackbar snack = Snackbar.make(
                                    (getActivity().findViewById(android.R.id.content)),
                                    "Out of stock", Snackbar.LENGTH_SHORT);
                            snack.show();
                        }
                    }
                });
        return view;
    }
}