package com.ali.ssb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;



import com.ali.ssb.interfacesapi.apilogin;
import com.ali.ssb.interfacesapi.signupapi;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
ProgressBar bar;
EditText name,email,password,conpass,cell,address;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_password =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).{7,}$", Pattern.CASE_INSENSITIVE);
String sname,semail,spassword,sconpass,saddres,scell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.pass);
        address=findViewById(R.id.address);
        cell=findViewById(R.id.cell);
        conpass=findViewById(R.id.conpass);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));


        bar=findViewById(R.id.bar);
        signin=findViewById(R.id.signin);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bar.setVisibility(View.VISIBLE);
                sname=name.getText().toString();
                semail=email.getText().toString();
                spassword=password.getText().toString();
                sconpass=conpass.getText().toString();
                saddres=address.getText().toString();
                scell=cell.getText().toString();

                if (sname.isEmpty()){
                    name.setError("Enter name");
                }
                if (semail.isEmpty()){
                    email.setError("Enter Email");
                }
                if (spassword.isEmpty()){
                    password.setError("Enter Password");
                }
                if (sconpass.isEmpty()){
                    conpass.setError("Re-enter Password");
                }
                if (saddres.isEmpty()){
                    address.setError("Enter Address");
                }

                if (scell.isEmpty()){
                    cell.setError("Enter Phone No");
                }

                if (scell.length()<11){
                    address.setError("Phone number must be of 11 digits");
                }

                if (scell.length()<11||sname.isEmpty()|| semail.isEmpty()||spassword.isEmpty()||sconpass.isEmpty()||saddres.isEmpty()||scell.isEmpty()){bar.setVisibility(View.INVISIBLE);}

                else {

                    Boolean a=validate(semail);
                    if (a){
                        if (spassword.equals(sconpass)){
                            if (spassword.length()>=7){
                               Boolean ab= validatepass(spassword);
                               if (ab){
                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://" + prefs.getString("ipv4", "10.0.2.2") + ":5000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    signupapi api = retrofit.create(signupapi.class);
                    Call<ResponseBody> listCall = api.responsesignup(semail, spassword, sname, saddres, scell);
                    listCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(signuppage.this, loginpagecustomer.class);
                                startActivity(intent);
                                bar.setVisibility(View.INVISIBLE);
                                Toast.makeText(signuppage.this, "Account created", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(signuppage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                               }
                               else {
                                   Toast.makeText(signuppage.this, "Password should contain 1 digit,1 uppercase,1 lowercase and 1 special character", Toast.LENGTH_SHORT).show();
                               }
                            }
                            else {
                                Toast.makeText(signuppage.this, "Password lenght cannot be less than 7", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(signuppage.this, "Both Passwords must match", Toast.LENGTH_SHORT).show();
                        }
                }
                    else {
                        Toast.makeText(signuppage.this, "Enter Valid email", Toast.LENGTH_SHORT).show();
                    }
                }

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
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatepass(String pass) {
        Matcher matcher = VALID_password.matcher(pass);
        return matcher.find();
    }
}
