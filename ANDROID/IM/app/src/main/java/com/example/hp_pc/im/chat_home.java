
package com.example.hp_pc.im;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class chat_home extends AppCompatActivity {

    String lid="",sendmsg="",ext="";
    EditText ed ;
    ImageButton imb;
    ArrayList<msg> al=new ArrayList<>();
    ListView lv;
    adapter4 ad;
    myreceiver1 mybr1;
    Toolbar t;
    TextView tv,filename;
    LinearLayout l;
    private static String mFileName = null;
    ImageButton img,imagattach,camera,gallery,send,video,audio,mic,file;
    boolean flag=false,audioflag=false;
    Uri myuri,videouri,audiouri,fileuri;
    private MediaRecorder mRecorder = null;
    boolean aflag=false;
    FrameLayout fm;

    MediaPlayer mp;
    File faudio;

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_chat_home,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.attach)
        {
            if(flag==false) {
                l.setVisibility(View.VISIBLE);

                flag=true;
            }
            else
            {
                l.setVisibility(View.GONE);
                flag=false;
            }
        }
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        return true;
    }


    public void onResume()
    {
        Log.d("Receive2","R");

        IntentFilter inf = new IntentFilter();
        inf.addAction("complete1");
        registerReceiver(mybr1, inf);
        super.onResume();
    }

    @Override
    public void onPause()
    {

        unregisterReceiver(mybr1);
        super.onPause();

    }

    class msg
    {
        int msgid;
        String msg,msgfrom,msgto,datetime,type;
        Bitmap bmp;
        msg(int msgid,String msg,String msgfrom,String msgto,String datetime,String type,Bitmap bmp)
        {
            this.msgid=msgid;
            this.msg=msg;
            this.msgfrom=msgfrom;
            this.msgto=msgto;
            this.datetime=datetime;
            this.type=type;
            this.bmp=bmp;


        }
    }
    class fetch_msg implements Runnable
    {

        @Override
        public void run()
        {

            SQLiteDatabase db=openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
            Cursor c=db.rawQuery("select * from msgs where msgfrom='"+GlobalApp.fphone+"' or msgto='"+GlobalApp.fphone+"'",null);


            al.clear();

            while(c.moveToNext()) {


                final int msgid = c.getInt(c.getColumnIndex("msgid"));
                final String msg = c.getString(c.getColumnIndex("msg"));
                final String msgfrom = c.getString(c.getColumnIndex("msgfrom"));
                final String msgto = c.getString(c.getColumnIndex("msgto"));
                final String datetime = c.getString(c.getColumnIndex("datetime"));
                final String type = c.getString(c.getColumnIndex("type"));
                final Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.chat_home);
                Log.d("msg", msgid + ":" + msg + ":" + msgfrom + ":" + msgto + ":");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        al.add(new msg(msgid, msg, msgfrom, msgto, datetime, type, bmp));

                    }
                });

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ad.notifyDataSetChanged();
                    lv.setSelection(ad.getCount() - 1);
                }
            });
            db.execSQL("UPDATE msgs SET status='read' where msgfrom='" + GlobalApp.fphone + "'");
           try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Socket sock = new Socket(GlobalApp.IP, GlobalApp.PORT);
                DataInputStream dis = new DataInputStream(sock.getInputStream());
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                dos.writeBytes("GET /IM/read_status?from=" + GlobalApp.fphone + "&to=" + GlobalApp.PHONE + " HTTP/1.1\r\n");
                dos.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + " \r\n");
                dos.writeBytes("\r\n");
                while (true) {
                    String s = dis.readLine();
                    if (s == null || s == "") {
                        break;
                    }

                }
                final String msg = dis.readLine();
                Log.d("Read",msg);
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }


            for(int i=0;i<al.size();i++)
            {
                if(al.get(i).type.equals("image"))
                {
                    try {
                        URL aURL = new URL("http://" + GlobalApp.IP + ":" + GlobalApp.PORT + "/IM/images/" +al.get(i).msgid + "_small.jpg");
                        URLConnection conn = aURL.openConnection();
                        conn.connect();
                        Log.d("MYMSG", "image fetched");
                        InputStream is = conn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);

                        Bitmap tempbmp = BitmapFactory.decodeStream(bis);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        tempbmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);

                        al.get(i).bmp = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
                        is.close();
                        bis.close();
                        baos.close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ad.notifyDataSetChanged();

                            }
                        });
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }


        }
    }

    private void startPlaying(Uri uri)
    {

    }

    private void stopPlaying() {
        mp.release();
        mp = null;
    }

    class adapter4 extends BaseAdapter {

        @Override
        public int getCount() {
            return al.size();
        }

        @Override
        public Object getItem(int position) {
            return al.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.single_row_one_chat, parent, false);
            final ImageButton file_down=(ImageButton) (convertView.findViewById(R.id.file_download));
            TextView tvmsg1 = (TextView) (convertView.findViewById(R.id.textView20));
            FrameLayout fl = (FrameLayout) (convertView.findViewById(R.id.imglayout));
            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linear1);
            LinearLayout linearLayout2 = (LinearLayout) convertView.findViewById(R.id.linear2);
            FrameLayout lfile = (FrameLayout) convertView.findViewById(R.id.lfile);
            final ImageView layout_image = (ImageView) convertView.findViewById(R.id.layout_image);
            final ImageView fileview = (ImageView) convertView.findViewById(R.id.fileview);
            final ImageView layout_download = (ImageView) convertView.findViewById(R.id.layout_download);
            LinearLayout laudio = (LinearLayout) convertView.findViewById(R.id.laudio);
            final ImageButton down = (ImageButton) (convertView.findViewById(R.id.down));
            final ImageButton play = (ImageButton) (convertView.findViewById(R.id.play));
            final ImageButton pause = (ImageButton) (convertView.findViewById(R.id.pause));
            final ProgressBar pb = (ProgressBar) (convertView.findViewById(R.id.progressBar));
            final TextView filename=(TextView) (convertView).findViewById(R.id.filename);



            if (al.get(position).msgfrom.equals(GlobalApp.PHONE)) {
                linearLayout.setGravity(Gravity.RIGHT);
                tvmsg1.setBackgroundResource(R.drawable.bubble_green);

            } else {

                tvmsg1.setTextColor(getResources().getColor(R.color.colorAccent));
                tvmsg1.setBackgroundResource(R.drawable.bubble_yellow);
            }

            Log.d("IMAGE", al.get(position).type + " " + al.get(position).msgid);
            if (al.get(position).type.equals("text")) {
                lfile.setVisibility(View.GONE);
                laudio.setVisibility(View.GONE);
                fl.setVisibility(View.GONE);
                String msg = al.get(position).msg;
                msg = StringEscapeUtils.unescapeJava(msg);
                tvmsg1.setText(msg);
                Log.d("IMAGE", "in if");


            } else if (al.get(position).type.equals("image")) {

                lfile.setVisibility(View.GONE);
                Log.d("IMAGE", "in else");
                laudio.setVisibility(View.GONE);
                layout_image.setImageBitmap(al.get(position).bmp);
                if (al.get(position).msgfrom.equals(GlobalApp.PHONE)) {
                    layout_image.setBackgroundResource(R.drawable.bubble_green);
                } else {
                    layout_image.setBackgroundResource(R.drawable.bubble_yellow);

                }
                fl.setVisibility(View.VISIBLE);
                tvmsg1.setVisibility(View.GONE);

                final File storage = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Images" + File.separator + al.get(position).msgid + ".jpg");

                if (storage.exists()) {
                    layout_download.setVisibility(View.GONE);
                    layout_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = new Intent();
                            i.setAction(Intent.ACTION_VIEW);
                            i.setDataAndType(Uri.fromFile(storage), "image/*");
                            startActivity(i);

                        }
                    });


                } else {
                    layout_download.setVisibility(View.VISIBLE);
                    layout_download.setOnClickListener(new View.OnClickListener() {
                        //int x=al.get(position).msgid;
                        @Override
                        public void onClick(View v) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //download
                                        final OutputStream output = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Images" + File.separator + al.get(position).msgid + ".jpg"));
                                        URL url = null;
                                        try {
                                            url = new URL("http://" + GlobalApp.IP + ":" + GlobalApp.PORT + "/IM/images/" + al.get(position).msgid + ".jpg");
                                            InputStream input = url.openStream();
                                            byte[] buffer = new byte[10000];
                                            int bytesRead = 0;
                                            while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                                                output.write(buffer, 0, bytesRead);
                                            }
                                            output.close();
                                            input.close();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    layout_download.setVisibility(View.GONE);
                                                    Intent i = new Intent();
                                                    i.setAction(Intent.ACTION_VIEW);
                                                    i.setDataAndType(Uri.fromFile(storage), "image/*");
                                                    startActivity(i);

                                                }
                                            });

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                }
                            }).start();

                        }
                    });

                }
            } else if (al.get(position).type.equals("video")) {

                lfile.setVisibility(View.GONE);
                laudio.setVisibility(View.GONE);
                Log.d("IMAGE", "in else");
                layout_image.setImageBitmap(BitmapFactory.decodeResource(getResources(), android.R.drawable.presence_video_away));
                if (al.get(position).msgfrom.equals(GlobalApp.PHONE)) {
                    layout_image.setBackgroundResource(R.drawable.bubble_green);
                } else {
                    layout_image.setBackgroundResource(R.drawable.bubble_yellow);

                }

                fl.setVisibility(View.VISIBLE);
                tvmsg1.setVisibility(View.GONE);

                final File storage = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Videos" + File.separator + al.get(position).msgid + ".mp4");

                if (storage.exists()) {
                    layout_download.setVisibility(View.GONE);
                    layout_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = new Intent();
                            i.setAction(Intent.ACTION_VIEW);
                            i.setDataAndType(Uri.fromFile(storage), "video/*");
                            startActivity(i);

                        }
                    });


                } else {
                    layout_download.setVisibility(View.VISIBLE);
                    layout_download.setOnClickListener(new View.OnClickListener() {
                        //int x=al.get(position).msgid;
                        @Override
                        public void onClick(View v) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //download
                                        final OutputStream output = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Videos" + File.separator + al.get(position).msgid + ".mp4"));
                                        URL url = null;
                                        try {
                                            url = new URL("http://" + GlobalApp.IP + ":" + GlobalApp.PORT + "/IM/videos/" + al.get(position).msgid + ".mp4");
                                            InputStream input = url.openStream();
                                            byte[] buffer = new byte[10000];
                                            int bytesRead = 0;
                                            while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                                                output.write(buffer, 0, bytesRead);
                                            }
                                            output.close();
                                            input.close();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    layout_download.setVisibility(View.GONE);
                                                    Intent i = new Intent();
                                                    i.setAction(Intent.ACTION_VIEW);
                                                    i.setDataAndType(Uri.fromFile(storage), "video/*");
                                                    startActivity(i);

                                                }
                                            });

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                }
                            }).start();

                        }
                    });

                }
            } else if (al.get(position).type.equals("audio")) {


                lfile.setVisibility(View.GONE);
                tvmsg1.setVisibility(View.GONE);
                fl.setVisibility(View.GONE);

                laudio.setVisibility(View.VISIBLE);

                if (al.get(position).msgfrom.equals(GlobalApp.PHONE)) {
                    play.setBackgroundColor(Color.GREEN);
                    pause.setBackgroundColor(Color.GREEN);
                    down.setBackgroundColor(Color.GREEN);

                    laudio.setGravity(Gravity.RIGHT);
                    laudio.setBackgroundResource(R.drawable.bubble_green);
                } else {
                    play.setBackgroundColor(Color.YELLOW);
                    pause.setBackgroundColor(Color.YELLOW);
                    down.setBackgroundColor(Color.YELLOW);
                    laudio.setGravity(Gravity.LEFT);
                    laudio.setBackgroundResource(R.drawable.bubble_yellow);

                }
               final File f = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Audio" + File.separator + al.get(position).msgid + ".mp3");
                if (f.exists()) {
                    down.setVisibility(View.GONE);
                    pause.setEnabled(false);

                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mp = new MediaPlayer();
                            try {

                                mp.setDataSource(Uri.fromFile(f).getPath());
                                mp.prepare();
                                mp.start();
                            } catch (IOException e) {
                                Log.e("", "prepare() failed");
                            }
                            if (mp.isPlaying()) {
                                play.setEnabled(false);
                                pause.setEnabled(true);
                            }

                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    play.setEnabled(true);
                                    pause.setEnabled(false);
                                }
                            });
                        }
                    });
                    pause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            stopPlaying();
                            play.setEnabled(true);
                            pause.setEnabled(false);

                        }
                    });


                } else {

                    pause.setVisibility(View.GONE);
                    play.setVisibility(View.GONE);
                    down.setVisibility(View.VISIBLE);
                    down.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Thread(new Runnable() {
                                int bytesRead = 0;

                                @Override
                                public void run() {
                                    try {
                                        //download
                                        faudio = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Audio" + File.separator + al.get(position).msgid + ".mp3");
                                        final OutputStream output = new FileOutputStream(faudio);
                                        Log.d("audio", al.get(position).msgid + "");

                                        URL url = null;
                                        try {
                                            url = new URL("http://" + GlobalApp.IP + ":" + GlobalApp.PORT + "/IM/audio/" + al.get(position).msgid + ".mp3");
                                            InputStream input = url.openStream();
                                            final int len = input.available();
                                            byte[] buffer = new byte[10000];
                                            bytesRead = 0;
                                            int count = 0;
                                            while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                                                count = count + bytesRead;
                                                final int percentage = (int) ((count * 100.0) / len);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        pb.setProgress(percentage);

                                                    }
                                                });
                                                output.write(buffer, 0, bytesRead);

                                            }

                                            output.close();
                                            input.close();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    down.setVisibility(View.INVISIBLE);
                                                    play.setVisibility(View.VISIBLE);
                                                    pause.setVisibility(View.GONE);
                                                    play.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            mp = new MediaPlayer();
                                                            try {

                                                                mp.setDataSource(Uri.fromFile(faudio).getPath());
                                                                mp.prepare();
                                                                mp.start();
                                                            } catch (IOException e) {
                                                                Log.e("", "prepare() failed");
                                                            }
                                                            if (mp.isPlaying()) {
                                                                play.setEnabled(false);
                                                                pause.setVisibility(View.VISIBLE);
                                                                pause.setEnabled(true);
                                                            }
                                                        }
                                                    });
                                                    pause.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {


                                                            stopPlaying();
                                                            play.setEnabled(true);
                                                            pause.setEnabled(false);

                                                        }
                                                    });


                                                }
                                            });

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }


                                }
                            }).start();

                        }
                    });


                }

            } else if (al.get(position).type.equals("file")) {
                tvmsg1.setVisibility(View.GONE);
                laudio.setVisibility(View.GONE);
                fl.setVisibility(View.GONE);

                if (al.get(position).msgfrom.equals(GlobalApp.PHONE)) {
                //    lfile.setGravity(Gravity.RIGHT);
                    lfile.setBackgroundResource(R.drawable.bubble_green);

                } else {
                  //  lfile.setGravity(Gravity.LEFT);
                    lfile.setBackgroundResource(R.drawable.bubble_yellow);

                }
                int size=al.get(position).msg.length();
                if(size>12)
                {
                    size=12;
                }
                filename.setText(al.get(position).msg.substring(0,size));
                final File f = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Documents" + File.separator + al.get(position).msg);
                Log.d("File Exist",f.getPath()+";"+ext);
                if (f.exists())
                {
                    Log.d("File Exist","Yes");
                    file_down.setVisibility(View.GONE);
                    fileview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            File fnew =new File(Environment.getExternalStorageDirectory().getPath()+ File.separator + "IM" + File.separator + "Documents" + File.separator);

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(fnew), "resource/folder");

                            if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
                            {
                                startActivity(intent);
                            }
                            else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Snackbar.make(fm,"Install a File Explorer",Snackbar.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });

                } else
                {
                    file_down.setVisibility(View.VISIBLE);

                    final File ffile=new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Documents" + File.separator + al.get(position).msg);

                    file_down.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Thread(new Runnable() {
                                int bytesRead = 0;

                                @Override
                                public void run() {
                                    try {
                                        //download
                                         final OutputStream output = new FileOutputStream(ffile);

                                        URL url = null;
                                        try {
                                            url = new URL("http://" + GlobalApp.IP + ":" + GlobalApp.PORT + "/IM/documents/" + al.get(position).msg);
                                            InputStream input = url.openStream();
                                            final int len = input.available();
                                            byte[] buffer = new byte[10000];
                                            bytesRead = 0;
                                            int count = 0;
                                            while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {


                                                output.write(buffer, 0, bytesRead);

                                            }

                                            output.close();
                                            input.close();


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            file_down.setVisibility(View.GONE);

                                            fileview.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    File fnew=new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "Documents" + File.separator );

                                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setDataAndType(Uri.fromFile(fnew), "resource/folder");

                                                    if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
                                                    {
                                                        startActivity(intent);
                                                    }
                                                    else
                                                    {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Snackbar.make(fm,"Install a File Explorer",Snackbar.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            });


                                        }
                                    });

                                }
                            }).start();

                        }
                    });





                }

            }
                return convertView;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(GlobalApp.theme);
        setContentView(R.layout.activity_chat_home);
        fm = (FrameLayout) findViewById(R.id.frame);
        img=(ImageButton) findViewById(R.id.imageButton5);
        mic = (ImageButton)(findViewById(R.id.mic));
        file = (ImageButton)(findViewById(R.id.file));

        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("file/*");
                startActivityForResult(intent,102);
            }
        });

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
        mFileName += "audiorecordertest.mp3";

        try {
            FileOutputStream fos=  new FileOutputStream(mFileName);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File test = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"IM");
        Log.d("test",test.exists()+"");

        mic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN: {
                        Toast.makeText(getApplicationContext(),"Start recording",Toast.LENGTH_SHORT).show();
                        mic.setBackgroundColor(Color.CYAN);

                        mRecorder = new MediaRecorder();
                        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        mRecorder.setOutputFile(mFileName);



                        try {
                            mRecorder.prepare();
                            mRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Toast.makeText(getApplicationContext(),"Stop recording",Toast.LENGTH_SHORT).show();
                        if(mRecorder!=null)
                        {
                            mic.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                            mRecorder.release();

                            mRecorder = null;
                            audiouri= Uri.fromFile(new File(mFileName));
                            sendaudio();
                            break;
                        }

                    }
                }


                return true;
            }
        });


        audio=(ImageButton) findViewById(R.id.audio);
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i=new Intent(Intent.ACTION_PICK);
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("audio/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(in, 98);


            }
        });

        video=(ImageButton)findViewById(R.id.imageButton6);
        l=(LinearLayout) findViewById(R.id.linearpop);
        t=(Toolbar)findViewById(R.id.toolbar2);
        tv=(TextView) findViewById(R.id.toolbar_title1);
        tv.setText(GlobalApp.fname);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mybr1=new myreceiver1();
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l.setVisibility(View.GONE);
                flag=false;
                final Dialog d=new Dialog(chat_home.this);
                d.setContentView(R.layout.dialog_show);
                d.show();
                camera=(ImageButton) d.findViewById(R.id.camera);
                gallery=(ImageButton) d.findViewById(R.id.gallery);
                send=(ImageButton) d.findViewById(R.id.send);
                send.setEnabled(false);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent in1 = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "DCIM" + File.separator + "Camera" + File.separator + new Date() + ".mp4");
                        videouri = Uri.fromFile(f);
                        in1.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20);
                        in1.putExtra(MediaStore.EXTRA_OUTPUT, videouri);
                        startActivityForResult(in1, 30);

                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(Intent.ACTION_PICK);
                        in.setType("video/*");
                        startActivityForResult(in, 40);


                    }
                });
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Image2", "Done");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    FileInputStream fis = (FileInputStream) (getContentResolver().openInputStream(videouri));
                                    int len = fis.available();

                                    Log.d("VideoLen", len + "");

                                    Socket c = new Socket(GlobalApp.IP, GlobalApp.PORT);
                                    DataInputStream i = new DataInputStream(c.getInputStream());
                                    DataOutputStream o = new DataOutputStream(c.getOutputStream());
                                    o.writeBytes("POST /IM/receive_vid?from=" + GlobalApp.PHONE + "&to=" + GlobalApp.fphone + " HTTP/1.1\r\n");
                                    o.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + "\r\n");
                                    o.writeBytes("Content-Length: " + len + "\r\n");
                                    o.writeBytes("Content-Type: video/mp4\r\n");
                                    o.writeBytes("\r\n");
                                    Log.d("Video", "Request Sent");
                                    byte b[] = new byte[10000];
                                    int r = 0;
                                    while (true) {
                                        r = fis.read(b, 0, 10000);
                                        if (r == -1) {

                                            Log.d("VideoSend", "Send");
                                            break;
                                        }
                                        Log.d("VideoWriting", "Writing File");
                                        o.write(b, 0, r);//Writing File To Server
                                    }
                                    while (true) {
                                        String s = i.readLine();
                                        if (s == null || s == "") {
                                            break;
                                        }
                                    }

                                    final String msg = i.readLine();
                                    Log.d("VideoResp",msg);
                                    fis = (FileInputStream) (getContentResolver().openInputStream(videouri));
                                    FileOutputStream fileOutputStream=new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+File.separator + "IM" + File.separator + "Videos"+File.separator+msg+".mp4");

                                    r= 0;

                                    while (true)
                                    {
                                        r = fis.read(b, 0, 10000);
                                        if (r == -1) {

                                            break;
                                        }

                                        fileOutputStream.write(b,0,r);
                                    }
                                    fileOutputStream.close();
                                    fm = (FrameLayout) findViewById(R.id.frame);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            d.dismiss();

                                        }
                                    });

                                    Thread.sleep(500);


                                    Snackbar.make(fm, "Success", Snackbar.LENGTH_SHORT).show();

                                }catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }

                            }
                        }).start();

                    }
                });





            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l.setVisibility(View.GONE);
                flag=false;
                final Dialog d=new Dialog(chat_home.this);
                d.setContentView(R.layout.dialog_show);
                d.show();
                camera=(ImageButton) d.findViewById(R.id.camera);
                gallery=(ImageButton) d.findViewById(R.id.gallery);
                send=(ImageButton) d.findViewById(R.id.send);
                send.setEnabled(false);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent in1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory().getPath() +File.separator+"DCIM"+File.separator+"Camera" +File.separator+ new Date() + ".jpg");
                        myuri = Uri.fromFile(f);
                        in1.putExtra(MediaStore.EXTRA_OUTPUT, myuri);
                        startActivityForResult(in1, 20);

                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(Intent.ACTION_PICK);
                        in.setType("image/*");
                        startActivityForResult(in, 10);


                    }
                });
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Image2", "Done");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    FileInputStream fis = (FileInputStream) (getContentResolver().openInputStream(myuri));
                                    int len = fis.available();

                                    Log.d("Image2", len + "");

                                    Socket c = new Socket(GlobalApp.IP, GlobalApp.PORT);
                                    DataInputStream i = new DataInputStream(c.getInputStream());
                                    DataOutputStream o = new DataOutputStream(c.getOutputStream());
                                    o.writeBytes("POST /IM/receive_img?from=" + GlobalApp.PHONE + "&to=" + GlobalApp.fphone + " HTTP/1.1\r\n");
                                    o.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + "\r\n");
                                    o.writeBytes("Content-Length: " + len + "\r\n");
                                    o.writeBytes("Content-Type: image/jpg\r\n");
                                    o.writeBytes("\r\n");
                                    Log.d("Image2", "Request Sent");
                                    byte b[] = new byte[10000];
                                    int r = 0;
                                    while (true) {
                                        r = fis.read(b, 0, 10000);
                                        if (r == -1) {

                                            Log.d("Image2", "Send");
                                            break;
                                        }
                                        Log.d("Image2", "Writing File");
                                        o.write(b, 0, r);//Writing File To Server
                                    }
                                    while (true) {
                                        String s = i.readLine();
                                        if (s == null || s == "") {
                                            break;
                                        }
                                    }

                                    final String msg = i.readLine();
                                    Log.d("Image2ID",msg);
                                    fis = (FileInputStream) (getContentResolver().openInputStream(myuri));
                                    FileOutputStream fileOutputStream=new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+File.separator + "IM" + File.separator + "Images"+File.separator+msg+".jpg");

                                    r = 0;
                                    lid=msg;
                                    while (true)
                                    {
                                           r = fis.read(b, 0, 10000);
                                           if (r == -1) {

                                               break;
                                           }

                                           fileOutputStream.write(b,0,r);
                                       }
                                    fileOutputStream.close();
                                    FrameLayout fm = (FrameLayout) findViewById(R.id.frame);
                                    Log.d("Image2", msg);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            d.dismiss();

                                        }
                                    });

                                    Thread.sleep(500);


                                        Snackbar.make(fm, "Success", Snackbar.LENGTH_SHORT).show();

                                }catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }

                            }
                        }).start();

                    }
                });
            }
        });




        ad=new adapter4();
        lv=(ListView) findViewById(R.id.listView3);
        lv.setAdapter(ad);
        Thread t=new Thread(new fetch_msg());
        t.start();

        ed=(EditText)findViewById(R.id.editText10);
        imb=(ImageButton)findViewById(R.id.imageButton4);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed.getText().toString().isEmpty())
                {


                } else {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String msg = ed.getText().toString();
                                msg = StringEscapeUtils.escapeJava(msg);


                                sendmsg=msg;
                                Socket sock = new Socket(GlobalApp.IP, GlobalApp.PORT);
                                DataInputStream dis = new DataInputStream(sock.getInputStream());
                                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                                String res = "/IM/send_msg?msg=" + msg + "&username=" + GlobalApp.PHONE + "&chat_name=" + GlobalApp.fphone;

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
                                final String si = dis.readLine();
                                Log.d("RESULT", si);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("MYMSG", "empty");
                                        ed.setText("");

                                    }
                                });


                            } catch (Exception ex) {
                                ex.printStackTrace();

                            }


                        }
                    }).start();

                }

            }
        });


    }
    class myreceiver1 extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Log.d("Receive2","R");
            new Thread(new fetch_msg()).start();

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(requestCode==20)
        {
            if (resultCode==RESULT_OK) {
                if (myuri != null) {
                    send.setEnabled(true);
                    send.setVisibility(View.VISIBLE);
                    Log.d("Image1", "Done");
                }
            }
        }
        else
        if(requestCode==10)
        {
            if(resultCode==RESULT_OK)
            {
                myuri=data.getData();

                if (myuri != null) {
                    send.setEnabled(true);
                    send.setVisibility(View.VISIBLE);

                }
                Log.d("Image2", myuri + "");



            }
        }
        else
        if(requestCode==30)
        {
            if (resultCode==RESULT_OK) {
                if (videouri != null) {


                    send.setEnabled(true);
                    send.setVisibility(View.VISIBLE);
                    Log.d("Video camera ", "Done");
                }
                Log.d("Video camera ", "cancel");
            }
        }
        else
        if(requestCode==40)
        {
            if (resultCode==RESULT_OK) {

                    videouri=data.getData();
                if (videouri != null) {
                    send.setEnabled(true);
                    send.setVisibility(View.VISIBLE);
                    Log.d("Video gallery ", "Done");
                }
                Log.d("Video camera ", "cancel");
            }
        }
        else
            if(requestCode==98)
            {
                if(resultCode==RESULT_OK)
                {
                    audiouri=data.getData();
                    if (audiouri != null)
                    {
                        sendaudio();
                    }
                }
            }
        else
                if(requestCode==102)
                {
                    if(resultCode==RESULT_OK) {
                        fileuri = data.getData();
                        Log.d("FILEURI", fileuri + "");
                        if (fileuri != null) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        Snackbar.make(fm,"Sending File",Snackbar.LENGTH_LONG).show();
                                        FileInputStream fis = (FileInputStream) (getContentResolver().openInputStream(fileuri));
                                        int len = fis.available();
                                        String path=fileuri.getPath();
                                        String res =path ;
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

                                        int pathindex=res.lastIndexOf(File.separator);


                                        res=res.substring(pathindex+1,res.length());
                                        Log.d("FileLen", len + "");
                                        Log.d("Fileext",path+","+ext);

                                        Socket c = new Socket(GlobalApp.IP, GlobalApp.PORT);
                                        DataInputStream i = new DataInputStream(c.getInputStream());
                                        DataOutputStream o = new DataOutputStream(c.getOutputStream());
                                        o.writeBytes("POST /IM/receive_file?from=" + GlobalApp.PHONE + "&to=" + GlobalApp.fphone+"&path="+res+ " HTTP/1.1\r\n");
                                        o.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + "\r\n");
                                        o.writeBytes("Content-Length: " + len + "\r\n");
                                        o.writeBytes("Content-Type: */*\r\n");
                                        o.writeBytes("\r\n");
                                        Log.d("File", "Request Sent");
                                        byte b[] = new byte[10000];
                                        int r = 0;
                                        while (true) {
                                            r = fis.read(b, 0, 10000);
                                            if (r == -1) {

                                                Log.d("FileSend", "Send");
                                                break;
                                            }
                                            Log.d("FileWriting", "Writing File");
                                            o.write(b, 0, r);//Writing File To Server
                                        }
                                        while (true) {
                                            String s = i.readLine();
                                            if (s == null || s == "") {
                                                break;
                                            }
                                        }

                                        final String msg = i.readLine();
                                        Log.d("FileResp",msg);
                                        fis = (FileInputStream) (getContentResolver().openInputStream(fileuri));
                                        FileOutputStream fileOutputStream=new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+File.separator + "IM" + File.separator + "Documents"+File.separator+msg);

                                        r= 0;

                                        while (true)
                                        {
                                            r = fis.read(b, 0, 10000);
                                            if (r == -1) {
                                                Log.d("local","saved");
                                                break;

                                            }

                                            fileOutputStream.write(b,0,r);
                                        }
                                        fileOutputStream.close();
                                        Snackbar.make(fm,"Success",Snackbar.LENGTH_LONG).show();

                                    } catch (Exception ex) {
                                        ex.printStackTrace();

                                    }

                                }
                            }).start();
                        }
                    }
                }
    }
    void sendaudio()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fis = (FileInputStream) (getContentResolver().openInputStream(audiouri));
                    int len = fis.available();

                    Log.d("AudioLen", len + "");

                    Socket co = new Socket(GlobalApp.IP, GlobalApp.PORT);
                    DataInputStream id = new DataInputStream(co.getInputStream());
                    DataOutputStream od = new DataOutputStream(co.getOutputStream());
                    od.writeBytes("POST /IM/receive_audio?from=" + GlobalApp.PHONE + "&to=" + GlobalApp.fphone + " HTTP/1.1\r\n");
                    od.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + "\r\n");
                    od.writeBytes("Content-Length: " + len + "\r\n");
                    od.writeBytes("Content-Type: audio/mp3\r\n");
                    od.writeBytes("\r\n");
                    byte b[] = new byte[10000];
                    int r = 0;
                    while (true) {
                        r = fis.read(b, 0, 10000);
                        if (r == -1) {

                            break;
                        }
                        od.write(b, 0, r);//Writing File To Server
                    }
                    while (true) {
                        String s = id.readLine();
                        if (s == null || s == "") {
                            break;
                        }
                    }

                    final String msg = id.readLine();
                    Log.d("AudioReceive",msg);
                    fis = (FileInputStream) (getContentResolver().openInputStream(audiouri));
                    FileOutputStream fileOutputStream=new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+File.separator + "IM" + File.separator + "Audio"+File.separator+msg+".mp3");

                    r= 0;

                    while (true)
                    {
                        r = fis.read(b, 0, 10000);
                        if (r == -1) {

                            break;
                        }

                        fileOutputStream.write(b,0,r);
                    }
                    fileOutputStream.close();
                    FrameLayout fm = (FrameLayout) findViewById(R.id.frame);


                    Snackbar.make(fm, "Success", Snackbar.LENGTH_SHORT).show();

                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        }).start();


    }
}
