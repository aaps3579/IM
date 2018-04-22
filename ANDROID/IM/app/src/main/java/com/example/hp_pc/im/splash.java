package com.example.hp_pc.im;

import android.animation.Animator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class splash extends AppCompatActivity{
    Animation animation;
    ImageView imv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File f1=getFileStreamPath("color.txt");
        if(f1.exists())
        {
            try
            {
                FileInputStream fs = openFileInput("color.txt");
                DataInputStream ds=new DataInputStream(fs);
                GlobalApp.theme=ds.readInt();


            }
            catch(Exception ex)
            {

            }
        }
        else
        {
            GlobalApp.theme=R.style.MyMaterialTheme;
        }
        setTheme(GlobalApp.theme);
        setContentView(R.layout.activity_splash);
        imv=(ImageView) findViewById(R.id.imv);

        imv.setAlpha(0.0f);
        imv.setVisibility(View.VISIBLE);
        imv.animate().alpha(1.0f).setDuration(3000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        startActivity(new Intent(splash.this,MainActivity.class));
                        finishAffinity();


                    }
                }).start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }


}
