package com.ali.ssb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;



import com.ali.ssb.interfacesapi.apilogin;
import com.ali.ssb.interfacesapi.signupapi;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class signuppage extends AppCompatActivity {
ImageView back;
TextView signin;
CardView signup;
EditText name,email,password,conpass;
String sname,semail,spassword,sconpass,saddres,scell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.pass);
        conpass=findViewById(R.id.conpass);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));



        signin=findViewById(R.id.signin);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sname=name.getText().toString();
                semail=email.getText().toString();
                spassword=password.getText().toString();
                sconpass=conpass.getText().toString();
                saddres="";
                scell="";

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://"+prefs.getString("ipv4","10.0.2.2")+":5000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                signupapi api=retrofit.create(signupapi.class);
                Call<ResponseBody> listCall=api.responsesignup(semail,spassword,sname,saddres,scell);
                listCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Intent intent=new Intent(signuppage.this,loginpagecustomer.class);
                            startActivity(intent);
                            Toast.makeText(signuppage.this, "Account created", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(signuppage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(signuppage.this,loginpagecustomer.class);
                startActivity(intent);
            }


        });


    }
}
