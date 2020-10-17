package com.ali.ssb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.ssb.interfacesapi.apilogin;
import com.ali.ssb.interfacesapi.forgetpassapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class forgetpassword extends AppCompatActivity {

    ImageView back;
    CardView reset;
    String semail;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);


        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));



        email=findViewById(R.id.email);
        back=findViewById(R.id.back);
        reset=findViewById(R.id.reset);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(forgetpassword.this,loginpagecustomer.class);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                semail=email.getText().toString();

                SharedPreferences prefs =getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                forgetpassapi api=retrofit.create(forgetpassapi.class);
                Call<ResponseBody> listCall=api.forgetpassword(semail);
                listCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(forgetpassword.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(forgetpassword.this,loginpagecustomer.class);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(forgetpassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


    }
}