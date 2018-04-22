package com.example.hp_pc.im;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class msg_service extends Service {
    String mymessage;
    public msg_service()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable()
        {
            String IP,NAME,PHONE,msgs;
            int ID;
            @Override
            public void run()
            {
                try {
                    FileInputStream fs = openFileInput("ID.txt");
                    DataInputStream ds = new DataInputStream(fs);
                    ID = ds.readInt();

                    ds.close();
                    ds.close();
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                    while(true) {
                        File w=getFileStreamPath("ID.txt");
                        File w1=getFileStreamPath("pass.txt");
                        if(!(w1.exists()))
                        {
                            break;

                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    try {
                        int count=0;
                        SQLiteDatabase db;
                        FileInputStream fx = openFileInput("pass.txt");
                        DataInputStream dx = new DataInputStream(fx);
                        PHONE = dx.readLine().toString();
                        NAME = dx.readLine().toString();
                        IP = dx.readLine().toString();



                        Socket sock = new Socket(IP, 8084);
                        db=openOrCreateDatabase("MyDB",MODE_PRIVATE,null);
                        DataInputStream dis = new DataInputStream(sock.getInputStream());
                        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                        dos.writeBytes("GET /IM/fetch_msg?phone=" + PHONE + "&ID=" + ID + " HTTP/1.1\r\n");
                        dos.writeBytes("HOST: " + IP + ":8084" + " \r\n");
                        dos.writeBytes("\r\n");
                        while (true) {
                            String s = dis.readLine();
                            if (s == null || s == "") {
                                break;
                            }
                            Log.d("MYMSG", s);
                        }

                        String msg=dis.readLine();
                        Log.d("x",msg+"");
                        StringTokenizer st = new StringTokenizer(msg, "@#");

                        while (st.hasMoreTokens()) {
                            String s1 = st.nextToken();
                            Log.d("one1",s1);
                            StringTokenizer st2 = new StringTokenizer(s1, "~~");
                            while (st2.hasMoreTokens())
                            {
                                String msg_from;
                                int id = Integer.parseInt(st2.nextToken());
                                String msgs = st2.nextToken();
                                String fname;
                                msg_from = st2.nextToken();
                                fname=msg_from;
                                String msg_to = st2.nextToken();
                                String time = st2.nextToken();
                                String type=st2.nextToken();
                                String status=st2.nextToken();
                                Log.d("msginsert", id + ":" + msgs + ":" + msg_from + ":" + msg_to + ":"+status);

                                Cursor c = db.rawQuery("select * from msgs where msgid='"+id+"'",null);
                                if(c.moveToNext())
                                {

                                }
                                else {

                                    String query = "INSERT INTO msgs values(" + id + ",'" + msgs + "','"+ msg_from + "','" + msg_to + "','" + time + "','"+type+"'" + ",'"+status+"')";
                                    db.execSQL(query);
                                    ID=id;

                                    if((!msg_from.equals(PHONE))&&status.equals("unread"))
                                    {
                                        count++;

                                        Cursor x=db.rawQuery("select * from contacts where phone='"+fname+"'",null);
                                        if(x.moveToNext())
                                        {
                                            fname=x.getString(x.getColumnIndex("name"));
                                        }
                                        else
                                        {


                                        }

                                        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext());
                                        Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.drawable.chat_home);
                                        builder.setLargeIcon(bmp);
                                        builder.setSmallIcon(R.drawable.ic_dialog_email);
                                        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.cool);
                                        builder.setSound(uri);
                                        builder.setVibrate(new long[]{1000,1000});

                                        builder.setContentTitle("IM");
                                        if(msgs.length()>20&&!msgs.contains("\\uD"))
                                        {
                                            msgs = msgs.substring(0,20);


                                        }
                                        msgs=StringEscapeUtils.unescapeJava(msgs);
                                        builder.setContentText(fname+":"+msgs);

                                        builder.setContentInfo(count + "");
                                        Intent in=new Intent(msg_service.this,main_chat.class);
                                        PendingIntent pendingIntent=PendingIntent.getActivity(msg_service.this,0,in,PendingIntent.FLAG_UPDATE_CURRENT);
                                        builder.setContentIntent(pendingIntent);
                                        Notification notif=builder.build();
                                        NotificationManager nm=(NotificationManager)(getSystemService(NOTIFICATION_SERVICE));

                                        nm.notify(0, notif);


                                    }
                                }

                            }


                        }

                        FileOutputStream fs = openFileOutput("ID.txt", MODE_PRIVATE);
                        DataOutputStream dS = new DataOutputStream(fs);
                        dS.writeInt(ID);

                        Log.d("TEST",msg);

                        if(msg==null||msg.equals(""))
                        {

                        }
                        else
                        {
                          /*  try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
*/

                            Intent broadcast=new Intent();
                            broadcast.setAction("complete");
                            sendBroadcast(broadcast);

                            Intent broadcast1=new Intent();
                            broadcast1.setAction("complete1");
                            sendBroadcast(broadcast1);
                            Log.d("TEST", "send");
                        }

                        db.close();


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }





            }
        }).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
