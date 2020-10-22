package com.ali.ssb.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ali.ssb.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link checkoutpage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class checkoutpage extends Fragment {

    public static final String MY_PREFS_NAME = "mydetails";
    EditText name,email,address,city,postal,phone;
    String sname,semail,saddress,scity,spostl,sphone;
    String rname,remail,raddress,rcity,rpostl,rphone;
    Button payment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public checkoutpage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment checkoutpage.
     */
    // TODO: Rename and change types and number of parameters
    public static checkoutpage newInstance(String param1, String param2) {
        checkoutpage fragment = new checkoutpage();
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
        View view= inflater.inflate(R.layout.fragment_checkoutpage, container, false);

        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        address=view.findViewById(R.id.address);
        city=view.findViewById(R.id.city);
        postal=view.findViewById(R.id.postal);
        phone=view.findViewById(R.id.phone);

        SharedPreferences preferences=getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        sname=preferences.getString("name","");
        semail=preferences.getString("email","");
        sphone=preferences.getString("phone","");
        saddress=preferences.getString("address","");

        name.setText(sname);
        email.setText(semail);
        phone.setText(sphone);
        address.setText(saddress);

        payment=view.findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentpage productfragment = new paymentpage();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, productfragment);
                fragmentTransactionpro.commit();

            }
        });


        return view;
    }
}