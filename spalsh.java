package com.ali.ssb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class spalsh extends AppCompatActivity {

    CardView background;
    ImageView next;
    int a=0;
    String status;
    private Handler mWaitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        SharedPreferences.Editor editor =getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("ipv4","192.168.0.108");
        editor.apply();

//        status = prefs.getString("loginstatus", "Null");
//        if (status.equals("true")) {
//            startActivity(new Intent(spalsh.this, dashboardcustomer.class));
//        } else {
            View decorView = getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);


            findViewById(R.id.next)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            a = 1;
                            startActivity(new Intent(spalsh.this, splashscreen.class));
                            finish();

                        }
                    });
            mWaitHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //The following code will execute after the 5 seconds.
                    try {
                        if (a == 0) {
                            //Go to next page i.e, start the next activity.
                            Intent intent = new Intent(getApplicationContext(), splashscreen.class);
                            startActivity(intent);
                        }
                        //Let's Finish Splash Activity since we don't want to show this when user press back button.
                        finish();
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            }, 5000);
        }
//    }
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        status = prefs.getString("loginstatus", "Null");
        if (status.equals("true")) {
            a=1;
            startActivity(new Intent(spalsh.this, dashboardcustomer.class));
        }

    }
}
