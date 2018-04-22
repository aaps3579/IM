package com.example.hp_pc.im;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class verify extends AppCompatActivity {

    String PHONE,VERIFY_CODE,USERNAME;


    TextView tv4;
    EditText ed5;
    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setTheme(GlobalApp.theme);

        setContentView(R.layout.activity_verify);
        tv4=(TextView)findViewById(R.id.textView4);
        ed5=(EditText)findViewById(R.id.editText5);
        bt=(Button)findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=getIntent();
                PHONE=in.getStringExtra("phone");
                USERNAME=in.getStringExtra("username");
                VERIFY_CODE=ed5.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Socket sock = new Socket(GlobalApp.IP,GlobalApp.PORT);
                            DataInputStream dis = new DataInputStream(sock.getInputStream());
                            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                            dos.writeBytes("GET /IM/verify_status?phone="+PHONE+"&verification_code="+VERIFY_CODE+" HTTP/1.1\r\n");
                            dos.writeBytes("HOST: "+GlobalApp.IP+":"+GlobalApp.PORT+"\r\n");
                            dos.writeBytes("\r\n");
                            while (true)
                            {
                                String s = dis.readLine();
                                if (s == null || s == "") {
                                    break;
                                }
                                Log.d("MYMSG", s);
                            }
                            final String msg=dis.readLine();
                            runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(verify.this, msg + "", Toast.LENGTH_LONG).show();

                                        if(msg.equals("Code Match")) {

                                            GlobalApp.PHONE = PHONE;
                                            GlobalApp.USERNAME = USERNAME;
                                            try {

                                                FileOutputStream fos = openFileOutput("pass.txt", MODE_PRIVATE);
                                                DataOutputStream da = new DataOutputStream(fos);
                                                da.writeBytes(PHONE + "\r\n");
                                                da.writeBytes(USERNAME + "\r\n");
                                                da.writeBytes(GlobalApp.IP+"\r\n");
                                                FileOutputStream fOs = openFileOutput("ID.txt", MODE_PRIVATE);
                                                DataOutputStream ds = new DataOutputStream(fOs);
                                                ds.writeInt(0);
                                                Intent s=new Intent(verify.this,msg_service.class);
                                                startService(s);


                                                Intent in1 = new Intent(verify.this, main_chat.class);
                                                startActivity(in1);

                                                da.close();
                                            } catch (Exception ex) {

                                                ex.printStackTrace();
                                            }
                                        }


                                    }
                                });


                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }

                    }
                }).start();

            }
        });
    }

}
