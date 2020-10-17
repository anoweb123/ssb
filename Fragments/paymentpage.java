package com.ali.ssb.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ali.ssb.R;
import com.ali.ssb.creditcardprice;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link paymentpage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class paymentpage extends Fragment {
    ssbprice ssbprice;
    Button pay;
    creditcardprice creditcardprice;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CheckBox checkBoxssb,checkBoxcredit;

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
        checkBoxssb=view.findViewById(R.id.ssbcheck);
        checkBoxcredit=view.findViewById(R.id.creditcardcheck);

        pay=view.findViewById(R.id.payment);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                summary productfragment = new summary();
                FragmentManager fragmentManagerpro = getChildFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, productfragment);
                fragmentTransactionpro.commit();

            }
        });
        checkBoxssb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ssbprice = new ssbprice();
                    FragmentManager fragmentManagerpro = getChildFragmentManager();
                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                    fragmentTransactionpro.add(R.id.ssb, ssbprice);
                    fragmentTransactionpro.commit();

                }
                if (!isChecked){
                    Fragment fragment =getChildFragmentManager().findFragmentById(R.id.ssb);
                    getChildFragmentManager().beginTransaction().remove(fragment).commit();

                }
            }
        });

        checkBoxcredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    creditcardprice = new creditcardprice();
                    FragmentManager fragmentManagerpro = getChildFragmentManager();
                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                    fragmentTransactionpro.add(R.id.credit, creditcardprice);
                    fragmentTransactionpro.commit();
                }
                if (!isChecked){
                    Fragment fragment =getChildFragmentManager().findFragmentById(R.id.credit);
                    getChildFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        });
        return view;
    }
}