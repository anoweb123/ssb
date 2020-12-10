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
import com.ali.ssb.interfacesapi.updatepasswordapi;
import com.ali.ssb.interfacesapi.updatephoneapi;
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
import static com.ali.ssb.signuppage.VALID_EMAIL_ADDRESS_REGEX;
import static com.ali.ssb.signuppage.validatepass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link updatepassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class updatepassword extends Fragment {


    public static final Pattern VALID_password =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).{7,}$", Pattern.CASE_INSENSITIVE);
    RelativeLayout frag;
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
        frag=view.findViewById(R.id.frag);
        conpass=view.findViewById(R.id.confirmpass);
        update=view.findViewById(R.id.update);
        bar=view.findViewById(R.id.progress);

        back=view.findViewById(R.id.back);
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


        bar.setVisibility(View.GONE);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                frag.setAlpha((float) 0.5);

                if (prepass.getText().toString().isEmpty()) {
                    prepass.setError("Enter Current Password");
                }

                if (conpass.getText().toString().isEmpty()) {
                    conpass.setError("Enter new password again");
                }
                if (newpass.getText().toString().isEmpty()) {
                    newpass.setError("Enter new password");
                }
                if (conpass.getText().toString().isEmpty() || newpass.getText().toString().isEmpty() || prepass.getText().toString().isEmpty()){

                    bar.setVisibility(View.INVISIBLE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    frag.setAlpha((float) 1.0);

                }
                else {

                    Boolean ad=validatepass(newpass.getText().toString());
                    if (!ad){
                        Toast.makeText(getContext(), "Password should contain 1 digit,1 uppercase,1 lowercase and 1 special character", Toast.LENGTH_SHORT).show();
                    }
                    else {


                        bar.setVisibility(View.VISIBLE);

                        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        sid = prefs.getString("customerid", "Null");
                        sprepass = prefs.getString("password", "Null");

                        if (prepass.getText().toString().equals(sprepass) && newpass.getText().toString().equals(conpass.getText().toString())) {

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://" + prefs.getString("ipv4", "10.0.2.2") + ":5000/customer/updateprofile/")
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

                                        bar.setVisibility(View.INVISIBLE);
                                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        frag.setAlpha((float) 1.0);

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {

                            bar.setVisibility(View.INVISIBLE);
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            frag.setAlpha((float) 1.0);
                            Toast.makeText(getContext(), "Both passwords not match", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });


        return view;
    }
    public static boolean validatepass(String pass) {
        Matcher matcher = VALID_password.matcher(pass);
        return matcher.find();
    }

}