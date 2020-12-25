package com.ali.ssb.Fragments;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
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
import com.ali.ssb.dashboardcustomer;
import com.ali.ssb.dbhandler;
import com.ali.ssb.holderclasses.holderreclast;
import com.ali.ssb.Models.modellastrec;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link summary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class summary extends Fragment {


    Button backto;
    TextView name,method,total;
    List<modellastrec> list;
    holderreclast adapter;
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

        View view= inflater.inflate(R.layout.fragment_summary, container, false);


        try {

            String CHannel_ID = "order";
            back = view.findViewById(R.id.back);
            backto = view.findViewById(R.id.backto);
            name = view.findViewById(R.id.name);
            method = view.findViewById(R.id.method);
            total = view.findViewById(R.id.total);
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
            list = new ArrayList<>();
            dbhandler dbhandler = new dbhandler(getContext());
            list = dbhandler.retrievecartforsum();
            dbhandler.close();
            rec = view.findViewById(R.id.reclast);
            rec.setHasFixedSize(true);
            rec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            adapter = new holderreclast(list, getContext());
            rec.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            name.setText(getArguments().getString("name", "name"));
            method.setText(getArguments().getString("method", "method"));
            total.setText("Rs "+getArguments().getString("total", "total"));

            dbhandler dbhandler1 = new dbhandler(getContext());
            dbhandler1.deleteallincart();
            dbhandler1.close();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(CHannel_ID, "personal", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("heloo");
                NotificationManager notificationManager=(NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            Intent mintent=new Intent(getContext(), dashboardcustomer.class);
            mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent=PendingIntent.getActivities(getActivity(),0, new Intent[]{mintent},PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(),CHannel_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setAutoCancel(true)
                    .setContentTitle("Order has been placed on SSB")
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentText("Thank you for ordering!");

            mBuilder.setContentIntent(pendingIntent);

            NotificationManager mNotificationManager = (NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(002, mBuilder.build());



        }
        catch (Exception e){

        }
        return view;
    }
}