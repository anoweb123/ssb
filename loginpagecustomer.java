package com.ali.ssb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.ali.ssb.Models.getdatabyloginmodel;
import com.ali.ssb.interfacesapi.apilogin;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class loginpagecustomer extends AppCompatActivity {
TextView signup,forget;
CardView login;

EditText email,pass;
ProgressBar bar;
String semail,spass;
List<getdatabyloginmodel> list;
public static final String MY_PREFS_NAME = "mydetails";


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpagecustomer);

        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        bar=findViewById(R.id.bar);

        bar.setVisibility(View.INVISIBLE);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


//        Window window = getWindow();
//
//// clear FLAG_TRANSLUCENT_STATUS flag:
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//// finally change the color
//        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));



        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        forget=findViewById(R.id.forget);



        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginpagecustomer.this,forgetpassword.class);
                startActivity(intent);

            }


        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginpagecustomer.this,signuppage.class);
                startActivity(intent);

            }


        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                semail=email.getText().toString();
                spass=pass.getText().toString();


                SharedPreferences prefs =getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apilogin api=retrofit.create(apilogin.class);
                Call<getdatabyloginmodel> listCall=api.response(semail,spass);
                listCall.enqueue(new Callback<getdatabyloginmodel>() {
                    @Override
                    public void onResponse(@NotNull Call<getdatabyloginmodel> call, @NotNull Response<getdatabyloginmodel> response) {
                        if (response.isSuccessful()){

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                            editor.putString("name", response.body().getName());
                            editor.putString("email", response.body().getEmail());
                            editor.putString("password", response.body().getPassword());
                            editor.putString("customerid", response.body().get_id());
                            editor.putString("address", response.body().getAddress());
                            editor.putString("image", response.body().getAddress());
                            editor.putString("loginstatus","true");
                            editor.putString("image",response.body().getImage());
                            editor.putString("phone", response.body().getCell());
                            editor.putString("notification", "");

                            editor.apply();

                            Toast.makeText(loginpagecustomer.this, "Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(loginpagecustomer.this,dashboardcustomer.class);
                            startActivity(intent);
                            bar.setVisibility(View.INVISIBLE);
                        }
                        else {
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(loginpagecustomer.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<getdatabyloginmodel> call, Throwable t) {
                        Toast.makeText(loginpagecustomer.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
//                Call<ResponseBody> call= loginretrofitclass.getapi().response(semail,spass);
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Toast.makeText(loginpagecustomer.this, response.body().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    }
//                });
            }
        });
    }
    }
