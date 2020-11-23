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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.ssb.R;
import com.ali.ssb.dbhandler;
import com.ali.ssb.holderclasses.holderreclast;
import com.ali.ssb.Models.modellastrec;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link summary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class summary extends Fragment {

    TextView name,method,total;
    Button backto;
    List<modellastrec> list;
    holderreclast adapter;
    public static final String MY_PREFS_NAME = "mydetails";
    RecyclerView rec;
    ImageView back;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public summary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment summary.
     */
    // TODO: Rename and change types and number of parameters
    public static summary newInstance(String param1, String param2) {
        summary fragment = new summary();
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
        View view= inflater.inflate(R.layout.fragment_summary, container, false);


        total=view.findViewById(R.id.total);
        name=view.findViewById(R.id.name);
        method=view.findViewById(R.id.method);

        SharedPreferences sharedPreferences=getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        name.setText(sharedPreferences.getString("name","no name"));



        back=view.findViewById(R.id.back);
        backto=view.findViewById(R.id.backto);
        backto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDashboardFragment ssbpric = new mainDashboardFragment();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, ssbpric);
                fragmentTransactionpro.commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentpage pay = new paymentpage();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, pay);
                fragmentTransactionpro.commit();
            }
        });
        list=new ArrayList<>();
        dbhandler dbhandler=new dbhandler(getContext());
        list=dbhandler.retrievecartforsum();
        dbhandler.close();
        rec=view.findViewById(R.id.reclast);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapter=new holderreclast(list,getContext());
        rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }
}