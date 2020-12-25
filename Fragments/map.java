package com.ali.ssb.Fragments;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.ssb.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.ali.ssb.Fragments.paymentpage.MY_PREFS_forcart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link map#newInstance} factory method to
 * create an instance of this fragment.
 */
public class map extends Fragment implements OnMapReadyCallback, LocationListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String lat, lon;
    ImageView back;
    Button pick;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;

    Location mLocation;


    FusedLocationProviderClient fusedLocationProviderClient;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;

    private static final long MIN_TIME_BW_UPDATE = 1000 * 60 * 1;

    public map() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment map.
     */
    // TODO: Rename and change types and number of parameters
    public static map newInstance(String param1, String param2) {
        map fragment = new map();
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        back = view.findViewById(R.id.back);
        pick = view.findViewById(R.id.confirmloc);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkoutpage productfragment = new checkoutpage();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, productfragment);
                fragmentTransactionpro.commit();
            }
        });

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkoutpage productfragment = new checkoutpage();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, productfragment);
                fragmentTransactionpro.commit();

                SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE).edit();
                editor.putString("latitude", lat);
                editor.putString("longitude", lon);
                editor.apply();

                Toast.makeText(getContext(), "Location Picked", Toast.LENGTH_SHORT).show();

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(map.this);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.isMyLocationEnabled();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
            // TODO: Consider calling
//                ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                    LocationManager locationManager;
                    locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

                    if (location!=null && task.isSuccessful()) {

                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        markerOptions.position(latLng);

                        googleMap.addMarker(markerOptions);

                        LatLng coordinate = new LatLng(location.getLatitude(), location.getLongitude()); //Store these lat lng values somewhere. These should be constant.
                        CameraUpdate locations = CameraUpdateFactory.newLatLngZoom(
                                coordinate, 15);
                        googleMap.animateCamera(locations);

                        lat = String.valueOf(latLng.latitude);
                        lon = String.valueOf(latLng.longitude);
                    }
                    else {
                        Toast.makeText(getContext(), "PLease enable mobile location", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                googleMap.clear();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);

                Toast.makeText(getContext(), "Location Updated", Toast.LENGTH_SHORT).show();

                lat = String.valueOf(latLng.latitude);
                lon = String.valueOf(latLng.longitude);


                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return false;
                    }
                });

            }
        });
        
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}