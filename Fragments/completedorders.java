package com.ali.ssb.Fragments;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.ssb.Models.modelpending;
import com.ali.ssb.R;
import com.ali.ssb.holderclasses.holdercompleted;
import com.ali.ssb.Models.modelcompleted;
import com.ali.ssb.holderclasses.holderpending;
import com.ali.ssb.interfacesapi.completedorderapi;
import com.ali.ssb.interfacesapi.pendingorderapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ali.ssb.Fragments.profilecustomer.MY_PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link completedorders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class completedorders extends Fragment implements holdercompleted.onitemsclicklistener {

    RecyclerView recyclerView;
    List<modelcompleted> list;
    holdercompleted adapter;

    ImageView back;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public completedorders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment completedorders.
     */
    // TODO: Rename and change types and number of parameters
    public static completedorders newInstance(String param1, String param2) {
        completedorders fragment = new completedorders();
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
        View view= inflater.inflate(R.layout.fragment_completedorders, container, false);



        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morefragment productfragment = new morefragment();
                FragmentManager fragmentManagerpro =getChildFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, productfragment);
                fragmentTransactionpro.commit();

            }
        });
        recyclerView=view.findViewById(R.id.rec);

        SharedPreferences prefss=getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);

        String userid=prefss.getString("customerid","");

        Retrofit retrofitpro = new Retrofit.Builder()
                .baseUrl("http://"+prefss.getString("ipv4","10.0.2.2")+":5000/orders/completedOrder/"+userid+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        completedorderapi apipro=retrofitpro.create(completedorderapi.class);
        Call<List<modelcompleted>> listCallpro=apipro.list();

        listCallpro.enqueue(new Callback<List<modelcompleted>>() {
            @Override
            public void onResponse(Call<List<modelcompleted>> call, Response<List<modelcompleted>> response) {
                if (response.isSuccessful()){
                    list=response.body();

                    adapter = new holdercompleted(list, getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.onitemsclicklistener(completedorders.this);

                }
            }

            @Override
            public void onFailure(Call<List<modelcompleted>> call, Throwable t) {
            }
        });
        return view;
    }

    @Override
    public void onshowitems(String id) {
        orderitems productfragment = new orderitems();
        FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
        fragmentTransactionpro.replace(R.id.fragment, productfragment);
        Bundle bundle=new Bundle();
        bundle.putString("orderid",id);
        productfragment.setArguments(bundle);
        fragmentTransactionpro.commit();

    }
}