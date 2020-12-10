package com.ali.ssb.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.ssb.R;
import com.ali.ssb.dbhandler;
import com.ali.ssb.holderclasses.holdercart;
import com.ali.ssb.Models.modelcart;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.Fragments.paymentpage.MY_PREFS_forcart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cart extends Fragment implements holdercart.ondel{

    Button checkout;
    String delcharges;
    RecyclerView recyclerView;
    List<modelcart> modelcarts;
    holdercart adapter;
    TextView totalprice,totalpayable,totaldiscount,payable,delcharge,withoutdiscount;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cart.
     */
    // TODO: Rename and change types and number of parameters
    public static cart newInstance(String param1, String param2) {
        cart fragment = new cart();
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
        View view= inflater.inflate(R.layout.fragment_cart, container, false);

        checkout=view.findViewById(R.id.checkout);
        delcharge=view.findViewById(R.id.delcharge);


        checkout.setOnClickListener(v -> {
            checkoutpage productfragment = new checkoutpage();
            FragmentManager fragmentManagerpro =getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
            fragmentTransactionpro.replace(R.id.fragment, productfragment);
            fragmentTransactionpro.commit();
        });

        recyclerView=view.findViewById(R.id.rec);
        totalprice=view.findViewById(R.id.totalprice);
        withoutdiscount=view.findViewById(R.id.without);
        totaldiscount=view.findViewById(R.id.totaldiscount);
        totalpayable=view.findViewById(R.id.totalpayable);
        payable=view.findViewById(R.id.payable);


        modelcarts = new ArrayList<>();

        dbhandler dbhandler=new dbhandler(getContext());
        modelcarts=dbhandler.retrievecart();
        dbhandler.close();
//

        SharedPreferences preferences=getContext().getSharedPreferences(MY_PREFS_forcart,MODE_PRIVATE);
        delcharges=preferences.getString("deliverycharges","0");

        delcharge.setText("Rs."+delcharges);


        dbhandler dbhandler1=new dbhandler(getContext());
        int tprice=dbhandler1.totalprice();
        totalprice.setText("Rs. "+String.valueOf(tprice));
        totaldiscount.setText("Rs. "+String.valueOf(dbhandler1.totaldiscount()));
        withoutdiscount.setText("Rs. "+String.valueOf(dbhandler1.totalpricewithoutdiscount()));
        dbhandler1.close();
//
        totalpayable.setText("Rs. "+String.valueOf(tprice+Integer.parseInt(delcharges)));
        payable.setText("Rs. "+String.valueOf(tprice+Integer.parseInt(delcharges)));
//


        adapter = new holdercart(modelcarts, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.onclick(this);

        if (modelcarts.isEmpty()){
            nullcart productfragment = new nullcart();
            FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
            fragmentTransactionpro.replace(R.id.fragment, productfragment);
            fragmentTransactionpro.commit();

            SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE).edit();
            editor.putString("shopincartid", "");
            editor.apply();


            delcharge.setText("Rs. 0");


        }


        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(payable, "alpha", .7f, .2f);
        fadeOut.setDuration(1000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(payable, "alpha", .2f, .7f);
        fadeIn.setDuration(1000);

        final AnimatorSet mAnimationSet = new AnimatorSet();
        mAnimationSet.play(fadeIn).after(fadeOut);
        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimationSet.start();
            }
        });

        mAnimationSet.start();

//        dbhandler dbhandler2=new dbhandler(getContext());
//        Toast.makeText(getContext(),String.valueOf(dbhandler2.countitems()), Toast.LENGTH_SHORT).show();
//        dbhandler2.close();


//        ItemTouchHelper itemTouchHelper = new
//                ItemTouchHelper(new Swipetodeletecallback(adapter,getContext()));
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }
    @Override
    public void onclicker(int position){
        if (position==-2){
            modelcarts = new ArrayList<>();
            dbhandler dbhandler=new dbhandler(getContext());
            modelcarts=dbhandler.retrievecart();

            adapter = new holdercart(modelcarts, getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.onclick(this);
            int tprice=dbhandler.totalprice();
            totalprice.setText("Rs. "+String.valueOf(tprice));
            totaldiscount.setText("Rs. "+String.valueOf(dbhandler.totaldiscount()));
            withoutdiscount.setText("Rs. "+String.valueOf(dbhandler.totalpricewithoutdiscount()));
            dbhandler.close();


            SharedPreferences preferences=getContext().getSharedPreferences(MY_PREFS_forcart,MODE_PRIVATE);
            delcharges=preferences.getString("deliverycharges","0");


            delcharge.setText("Rs."+delcharges);

            totalpayable.setText("Rs. "+String.valueOf(tprice+Integer.parseInt(delcharges)));
            payable.setText("Rs. "+String.valueOf(tprice+Integer.parseInt(delcharges)));
            if (modelcarts.isEmpty() && recyclerView.getChildCount()==0){
                nullcart productfragment = new nullcart();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, productfragment);
                fragmentTransactionpro.commit();

                SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE).edit();
                editor.putString("shopincartid", "");
                editor.apply();

            }

        }
        else {
            modelcarts.remove(position);
            adapter.notifyDataSetChanged();

            modelcarts = new ArrayList<>();
            dbhandler dbhandler=new dbhandler(getContext());
            modelcarts=dbhandler.retrievecart();

            adapter = new holdercart(modelcarts, getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.onclick(this);
            int tprice=dbhandler.totalprice();
            totalprice.setText("Rs. "+String.valueOf(tprice));
            totaldiscount.setText("Rs. "+String.valueOf(dbhandler.totaldiscount()));
            withoutdiscount.setText("Rs. "+String.valueOf(dbhandler.totalpricewithoutdiscount()));
            dbhandler.close();

            SharedPreferences preferences=getContext().getSharedPreferences(MY_PREFS_forcart,MODE_PRIVATE);
            delcharges=preferences.getString("deliverycharges","0");

            delcharge.setText("Rs."+delcharges);

            totalpayable.setText("Rs. "+String.valueOf(tprice+Integer.parseInt(delcharges)));
            payable.setText("Rs. "+String.valueOf(tprice+Integer.parseInt(delcharges)));
            if (modelcarts.isEmpty() && recyclerView.getChildCount()==0){
                nullcart productfragment = new nullcart();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, productfragment);
                fragmentTransactionpro.commit();

                SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE).edit();
                editor.putString("shopincartid", "");
                editor.apply();

            }
        }
    }
}