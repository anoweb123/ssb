package com.ali.ssb.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ali.ssb.R;
import com.ali.ssb.checkoutpaymentstipe;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.Fragments.paymentpage.MY_PREFS_forcart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link checkoutpage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class checkoutpage extends Fragment{

    GoogleMap mMap;
    LinearLayout lin;
    public static final String MY_PREFS_NAME = "mydetails";
    EditText name,email,address,city,postal,phone;
    String sname,semail,saddress,scity,spostl,sphone;
    String rname,remail,raddress,rcity,rpostl,rphone;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button payment,pick;
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



        lin=view.findViewById(R.id.maplin);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        address=view.findViewById(R.id.address);
        city=view.findViewById(R.id.city);
        postal=view.findViewById(R.id.postal);
        phone=view.findViewById(R.id.phone);



        pick=view.findViewById(R.id.pick);

        SharedPreferences preferences=getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        sname=preferences.getString("name","");
        semail=preferences.getString("email","");
        sphone=preferences.getString("phone","");
        saddress=preferences.getString("address","");
        scity="";
        spostl="";


        name.setText(sname);
        email.setText(semail);
        phone.setText(sphone);
        address.setText(saddress);
        city.setText("");
        postal.setText("");

        SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE).edit();
        editor.putString("latitude", "11.0");
        editor.putString("longitude", "11.0");
        editor.apply();


        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getActivity());
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        map productfragment = new map();
                        FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                        fragmentTransactionpro.replace(R.id.fragment, productfragment);
                        fragmentTransactionpro.commit();

//                        lin.setVisibility(View.INVISIBLE);
//                        SupportMapFragment mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.googlemap);
//                        mapFragment.getMapAsync(checkoutpage.this);


//                        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                            @Override
//                            public void onSuccess(Location location) {
//                                if (location!=null){
//                                    Toast.makeText(getContext(), String.valueOf(location.getAltitude()), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                    }
                    else { ActivityCompat.requestPermissions(getActivity(),
                            new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
            }
        });
        payment=view.findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences preferences=getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
                sname=name.getText().toString();
                semail=email.getText().toString();
                sphone=phone.getText().toString();
                saddress=address.getText().toString();
                scity=city.getText().toString();
                spostl=postal.getText().toString();

                if (sname.isEmpty()){
                    name.setError("Enter name");
                }
                if (semail.isEmpty()){
                    email.setError("Enter email");
                }
                if (saddress.isEmpty()){
                    address.setError("Enter address");
                }
                if (sphone.isEmpty()){
                    phone.setError("Enter phone");
                }
                if (scity.isEmpty()){
                    city.setError("Enter city");
                }
                if (spostl.isEmpty()){
                    postal.setError("Enter postal code");
                }
                if (sname.isEmpty()||semail.isEmpty()||sphone.isEmpty()||saddress.isEmpty()||scity.isEmpty()||spostl.isEmpty()){}
                else {

                    paymentpage productfragment = new paymentpage();
                    FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                    fragmentTransactionpro.replace(R.id.fragment, productfragment);
                    Bundle bundle=new Bundle();
                    bundle.putString("name",sname);
                    bundle.putString("address",saddress);
                    bundle.putString("city",scity);
                    bundle.putString("pastal",spostl);
                    bundle.putString("phone",sphone);
                    productfragment.setArguments(bundle);
                    fragmentTransactionpro.commit();
                }
            }
        });

        return view;
    }
}