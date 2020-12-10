package com.ali.ssb.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.ali.ssb.Fragments.summary;
import com.ali.ssb.Models.modelcart;
import com.ali.ssb.Models.modelreturnoforderinfo;
import com.ali.ssb.R;
import com.ali.ssb.checkoutpaymentstipe;
import com.ali.ssb.dbhandler;
import com.ali.ssb.interfacesapi.orderinfoapi;
import com.ali.ssb.interfacesapi.orderitemapi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link paymentpage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class paymentpage extends Fragment {
    ssbprice ssbprice;
    int d=0;
    String shopid,orderid;
    public static String MY_PREFS_forcart="MY_PREFS_forcart";
    Button pay;
    String lat,lon,paymentstatud,paymentmethod;
    creditcardprice creditcardprice;

    String paymentstatusorder,paymentmethodorder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CheckBox checkBoxssb,checkBoxcredit,cashondel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public paymentpage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment paymentpage.
     */
    // TODO: Rename and change types and number of parameters
    public static paymentpage newInstance(String param1, String param2) {
        paymentpage fragment = new paymentpage();
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
        View view= inflater.inflate(R.layout.fragment_paymentpage, container, false);
//        checkBoxssb=view.findViewById(R.id.ssbcheck);
        checkBoxcredit=view.findViewById(R.id.creditcardcheck);
        cashondel=view.findViewById(R.id.cashondel);




        pay=view.findViewById(R.id.payment);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                summary productfragment = new summary();
//                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
//                fragmentTransactionpro.replace(R.id.fragment, productfragment);
//                fragmentTransactionpro.commit();

                SharedPreferences preferences=getContext().getSharedPreferences(MY_PREFS_forcart, Context.MODE_PRIVATE);
                shopid=preferences.getString("shopincartid","");
                Toast.makeText(getContext(), "Ordering please wait", Toast.LENGTH_SHORT).show();
                getinfoapi();

            }
        });
//        checkBoxssb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//
//                    paymentmethodorder="Creditcard";
//                    paymentstatusorder="Paid by ssb";
//
//                    ssbprice = new ssbprice();
//                    FragmentManager fragmentManagerpro =getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
//                    fragmentTransactionpro.replace(R.id.ssb, ssbprice);
//                    fragmentTransactionpro.commit();
//                }
//                else {
//                    ssbprice = new ssbprice();
//                    FragmentManager fragmentManagerpro =getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
//                    fragmentTransactionpro.remove(ssbprice);
//                    fragmentTransactionpro.commit();
//                }
//            }
//        });

        checkBoxcredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    paymentmethodorder="Creditcard";
                    paymentstatusorder="Paid by creditcard";
//                    FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
//                    fragmentTransactionpro.add(R.id.credit, creditcardprice);
//                    fragmentTransactionpro.commit();
//                }
//                if (!isChecked){
//                    Fragment fragment =getChildFragmentManager().findFragmentById(R.id.credit);
//                    getChildFragmentManager().beginTransaction().remove(fragment).commit();
//                }

                    Intent intent=new Intent(getActivity(),checkoutpaymentstipe.class);
                    startActivity(intent);
                }

            }
        });
        cashondel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    paymentmethodorder="COD";
                    paymentstatusorder="COD";

                }
            }
        });
        return view;
    }

    public void getinfoapi() {
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + prefs.getString("ipv4", "10.0.2.2") + ":5000/orders/addorder/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        SharedPreferences editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE);
        lat=editor.getString("latitude","");
        lon=editor.getString("longitude", "");



        SharedPreferences preferences=getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        dbhandler dbhandler=new dbhandler(getContext());
        String tot=String.valueOf(dbhandler.totalprice());
        String dis=String.valueOf(dbhandler.totaldiscount());

        SharedPreferences pref2 = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE);
        String delcharge=pref2.getString("deliverycharges","nocharge");

        String grand=String.valueOf(Integer.valueOf(tot)+Integer.valueOf(delcharge));

        dbhandler dbhandler1=new dbhandler(getContext());
        String tax_actualprice=String.valueOf(dbhandler1.totalpricewithoutdiscount());

        orderinfoapi api = retrofit.create(orderinfoapi.class);
        Call<modelreturnoforderinfo> listCall = api.response(getArguments().getString("name"),preferences.getString("customerid",""),getArguments().getString("address"),getArguments().getString("phone"),tot,dis,delcharge,grand,shopid,tax_actualprice,Integer.parseInt(delcharge),lon,lat,paymentmethodorder,paymentstatusorder);
        dbhandler1.close();

        listCall.enqueue(new Callback<modelreturnoforderinfo>() {
            @Override
            public void onResponse(Call<modelreturnoforderinfo> call, Response<modelreturnoforderinfo> response) {
                if (response.isSuccessful()) {
                    orderid=response.body().get_id();
                    orderitems(orderid);

                }
            }
            @Override
            public void onFailure(Call<modelreturnoforderinfo> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void orderitems(String orderid){
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + prefs.getString("ipv4", "10.0.2.2") + ":5000/orders/addorderitem/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dbhandler dbhandler=new dbhandler(getContext());
        List<modelcart> list=dbhandler.retrievecart();
        dbhandler.close();

        orderitemapi api = retrofit.create(orderitemapi.class);

        for (int i=0;i<list.size();i++){
            d=i;
            Call<ResponseBody> listCall = api.response(orderid,list.get(i).getTitle(),list.get(i).getImage(),list.get(i).getQuantity(),list.get(i).getDiscounted(),list.get(i).getProid());
            listCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), "Ordered", Toast.LENGTH_SHORT).show();
                    }

                    if (response.isSuccessful() && d==list.size()-1){
                        summary productfragment = new summary();
                        FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                        fragmentTransactionpro.replace(R.id.fragment, productfragment);
                        fragmentTransactionpro.commit();

                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        }


    }
}
