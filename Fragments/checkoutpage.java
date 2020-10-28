package com.ali.ssb.Fragments;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
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
import android.widget.Toast;

import com.ali.ssb.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.bouncycastle.jcajce.provider.symmetric.Poly1305;

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

        name.setText(sname);
        email.setText(semail);
        phone.setText(sphone);
        address.setText(saddress);


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