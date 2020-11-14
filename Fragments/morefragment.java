package com.ali.ssb.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ali.ssb.R;
import com.ali.ssb.loginpagecustomer;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link morefragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class morefragment extends Fragment {

    CircleImageView profileimage;
    ImageView option;
    String sname,semail,simage;

    RelativeLayout transactionhis,logout;
    TextView completed,pending,history,name,email;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static final String MY_PREFS_NAME = "mydetails";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public morefragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment morefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static morefragment newInstance(String param1, String param2) {
        morefragment fragment = new morefragment();
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
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_morefragment, container, false);
        option=view.findViewById(R.id.option);
        transactionhis=view.findViewById(R.id.transactionhis);
        completed=view.findViewById(R.id.completed);
        pending=view.findViewById(R.id.pending);
        logout=view.findViewById(R.id.logout);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        profileimage=view.findViewById(R.id.imageview_account_profile);


        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sname = prefs.getString("name", "Null");//"No name defined" is the default value.
        semail = prefs.getString("email", "Null");//"No name defined" is the default value.
        simage = prefs.getString("image", "no"); //0 is the default value.

        name.setText(sname);
        email.setText(semail);

        if (simage.equals("")||simage.equals("no")){

        }
        else {
            Picasso.get().load(simage).into(profileimage);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("loginstatus","false");
                editor.apply();

                startActivity(new Intent(getContext(), loginpagecustomer.class));
            }
        });

        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completedorders completedorders = new completedorders();
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.fragment, completedorders);
                fragmentTransaction1.commit();
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingorders pending =new pendingorders();
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.fragment,pending);
                fragmentTransaction1.commit();
            }
        });
        transactionhis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionhistory history =new transactionhistory();
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.fragment,history);
                fragmentTransaction1.commit();
            }
        });
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),option);
                popupMenu.getMenuInflater().inflate(R.menu.optionsetting, popupMenu.getMenu());
                popupMenu.show();
            }
        });
        return  view;
    }
}