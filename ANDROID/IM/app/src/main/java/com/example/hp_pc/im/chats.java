package com.example.hp_pc.im;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class chats extends Fragment {


    SQLiteDatabase db;
    myreceiver mybr;
    ListView lv;
    myadapter3 ad3;
    boolean check;

    public chats() {
        // Required empty public constructor
    }


    @Override
    public void onResume()
    {
        IntentFilter inf = new IntentFilter();
        inf.addAction("complete");
        getActivity().registerReceiver(mybr, inf);
        super.onResume();
    }

    @Override
    public void onPause()
    {

       getActivity().unregisterReceiver(mybr);
        super.onPause();

    }
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible && GlobalApp.flag)
        {
            new Thread(new fetchmsg()).start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!check) {
            mybr = new myreceiver();
            lv = (ListView) getActivity().findViewById(R.id.listView4);
            ad3 = new myadapter3();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    GlobalApp.fname = GlobalApp.alchat.get(position).fname;
                    GlobalApp.fphone = GlobalApp.alchat.get(position).fphone;
                    Intent in = new Intent(getActivity().getApplicationContext(), chat_home.class);
                    startActivity(in);
                }
            });
            check=true;
        }

      if(GlobalApp.flag) {

            new Thread(new fetchmsg()).start();
        }
    }
    class myadapter3 extends BaseAdapter {

        @Override
        public int getCount() {
            return GlobalApp.alchat.size();
        }

        @Override
        public Object getItem(int position) {
            return GlobalApp.alchat.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.singlerow_chats, parent, false);
            ImageView imv=(ImageView) convertView.findViewById(R.id.imageView3);

            TextView name=(TextView) convertView.findViewById(R.id.textview18);
            TextView msg=(TextView) convertView.findViewById(R.id.textview19);
            TextView time=(TextView) convertView.findViewById(R.id.textview20);
            TextView count=(TextView) convertView.findViewById(R.id.textview21);
            imv.setImageBitmap(GlobalApp.alchat.get(position).bmp);
            name.setText(GlobalApp.alchat.get(position).fname);

            time.setText(GlobalApp.alchat.get(position).datetime);
            String msg1=GlobalApp.alchat.get(position).msg;
            if(msg1.length()>10&&!msg1.contains("\\uD"))
            {
                msg1=msg1.substring(0,10);
            }

            msg.setText(GlobalApp.alchat.get(position).fname+":"+StringEscapeUtils.unescapeJava(msg1));
            int read=GlobalApp.alchat.get(position).read;
            if(read>0)
            {
                count.setText(GlobalApp.alchat.get(position).read+"");
            }
            else
            {
                count.setText("");


            }

            return convertView;
        }
    }
    class fetchmsg implements Runnable
    {

        int msgid;
        String msgfrom, msg, msgto, type, datetime;

        @Override
        public void run() {
            Log.d("OnReceive","Thread");

            db = getActivity().openOrCreateDatabase("MyDB", getActivity().MODE_PRIVATE, null);
            Cursor c = db.query(false, "msgs", null, null, null, null, null, "msgid desc", null);
            GlobalApp.alchat.clear();
            while (c.moveToNext()) {
                msgid = c.getInt(c.getColumnIndex("msgid"));
                msg = c.getString(c.getColumnIndex("msg"));
                msgfrom = c.getString(c.getColumnIndex("msgfrom"));
                msgto = c.getString(c.getColumnIndex("msgto"));
                type = c.getString(c.getColumnIndex("type"));
                datetime = c.getString(c.getColumnIndex("datetime"));

                int flag=0;
                String fphone = "", fname = "", mfname = "";
                if (msgfrom.equals(GlobalApp.PHONE)) {
                    mfname = GlobalApp.USERNAME;
                    fphone = msgto;
                } else {
                    fphone = msgfrom;

                }
                for (int i = 0; i < GlobalApp.alchat.size(); i++) {
                    if (GlobalApp.alchat.get(i).fphone.equals(fphone)) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0)
                {
                    Cursor c1 = db.rawQuery("select * from contacts where phone='" + fphone + "'", null);
                    if (c1.moveToNext())
                    {
                        fname = c1.getString(c1.getColumnIndex("name"));
                        if (mfname.equals(""))
                        {
                            mfname = fname;

                        }
                    }
                    else {
                        fname = fphone;
                        mfname = fphone;
                    }
                    c1.close();
                    Cursor c2 = db.rawQuery("select * from msgs where msgfrom='" + fphone + "' and status='unread'", null);
                    int count = c2.getCount();
                    c2.close();

                    Bitmap bmp = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.chat_home);
                    GlobalApp.alchat.add(new chat(fname, fphone, mfname, msg, count, datetime, bmp));
                }

            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lv.setAdapter(ad3);

                }
            });
            for (int i = 0; i < GlobalApp.alchat.size(); i++) {
                URL aURL = null;
                try {
                    aURL = new URL("http://" + GlobalApp.IP + ":" + GlobalApp.PORT + "/IM/ProfilePics/" + GlobalApp.alchat.get(i).fphone + ".jpg");
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    Log.d("MYMSG", "image fetched");
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);

                    Bitmap tempbmp = BitmapFactory.decodeStream(bis);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    tempbmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);

                    Bitmap b=BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
                    if(b!=null) {
                        GlobalApp.alchat.get(i).bmp =b;
                    }

                    if(getActivity()!=null)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ad3.notifyDataSetChanged();

                            }
                        });
                    }

                    is.close();
                    bis.close();
                    baos.close();



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //null pointer exception

        }
    }
    class myreceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            if(GlobalApp.flag) {
                Thread t = new Thread(new fetchmsg());

                t.start();
            }
        }
    }

}
