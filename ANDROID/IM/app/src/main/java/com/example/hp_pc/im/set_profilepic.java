package com.example.hp_pc.im;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PictureDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class set_profilepic extends AppCompatActivity
{

    ImageButton pf,more;
    Uri myuri,original;
    ImageView imv;
    String phone,gender;
    LinearLayout l1,l2;
    Button up,cn;
    int count=1;
    ProgressDialog pd;
    Toolbar tl;

    @Override

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_profilepic,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.edit)
        {
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.VISIBLE);


        }
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(GlobalApp.theme);

       /* Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.addFlags(WindowManager.LayoutParams.ALPHA_CHANGED);
        window.setStatusBarColor(getResources().getColor(R.color.blue));*/


        setContentView(R.layout.activity_set_profilepic);
       // setTitle("Profile Pic");
       /* getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));*/
         tl=(Toolbar) findViewById(R.id.toolbar3);
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

        imv = (ImageView) findViewById(R.id.imageView);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),imageview.class);

                startActivity(i);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL aURL = null;
                    aURL = new URL("http://" + GlobalApp.IP + ":" + GlobalApp.PORT + "/IM/ProfilePics/" + GlobalApp.PHONE + ".jpg");
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    Log.d("MYMSG", "image fetched");
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);

                    Bitmap tempbmp = BitmapFactory.decodeStream(bis);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    tempbmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);

                  GlobalApp.bbig= BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imv.setImageBitmap(GlobalApp.bbig);
                        }
                    });


                    is.close();
                    bis.close();
                    baos.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imv.setImageResource(R.drawable.default_pic);
                        }
                    });

                }
            }
        }).start();
            up = (Button) findViewById(R.id.button7);
            cn = (Button) findViewById(R.id.button8);
          //  more = (ImageButton) findViewById(R.id.imageButton3);
            l1 = (LinearLayout) findViewById(R.id.ll1);
            l2 = (LinearLayout) findViewById(R.id.ll2);
            pf = (ImageButton) findViewById(R.id.imageButton2);
            phone = GlobalApp.PHONE;


            pf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder b = new AlertDialog.Builder(set_profilepic.this);
                    b.setTitle("IM");
                    b.setMessage("Choose An Image");
                    b.setIcon(R.drawable.ic_menu_camera);
                    b.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in = new Intent(Intent.ACTION_PICK);
                            in.setType("image/*");
                            startActivityForResult(in, 10);
                        }

                    });
                    b.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(Environment.getExternalStorageDirectory().getPath() +File.separator+"DCIM"+File.separator+"Camera" +File.separator+ new Random().nextInt(1000) + ".jpg");
                            myuri = Uri.fromFile(f);
                            in1.putExtra(MediaStore.EXTRA_OUTPUT, myuri);
                            startActivityForResult(in1, 20);

                        }
                    });
                    b.setNeutralButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog ad = b.create();
                    ad.show();


                }
            });
          /*  more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.VISIBLE);


                }
            });*/
            up.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          new Thread(new Runnable() {

                                              @Override
                                              public void run() {
                     /*       WifiManager wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
                            boolean wifi=wifiManager.isWifiEnabled();
                            if(!wifi)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(set_profilepic.this, "Please Enable Wifi", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }*/
                                                  try {
                                                      if (myuri == null) {
                                                          runOnUiThread(new Runnable() {
                                                              @Override
                                                              public void run() {
                                                                  Toast.makeText(set_profilepic.this, "Select a New Photo", Toast.LENGTH_SHORT).show();
                                                              }
                                                          });
                                                      } else {

                                                          runOnUiThread(new Runnable() {
                                                              @Override
                                                              public void run() {
                                                                  pd = new ProgressDialog(set_profilepic.this, ProgressDialog.STYLE_SPINNER);
                                                                  pd.setMessage("Uploading... ");
                                                                  pd.setIcon(R.drawable.upload);
                                                                  pd.show();
                                                              }


                                                          });

                                                          FileInputStream fis = (FileInputStream) (getContentResolver().openInputStream(myuri));
                                                          //        File f2 = new File(Environment.getExternalStorageDirectory().getPath()+File.separator+"IM"+File.separator+"ProfilePics"+File.separator + GlobalApp.PHONE + ".jpg");

                                                          File p = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "IM" + File.separator + "ProfilePics");
                                                          if (!p.exists()) {
                                                              p.mkdirs();
                                                          }
                                                          FileOutputStream fs = new FileOutputStream(p + File.separator + GlobalApp.PHONE + ".jpg");

                                                          long len = fis.available();
                                                          Socket socket = new Socket(GlobalApp.IP, GlobalApp.PORT);
                                                          DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                                                          DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                                                          dataOutputStream.writeBytes("POST /IM/profile_pic?phone=" + phone + " HTTP/1.1\r\n");
                                                          dataOutputStream.writeBytes("HOST: " + GlobalApp.IP + ":" + GlobalApp.PORT + "\r\n");
                                                          dataOutputStream.writeBytes("Content-Length: " + len + "\r\n");
                                                          dataOutputStream.writeBytes("Content-Type: image/jpg\r\n");
                                                          dataOutputStream.writeBytes("\r\n");
                                                          byte b[] = new byte[10000];
                                                          int r;
                                                          while (true) {
                                                              r = fis.read(b, 0, 10000);
                                                              if (r == -1) {
                                                                  break;
                                                              }
                                                              dataOutputStream.write(b, 0, r);//Writing File To Server
                                                              fs.write(b, 0, r);//Writing File In Device
                                                          }
                                                          dataOutputStream.flush();
                                                          fs.flush();
                                                          while (true) {
                                                              String s = dataInputStream.readLine();
                                                              if (s == null || s == "") {
                                                                  break;
                                                              }
                                                          }
                                                          final String msg = dataInputStream.readLine();

                                                      }
                                                  } catch (Exception ex) {
                                                      ex.printStackTrace();
                                                  }
                                                  runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          pd.dismiss();
                                                          l1.setVisibility(View.GONE);
                                                          l2.setVisibility(View.GONE);

                                                      }
                                                  });

                                              }
                                              }).start();
                                          }
            });
            cn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                  /*  File p=new File(Environment.getExternalStorageDirectory().getPath()+"/"+GlobalApp.PHONE+".jpg");
                    myuri = Uri.fromFile(p);
                    imv.setImageURI(myuri);*/
                }
            });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(requestCode==20)
        {
            if (resultCode==RESULT_OK)
            {

                imv.setImageURI(myuri);
                l2.setVisibility(View.VISIBLE);
            }
        }
        else
        if(requestCode==10)
        {
            if(resultCode==RESULT_OK)
            {
               myuri=data.getData();

                imv.setImageURI(myuri);
                l2.setVisibility(View.VISIBLE);

            }
        }
    }


}
