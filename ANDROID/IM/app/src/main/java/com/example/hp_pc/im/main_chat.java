package com.example.hp_pc.im;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class main_chat extends AppCompatActivity {
    ViewPager vp;
    String cn="";
    Toolbar toolbar;
    TabLayout tabs;
    ProgressDialog pd;
    String phoneNo="";
    SQLiteDatabase db;
    TextView tv;
    CoordinatorLayout cordinator;
    String allcontacts="";
    ProgressDialog pd1;
    @Override


    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setTheme(GlobalApp.theme);
        GlobalApp.myal.clear();
        new MyThread1().start();
        setContentView(R.layout.activity_main_chat);
        cordinator=(CoordinatorLayout) findViewById(R.id.cordinator);
        vp = (ViewPager) findViewById(R.id.vp);
        toolbar=(Toolbar)findViewById(R.id.toolbar2);
        tv=(TextView) findViewById(R.id.toolbar_title);
        tv.setText("Welcome "+GlobalApp.USERNAME);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.chat_home);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        tabs=(TabLayout)findViewById(R.id.tabs);
        setupViewPager(vp);
        tabs.setupWithViewPager(vp);


    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new chats(), "Chats");
        adapter.addFragment(new contacts(), "Favourites");
        adapter.addFragment(new all_contacts(), "Contacts");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private class MyThread1 extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pd = new ProgressDialog(main_chat.this, ProgressDialog.STYLE_SPINNER);
                    pd.setMessage("Loading...Please Wait ");
                    pd.setIcon(R.drawable.upload);

                    pd.setCancelable(false);
                    pd.show();

                }
            });

            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

            if (cur.getCount() > 0) {

                while (cur.moveToNext()) {
                    final String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    final String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneNo = phoneNo.replaceAll(" ", "");
                            phoneNo = phoneNo.replaceAll("-", "");
                            phoneNo = phoneNo.replaceAll("\\(", "");
                            phoneNo = phoneNo.replaceAll("\\)", "");
                            phoneNo = phoneNo.replaceAll("\\+91", "");
                            if (!cn.contains(phoneNo)) {
                                cn = cn + phoneNo + ";";

                                        GlobalApp.al.add(new contact(name, phoneNo));


                                    }

                            
                        }
                        pCur.close();
                    }
                }
            }


                try
                {

                Socket co = new Socket(GlobalApp.IP, GlobalApp.PORT);
                DataInputStream is = new DataInputStream(co.getInputStream());
                DataOutputStream o = new DataOutputStream(co.getOutputStream());
                o.writeBytes("POST /IM/fetch_contacts HTTP/1.1\r\n");
                o.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + "\r\n");
                o.writeBytes("Content-Length: " +cn.length()  + "\r\n");
                o.writeBytes("Content-Type: text/plain\r\n");
                o.writeBytes("\r\n");
                o.writeBytes(cn+"\r\n");
                while (true) {
                    String s = is.readLine();
                    if (s == null || s == "") {
                        Log.d("RESP",s);
                        break;
                    }
                }
                String result=is.readLine();
                Log.d("RESPONSE",result);
                    StringTokenizer res=new StringTokenizer(result,"`");
                    while(res.hasMoreTokens())
                    {
                        String s1=res.nextToken();
                        StringTokenizer s2=new StringTokenizer(s1,"~");
                        Log.d("MYMSG",s1);
                        while(s2.hasMoreTokens()) {

                            String phone = s2.nextToken();
                            String status = s2.nextToken();
                            if(!phone.equals(GlobalApp.PHONE))
                            {

                            for (int x = 0; x < GlobalApp.al.size(); x++) {


                                if (GlobalApp.al.get(x).PHONE.contains(phone)) {
                                    String name = GlobalApp.al.get(x).NAME;
                                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.hello);
                                    GlobalApp.myal.add(new mycontacts(name, phone, status, bmp));
                                    Log.d("RESULT", name + "," + phone + "," + status + "," + bmp);
                                    break;
                                }

                            }
                            }

                        }
                    }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            db=openOrCreateDatabase("MyDB",MODE_PRIVATE,null);

            for(int i=0;i<GlobalApp.myal.size();i++) {


                Cursor c=db.rawQuery("select * from contacts where phone='"+GlobalApp.myal.get(i).PHONE+"'",null);
                if(c.moveToNext())
                {}
                else
                {
                    String query = "INSERT INTO contacts values(" + GlobalApp.myal.get(i).PHONE + ",'" + GlobalApp.myal.get(i).NAME + "')";

                    db.execSQL(query);
                    Log.d("INSERT", "inserted" + GlobalApp.myal.get(i).PHONE + "," + GlobalApp.myal.get(i).NAME);
                }
            }
            db.close();
            GlobalApp.flag=true;
            Intent broadcast=new Intent();
            broadcast.setAction("complete");
            sendBroadcast(broadcast);

            Intent broadcast2=new Intent();
            broadcast2.setAction("test");
            sendBroadcast(broadcast2);

            pd.dismiss();


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.status)
        {
            Intent in=new Intent(main_chat.this,set_status.class);
            startActivity(in);
        }
        else if(item.getItemId()==R.id.ProfilePic)
        {
            Intent in=new Intent(main_chat.this,set_profilepic.class);
            startActivity(in);

        }
        else if(item.getItemId()==R.id.WEB)
        {
            Intent in =new Intent(main_chat.this,QRReader.class);
            startActivityForResult(in, 109);

        }
        else
            if(item.getItemId()==R.id.Theme)
            {
                startActivity(new Intent(getApplicationContext(),color_chooser.class));
            }
        else if(item.getItemId()==R.id.logout)
        {
            deleteFile("ID.txt");
            deleteFile("pass.txt");
            SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
            db.delete("msgs", null, null);
            db.delete("contacts", null, null);

            startActivity(new Intent(main_chat.this, splash.class));
            finishAffinity();
        }
        else
        if(item.getItemId()==R.id.delete)
        {
            Intent in=new Intent(getApplicationContext(),delete_acc.class);
            startActivity(in);

        }
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

            return true;
    }

    public void onBackPressed() {
        finishAffinity();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==109&&resultCode==RESULT_OK)
        {
           final String text=data.getStringExtra("text");

            allcontacts="";
            for(int i=0;i<GlobalApp.myal.size();i++)
            {
                allcontacts+=GlobalApp.myal.get(i).PHONE+"``"+GlobalApp.myal.get(i).NAME+"~~";
            }
            Log.d("QR",text);
            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    try
                    {
                        Socket sock = new Socket(GlobalApp.IP, GlobalApp.PORT);
                        DataInputStream dis = new DataInputStream(sock.getInputStream());
                        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                        String res= "/IM/validate_phone?phone=" + GlobalApp.PHONE +"&id="+text +"&my_contacts="+ allcontacts+"&name="+ GlobalApp.USERNAME;
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
                        dos.writeBytes("GET "+res+" HTTP/1.1\r\n");
                        dos.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + " \r\n");
                        dos.writeBytes("\r\n");
                        while (true) {
                            String s = dis.readLine();
                            if (s == null || s == "") {
                                break;
                            }
                            Log.d("QR", s);
                        }
                        final String msg = dis.readLine();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
    }
}
