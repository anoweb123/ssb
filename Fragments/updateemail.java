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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ali.ssb.R;
import com.ali.ssb.interfacesapi.updateemailapi;
import com.ali.ssb.signuppage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * Use the {@link updateemail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class updateemail extends Fragment {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    CardView update;
    RelativeLayout frag;
    EditText pass,email;
    String spass,sid;
    ProgressBar bar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView back;
    public updateemail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment updateemail.
     */
    // TODO: Rename and change types and number of parameters
    public static updateemail newInstance(String param1, String param2) {
        updateemail fragment = new updateemail();
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
        View view=inflater.inflate(R.layout.fragment_updateemail, container, false);

        email=view.findViewById(R.id.email);
        pass=view.findViewById(R.id.pass);
        update=view.findViewById(R.id.update);
        bar=view.findViewById(R.id.progress);
        back=view.findViewById(R.id.back);
        frag=view.findViewById(R.id.fragment);

        bar.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilecustomer productfragment = new profilecustomer();
                FragmentManager fragmentManagerpro = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                fragmentTransactionpro.replace(R.id.fragment, productfragment);
                fragmentTransactionpro.commit();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                frag.setAlpha((float) 0.5);

                if (email.getText().toString().isEmpty()) {
                    email.setError("Enter Email");
                }
                if (pass.getText().toString().isEmpty()) {
                    pass.setError("Enter password");
                }
                if (email.getText().toString().isEmpty()||pass.getText().toString().isEmpty()){
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    frag.setAlpha((float) 1.0);

                }
                else {
                    Boolean ab=validate(email.getText().toString());
                    if (!ab){
                        Toast.makeText(getContext(), "Enter Valid email", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    bar.setVisibility(View.VISIBLE);

                    SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    sid = prefs.getString("customerid", "Null");
                    spass = prefs.getString("password", "Null");

                    if (pass.getText().toString().equals(spass)){

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/customer/updateprofile/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        updateemailapi api = retrofit.create(updateemailapi.class);
                        Call<ResponseBody> listCall = api.updateemail(sid, email.getText().toString());
                        listCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getContext(), "Email updated", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("email", email.getText().toString());
                                    editor.apply();

                                    email.setText("");
                                    pass.setText("");

                                    bar.setVisibility(View.INVISIBLE);
                                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    frag.setAlpha((float) 1.0);

                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });}
                    else {
                        bar.setVisibility(View.INVISIBLE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        frag.setAlpha((float) 1.0);

                        Toast.makeText(getContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                    }


                }
            }
        });


        return view;
    }
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}