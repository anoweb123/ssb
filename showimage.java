package com.ali.ssb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class showimage extends AppCompatActivity {

    ImageView imageView;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);

        imageView=findViewById(R.id.img);

        s=getIntent().getExtras().getString("image");
        Picasso.get().load(s).networkPolicy(NetworkPolicy.NO_STORE).into(imageView);



    }
}