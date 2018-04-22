package com.example.hp_pc.im;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class delete_acc extends AppCompatActivity {
    ProgressDialog pd1;
    EditText ed1;
    public void go(View v)
    {

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                try
                {
                    Socket sock = new Socket(GlobalApp.IP,GlobalApp.PORT);
                    DataInputStream dis = new DataInputStream(sock.getInputStream());
                    DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                    dos.writeBytes("GET /IM/verify_status?phone="+GlobalApp.PHONE+"&verification_code="+ed1.getText().toString()+" HTTP/1.1\r\n");
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
                    if(msg.equals("Code Match"))
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(delete_acc.this, "Good Bye  !", Toast.LENGTH_SHORT).show();

                            }
                        });
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    Intent in=new Intent();


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pd1  = new ProgressDialog(delete_acc.this);
                                            pd1.setIndeterminate(true);
                                            pd1.setMessage("Deleting Account");
                                            pd1.show();

                                        }
                                    });
                                    Socket sock = new Socket(GlobalApp.IP, GlobalApp.PORT);
                                    DataInputStream dis = new DataInputStream(sock.getInputStream());
                                    DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                                    dos.writeBytes("GET /IM/delete_acc?phone=" + GlobalApp.PHONE + " HTTP/1.1\r\n");
                                    dos.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + " \r\n");
                                    dos.writeBytes("\r\n");
                                    while (true) {
                                        String s = dis.readLine();
                                        if (s == null || s == "") {
                                            break;
                                        }

                                    }
                                    final String msg = dis.readLine();
                                    if(msg.equals("DELETE"))
                                    {
                                        deleteFile("ID.txt");
                                        deleteFile("pass.txt");
                                        SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
                                        db.delete("msgs",null,null);
                                        db.delete("contacts",null,null);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                pd1.dismiss();


                                            }
                                        });

                                        finishAffinity();

                                    }

                                }
                                catch(Exception ex)
                                {
                                    ex.printStackTrace();
                                }

                            }
                        }).start();

                    }
                    else
                    {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(delete_acc.this, "Sorry Wrong Code", Toast.LENGTH_SHORT).show();
                           }
                       });
                    }

                }catch(Exception e){
                    Log.d("namma",e.getMessage());
                }
            }
        }).start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(GlobalApp.theme);

        setContentView(R.layout.activity_delete_acc);
        ed1 = (EditText) findViewById(R.id.deled);


    }
}
