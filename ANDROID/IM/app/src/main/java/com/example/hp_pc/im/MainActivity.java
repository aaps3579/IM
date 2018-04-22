package com.example.hp_pc.im;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText ed,ed2,ed3,ed4;
    Button bt,bt2;
    RadioButton rb1,rb2;
    TextView tv;
    TextInputLayout til,til1,til2,til3;
    String phone,username,email,name,gender,verification_code;
    LinearLayout linearLayout;
    Toolbar tl;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTheme(GlobalApp.theme);

        setContentView(R.layout.activity_main);
        SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS msgs(msgid number primary key,msg varchar,msgfrom varchar,msgto varchar,datetime varchar,type varchar,status varchar )");
        db.execSQL("CREATE TABLE IF NOT EXISTS contacts(phone number primary key,name varchar)");

        Log.d("PATH", "in oncreate");
        File p = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Images");
        if (!p.exists()) {
            p.mkdirs();
            Log.d("PATH",p.getPath());
        }
        File p1 = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Videos");
        if (!p1.exists()) {
            p1.mkdirs();
            Log.d("PATH",p1.getPath());
        }
        File p2 = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Audio");
        if (!p2.exists()) {
            p2.mkdirs();
            Log.d("PATH",p2.getPath());
        }
        File p3 = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Documents");
        if (!p3.exists()) {
            p3.mkdirs();
            Log.d("PATH",p3.getPath());
        }
        File f=getFileStreamPath("pass.txt");

        if(f.exists())
        {

            try {
                FileInputStream fis = openFileInput("pass.txt");
                DataInputStream di=new DataInputStream(fis);
                GlobalApp.PHONE=di.readLine().toString();
                GlobalApp.USERNAME=di.readLine().toString();
                Log.d("File",GlobalApp.PHONE+","+GlobalApp.USERNAME);
                Intent in2=new Intent(MainActivity.this,main_chat.class);
                startActivity(in2);

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
        tl=(Toolbar)findViewById(R.id.toolbar);
        ed=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);
        ed3=(EditText)findViewById(R.id.editText3);
        bt=(Button)findViewById(R.id.button);
        bt2=(Button)findViewById(R.id.button3);
        rb1=(RadioButton)findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        til=(TextInputLayout)findViewById(R.id.til);
        til1=(TextInputLayout)findViewById(R.id.til1);
        til2=(TextInputLayout)findViewById(R.id.til2);
        ed.addTextChangedListener(new mywatcher(ed));
        ed2.addTextChangedListener(new mywatcher(ed2));
        ed3.addTextChangedListener(new mywatcher(ed3));

        linearLayout=(LinearLayout)findViewById(R.id.ll);
        setSupportActionBar(tl);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        bt2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Snackbar.make(linearLayout,"Login",Snackbar.LENGTH_SHORT).show();
                Intent n=new Intent(MainActivity.this,login.class);
                startActivity(n);
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();

            }
        });
    }
    class mywatcher implements TextWatcher
    {
        private View view;
        mywatcher(View view)
        {
            this.view=view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch(view.getId())
            {
                case R.id.editText:
                    validatePhone();
                    break;
                case R.id.editText2:
                    validateName();
                    break;
                case R.id.editText3:
                    validateEmail();
                    break;

            }


        }
    }

    private void submitForm() {

        if(!validatePhone()) {
            return;
        }

        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }


        phone = ed.getText().toString();
        username = ed2.getText().toString();
        email = ed3.getText().toString();

        if (rb1.isChecked() == true) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        final ProgressDialog pd=new ProgressDialog(this);
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               pd.setMessage("Processing");
               pd.setIndeterminate(true);
               pd.show();

           }
       });


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Socket sock = new Socket(GlobalApp.IP, GlobalApp.PORT);
                    DataInputStream dis = new DataInputStream(sock.getInputStream());
                    DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                    String res ="/IM/signup?phone=" + phone + "&username=" + username + "&email=" + email + "&gender=" + gender + "&name=" + name ;
                            String actualchar[] = {"%", "<", ">", "~", ".", "\"",
                            "{", "}", "|", "\\", "-", "\'", "_", "^", " ",
                            "\n"};
                    String encchar[] = {"%25", "%3C", "%3E", "%7E", "%2E",
                            "%22", "%7B", "%7D", "%7C", "%5C", "%2D",
                            "%60", "%5F", "%5E", "%20", "%0A"};
                    Log.d("MYMSG", "connection sent");

                    for (int i = 0; i < actualchar.length; i++) {
                        res = res.replace(actualchar[i], encchar[i]);
                    }
                    dos.writeBytes("GET " + res + " HTTP/1.1\r\n");
                    dos.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + " \r\n");
                    dos.writeBytes("\r\n");
                    while (true) {
                        String s = dis.readLine();
                        if (s == null || s == "") {
                            break;
                        }
                        Log.d("MYMSG", s);
                    }
                    final String msg = dis.readLine();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                pd.dismiss();
                            if (msg.equals("Sucessfull")) {
                                Snackbar.make(linearLayout,"Login Successful", Snackbar.LENGTH_SHORT).show();

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Intent in = new Intent(MainActivity.this, verify.class);
                                in.putExtra("phone", phone);
                                in.putExtra("username", username);
                                startActivity(in);

                            } else {

                                Snackbar.make(linearLayout, "User Already Exists", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

                    sock.close();
                    dos.close();
                    dis.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private boolean validatePhone()
    {
        if(ed.getText().toString().isEmpty()||ed.getText().toString().length()!=10)
        {
            til.setError("10 Digits Only");
            requestFocus(ed);
            return false;
        }else {
            til.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateName() {
        if (ed2.getText().toString().trim().isEmpty()||ed2.getText().toString().length()<4) {
            til1.setError("More Than 4 Characters");
            requestFocus(ed2);
            return false;
        } else {
            til1.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = ed3.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            til2.setError("Inalid Email");
            requestFocus(ed3);
            return false;
        } else {
            til2.setErrorEnabled(false);
        }

        return true;
    }



    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}