package com.example.hp_pc.im;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class set_status extends AppCompatActivity {

    ImageButton imb;
    EditText ed,ed1;
    LinearLayout l;
    Button update,cancel;
    String phone=GlobalApp.PHONE,Status;
    int count=1;
    Toolbar tl;
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(GlobalApp.theme);

      /*  Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.addFlags(WindowManager.LayoutParams.ALPHA_CHANGED);
        window.setStatusBarColor(getResources().getColor(R.color.blue));*/
       // setTitle("Status");
      //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));

        setContentView(R.layout.activity_set_status);
        tl=(Toolbar) findViewById(R.id.toolbar4);
        /*tl.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        tl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        setSupportActionBar(tl);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ed=(EditText)findViewById(R.id.editText8);
        imb=(ImageButton)findViewById(R.id.imageButton);
         ed1=(EditText)findViewById(R.id.editText9);
        l=(LinearLayout)findViewById(R.id.lstatus);
        update=(Button)findViewById(R.id.button5);
        cancel=(Button)findViewById(R.id.button6);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket sock = new Socket(GlobalApp.IP, GlobalApp.PORT);
                    DataInputStream h = new DataInputStream(sock.getInputStream());
                    DataOutputStream v = new DataOutputStream(sock.getOutputStream());
                    v.writeBytes("GET /IM/get_status?phone=" + phone +  " HTTP/1.1\r\n");
                    v.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + " \r\n");
                    v.writeBytes("\r\n");
                        while (true) {
                            String s = h.readLine();
                            if (s == null || s == "") {
                                break;
                            }

                        }
                        final String msg = h.readLine();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ed.setText(msg);

                            }
                        });

                }
                catch(Exception x)
                {
                    x.printStackTrace();
                }


            }
        }).start();
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 1) {
                    ed1.setVisibility(View.VISIBLE);
                    l.setVisibility(View.VISIBLE);

                }
                if (count == 0) {
                    ed1.setVisibility(View.GONE);
                    l.setVisibility(View.GONE);
                    count = count + 2;
                }
                count--;
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Status=ed1.getText().toString();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Socket so = new Socket(GlobalApp.IP, GlobalApp.PORT);
                                DataInputStream p = new DataInputStream(so.getInputStream());
                                DataOutputStream s = new DataOutputStream(so.getOutputStream());
                                s.writeBytes("GET /IM/set_status?phone=" + phone + "&Status=" + Status + " HTTP/1.1\r\n");
                                s.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + " \r\n");
                                s.writeBytes("\r\n");
                                while (true) {
                                    String si = p.readLine();
                                    if (si == null || si == "") {
                                        break;
                                    }

                                }
                                final String msg = p.readLine();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ed1.setVisibility(View.GONE);
                                        l.setVisibility(View.GONE);

                                        ed.setText(msg);
                                        ed1.setText("");

                                    }
                                });
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }).start();

                }


        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ed1.setVisibility(View.GONE);
                l.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}
