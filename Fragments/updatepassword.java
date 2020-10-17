package com.ali.ssb.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ali.ssb.R;
import com.ali.ssb.interfacesapi.updatepasswordapi;
import com.ali.ssb.interfacesapi.updatephoneapi;

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
 * Use the {@link updatepassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class updatepassword extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CardView update;
    EditText prepass,newpass,conpass;
    ProgressBar bar;
    String sid,sprepass;
    ImageView back;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public updatepassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment updatepassword.
     */
    // TODO: Rename and change types and number of parameters
    public static updatepassword newInstance(String param1, String param2) {
        updatepassword fragment = new updatepassword();
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
        View view= inflater.inflate(R.layout.fragment_updatepassword, container, false);
        prepass=view.findViewById(R.id.curpass);
        newpass=view.findViewById(R.id.newpass);
        conpass=view.findViewById(R.id.confirmpass);
        update=view.findViewById(R.id.update);
        bar=view.findViewById(R.id.progress);

        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilecustomer productfragment = new profilecustomer();
                FragmentManager fragmentManagerpro = getChildFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, productfragment);
                fragmentTransactionpro.commit();

            }
        });


        bar.setVisibility(View.GONE);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prepass.getText().toString().isEmpty()) {
                    prepass.setError("Enter Current Password");
                }

                if (conpass.getText().toString().isEmpty()) {
                    conpass.setError("Enter new password again");
                }
                if (newpass.getText().toString().isEmpty()) {
                    newpass.setError("Enter new password");
                }
                if (conpass.getText().toString().isEmpty() || newpass.getText().toString().isEmpty() || prepass.getText().toString().isEmpty()){}
                else {
                    bar.setVisibility(View.VISIBLE);

                    SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    sid = prefs.getString("customerid", "Null");
                    sprepass = prefs.getString("password", "Null");

                    if (prepass.getText().toString().equals(sprepass) && newpass.getText().toString().equals(conpass.getText().toString())){

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/customer/updateprofile/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        updatepasswordapi api = retrofit.create(updatepasswordapi.class);
                        Call<ResponseBody> listCall = api.updatepassword(sid, newpass.getText().toString());
                        listCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getContext(), "Password updated", Toast.LENGTH_SHORT).show();

                                    SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("password", newpass.getText().toString());
                                    editor.apply();

                                    prepass.setText("");
                                    conpass.setText("");
                                    newpass.setText("");

                                    bar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });}
                    else {
                        bar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        return view;
    }
}