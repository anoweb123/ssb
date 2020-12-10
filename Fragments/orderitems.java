package com.ali.ssb.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.ali.ssb.Models.modelorderitems;
import com.ali.ssb.R;
import com.ali.ssb.holderclasses.adaperslider;
import com.ali.ssb.holderclasses.holdercompleted;
import com.ali.ssb.holderclasses.holderorderitemsincom;
import com.ali.ssb.interfacesapi.orderitemsapi;
import com.ali.ssb.interfacesapi.updateemailapi;

import java.util.ArrayList;
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
 * Use the {@link orderitems#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderitems extends Fragment {

   String Orderid;
   List<modelorderitems> list;
    RecyclerView recyclerView;
    holderorderitemsincom adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public orderitems() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderitems.
     */
    // TODO: Rename and change types and number of parameters
    public static orderitems newInstance(String param1, String param2) {
        orderitems fragment = new orderitems();
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
        View view= inflater.inflate(R.layout.fragment_orderitems, container, false);

        SharedPreferences prefs=getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        Orderid=getArguments().getString("orderid","");

        recyclerView=view.findViewById(R.id.rec);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/delivery/orderItems/"+Orderid+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        orderitemsapi api = retrofit.create(orderitemsapi.class);
        Call<List<modelorderitems>> listCall = api.list();

        listCall.enqueue(new Callback<List<modelorderitems>>() {
            @Override
            public void onResponse(Call<List<modelorderitems>> call, Response<List<modelorderitems>> response) {
                Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()){
                    list=response.body();

                    adapter = new holderorderitemsincom(list, getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<modelorderitems>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return  view;
    }

}