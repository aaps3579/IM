package com.example.hp_pc.im;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class GlobalApp extends Application
{
    static boolean flag=false;
    static String PHONE;
    static String USERNAME;
    static String IP="192.168.1.233";
    static int PORT=8084;
    static ArrayList <contact> al=new ArrayList<>();
    static ArrayList<mycontacts> myal=new ArrayList<>();
    static ArrayList<chat> alchat=new ArrayList<>();
    static String fname;
    static String fphone;
    static String fstatus;
    static int theme=R.style.MyMaterialTheme;
    static Bitmap bbig;
}
