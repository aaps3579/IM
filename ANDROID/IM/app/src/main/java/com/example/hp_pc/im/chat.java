package com.example.hp_pc.im;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by HP_PC on 18-04-2016.
 */
public class chat
{
    public String fname;
    public String fphone;
    public String msgfrom;
    public String msg;
    public int  read;
    public String datetime;
    public Bitmap bmp;

    chat(
            String fname,
            String fphone,
            String msgfrom,
            String msg,
            int read,
            String datetime,
            Bitmap bmp
            )
    {

        this.fname = fname;
        this.fphone = fphone;
        this.msgfrom = msgfrom;
        this.msg = msg;
        this.read=read;
        this.datetime = datetime;
        this.bmp = bmp;
    }

}
