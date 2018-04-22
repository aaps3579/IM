package com.example.hp_pc.im;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class login extends AppCompatActivity {

    Button bt,btforgot;
    EditText et,et2;
    String phone,verify_code;
    Toolbar tl1;
    TextInputLayout til4,til5;
    LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(GlobalApp.theme);

        setContentView(R.layout.activity_login);
        linear=(LinearLayout)findViewById(R.id.linear);
        bt=(Button)findViewById(R.id.button4);
        btforgot=(Button)findViewById(R.id.button9);
        et=(EditText)findViewById(R.id.editText6);
        et2=(EditText)findViewById(R.id.editText7);
        tl1=(Toolbar)findViewById(R.id.toolbar1);
        til4=(TextInputLayout)findViewById(R.id.til4);
        til5=(TextInputLayout)findViewById(R.id.til5);
        et.addTextChangedListener(new mytextwatcher(et));
        et2.addTextChangedListener(new mytextwatcher(et2));
        setSupportActionBar(tl1);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        try
                        {
                            if(et.getText().toString().length()!=10)
                            {
                                Snackbar.make(linear, "10 digit Number Only", Snackbar.LENGTH_SHORT).show();
                            }
                            else {
                                phone = et.getText().toString();
                                Socket sock = new Socket(GlobalApp.IP, GlobalApp.PORT);
                                DataInputStream dis = new DataInputStream(sock.getInputStream());
                                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                                dos.writeBytes("GET /IM/forgot_code?phone=" + phone + " HTTP/1.1\r\n");
                                dos.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + " \r\n");
                                dos.writeBytes("\r\n");
                                while (true) {
                                    String s = dis.readLine();
                                    if (s == null || s == "") {
                                        break;
                                    }

                                }
                                final String msg = dis.readLine();
                                if (msg.equals("OK")) {
                                    Snackbar.make(linear, "You Will Receive Email and Message Shortly", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Snackbar.make(linear, "You Are Not Registered", Snackbar.LENGTH_LONG).show();
                                }


                                dos.close();
                                dis.close();
                                sock.close();
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }

                    }
                }).start();



            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitform();

            }
        });
    }


    class mytextwatcher implements TextWatcher
    {

        private View view;
        mytextwatcher(View view)
        {
            this.view=view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            switch(view.getId()) {

                case R.id.editText6:
                    validatePhone();
                    break;
                case R.id.editText7:
                    validateCode();
                    break;
            }

        }
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
    private void submitform()
    {
        if(!validatePhone()) {
                return;
            }

        if (!validateCode()) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                /*if (et.getText().toString().equals("") || et2.getText().toString().equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this, "Fill All Fields", Toast.LENGTH_SHORT);
                        }
                    });
                } else if (et.getText().toString().length() != 10) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this, "Mobile  Number Must be Of 10 Digits", Toast.LENGTH_SHORT);
                        }
                    });

                } else if (et2.getText().toString().length() != 4) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this, "Code Must Be Of 4 Digits", Toast.LENGTH_SHORT);
                        }
                    });
                } else*/ {
                    try {
                        phone = et.getText().toString();
                        verify_code = et2.getText().toString();

                        Socket sock = new Socket(GlobalApp.IP, GlobalApp.PORT);
                        DataInputStream dis = new DataInputStream(sock.getInputStream());
                        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                        dos.writeBytes("GET /IM/login?phone=" + phone + "&verification_code=" + verify_code + " HTTP/1.1\r\n");
                        dos.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + " \r\n");
                        dos.writeBytes("\r\n");
                        while (true) {
                            String s = dis.readLine();
                            if (s == null || s == "") {
                                break;
                            }

                        }
                        final String msg = dis.readLine();

                        //Log.d("LOGIN", msg);

                        if (msg.equalsIgnoreCase("Login Success")) {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                                        Snackbar.make(linear,msg,Snackbar.LENGTH_LONG).show();
                                    }
                                });
                                GlobalApp.PHONE = phone;
                                String l = dis.readLine();
                                GlobalApp.USERNAME = l;
                                FileOutputStream xf = openFileOutput("pass.txt", MODE_PRIVATE);
                                DataOutputStream xd = new DataOutputStream(xf);
                                xd.writeBytes(phone + "\r\n");
                                xd.writeBytes(l + "\r\n");
                                xd.writeBytes(GlobalApp.IP + "\r\n");
                                Log.d("File", phone + ";" + l + ";" + GlobalApp.IP);
                                FileOutputStream fos = openFileOutput("ID.txt", MODE_PRIVATE);
                                DataOutputStream ds = new DataOutputStream(fos);
                                ds.writeInt(0);
                                Intent s = new Intent(login.this, msg_service.class);
                                startService(s);
                                Intent t = new Intent(login.this, main_chat.class);
                                Log.d("IF", msg);
                                startActivity(t);


                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }


                        } else {
                            if (msg.equals("Wrong Credentials"))
                            {
                                Snackbar.make(linear,"Wrong Credentials",Snackbar.LENGTH_LONG).show();
                            }

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }


            }
        }).start();


    }
    private boolean validatePhone()
    {
        if(et.getText().toString().isEmpty()||et.getText().toString().length()!=10)
        {
            til4.setError("10 Digits Only");
            requestFocus(et);
            return false;
        }
        else
        {
            til4.setErrorEnabled(false);

        }
        return true;
    }
    private boolean validateCode()
    {
        if(et2.getText().toString().isEmpty()||et2.getText().toString().length()!=4)
        {
            til5.setError("4 digits Only");
            requestFocus(et2);
            return false;
        }
        else
        {
            til5.setErrorEnabled(false);

        }
        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
