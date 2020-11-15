package com.ali.ssb.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.ssb.Models.modelbaner;
import com.ali.ssb.Models.modelnotification;
import com.ali.ssb.R;
import com.ali.ssb.holderclasses.holdernoti;
import com.ali.ssb.holderclasses.sliderbanneradapter;
import com.ali.ssb.interfacesapi.banerapi;
import com.ali.ssb.interfacesapi.notiapi;
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

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notifications extends Fragment {


    List<modelnotification> list;
    holdernoti holdernoti;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public notifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notifications.
     */
    // TODO: Rename and change types and number of parameters
    public static notifications newInstance(String param1, String param2) {
        notifications fragment = new notifications();
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
        View view= inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView=view.findViewById(R.id.rec);

        SharedPreferences prefss = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        list = new ArrayList<>();
        Retrofit retrofitpro = new Retrofit.Builder()
                .baseUrl("http://"+prefss.getString("ipv4","10.0.2.2")+":5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notiapi apipro=retrofitpro.create(notiapi.class);
        Call<List<modelnotification>> listCallpro=apipro.list();

        listCallpro.enqueue(new Callback<List<modelnotification>>() {
            @Override
            public void onResponse(Call<List<modelnotification>> call, Response<List<modelnotification>> response) {
                if (response.isSuccessful()){
                    list=response.body();
                    recyclerView.hasFixedSize();
                    holdernoti=new holdernoti(list,getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(holdernoti);
                    holdernoti.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<modelnotification>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




        return view;
    }
}
