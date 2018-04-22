package com.example.hp_pc.im;

import android.content.Intent;
import android.graphics.PointF;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.zxing.client.android.camera.open.OpenCameraManager;

public class QRReader extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener
{
    private QRCodeReaderView mydecoderview;

    private Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(GlobalApp.theme);

        setContentView(R.layout.activity_qrreader);
        sw=(Switch)findViewById(R.id.sw);
        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {


                }
                else
                {

                }

            }
        });
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {


        Intent in = new Intent();
        in.putExtra("text",text);

        setResult(RESULT_OK, in);

        finish();
    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }
}
