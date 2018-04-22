package com.example.hp_pc.im;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class imageview extends AppCompatActivity {

    ImageView imv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        imv=(ImageView) findViewById(R.id.imagebig);

        imv.setImageBitmap(GlobalApp.bbig);
    }
}
