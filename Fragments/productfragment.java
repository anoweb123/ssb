package com.ali.ssb.Fragments;

import android.animation.Animator;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.ssb.R;
import com.ali.ssb.dbhandler;
import com.dk.animation.circle.CircleAnimationUtil;
import com.squareup.picasso.Picasso;

import ru.nikartm.support.ImageBadgeView;

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
    CardView addtocart,addtowish;

    ImageBadgeView cart;
    int count;
//    Button addtocart,buynow,addtowish;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String title,price,discount,desc,image,leftinstoke,daysleft,color,size,proid;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
                final View view= inflater.inflate(R.layout.fragment_productfragment, container, false);


                addtocart=view.findViewById(R.id.addtocart);
                addtowish=view.findViewById(R.id.addtowishlist);

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
                daysleftview.setText(daysleft+" days left at this price");
                colorview.setText(color);
                sizeview.setText(size);
                //top pro idr ki wja sy crash kra ha
                Picasso.get().load(image.replaceFirst("localhost",prefs.getString("ipv4","10.0.2.2"))).into(imageView);

        addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dbhandler dbhandler=new dbhandler(getContext());
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
                });
                addtowish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbhandler dbhandler=new dbhandler(getContext());
                        long a=dbhandler.addtowishlist(title,image,desc,price,discount,color,size);
                        dbhandler.close();
                        Toast.makeText(getContext(), "Added to Wishlist", Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }
}