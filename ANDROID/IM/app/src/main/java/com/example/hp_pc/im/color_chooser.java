package com.example.hp_pc.im;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class color_chooser extends AppCompatActivity {

    Toolbar tools;
    TextView tools_title;
    GridView gv;
    ArrayList<color> coloral=new ArrayList<>();
    mycolorad ad;
    String colors[]={"#3F51B5","#009900","#90AFC5","#F98866","#003B46","#98DBC6","#EB8A44","#F52549","#50312F","#34888C","#B9D9C3","#805A3B","#7F152E","#000B29","#00CFFA","#C5001A","#919636","#C5001A","#2F2E33","#756867","#E99787"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(GlobalApp.theme);
        setContentView(R.layout.activity_color_chooser);
        tools=(Toolbar) findViewById(R.id.tools);
        tools_title=(TextView) findViewById(R.id.tools_title);
        tools_title.setText("Themes");
        setSupportActionBar(tools);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        gv=(GridView) findViewById(R.id.gridview);
        ad=new mycolorad();
        for(int i=0;i<colors.length;i++)
        {
            coloral.add(new color(colors[i],false));
        }

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
            {

                coloral.get(position).flag=true;
                ad.notifyDataSetChanged();

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(color_chooser.this,GlobalApp.theme);
                builder.setTitle("Confirmation");
                builder.setMessage("App Will Restart....");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GlobalApp.theme = 2131361981 + position;
                        Log.d("Value", GlobalApp.theme + "");
                        try {
                            FileOutputStream fOs = openFileOutput("color.txt", MODE_PRIVATE);
                            DataOutputStream ds = new DataOutputStream(fOs);
                            ds.writeInt(GlobalApp.theme);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        setTheme(GlobalApp.theme);

                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create();
                builder.show();



            }
        });
        gv.setAdapter(ad);
    }
    class mycolorad extends BaseAdapter
    {

        @Override
        public int getCount() {
            return coloral.size();
        }

        @Override
        public Object getItem(int position) {
            return coloral.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            convertView=inflater.inflate(R.layout.singlerow_grid, parent, false);

            ImageView circlecolor=(ImageView)convertView.findViewById(R.id.color);
            ImageView tick=(ImageView)convertView.findViewById(R.id.tick);
            if(coloral.get(position).flag)
            {
                tick.setVisibility(View.VISIBLE);
            }
            else
            {
                tick.setVisibility(View.INVISIBLE);

            }

          //  circlecolor.setImageResource(R.drawable.circle);
             //    int c=Integer.valueOf(coloral.get(position).back);
           //  Log.d("Color",c+"");

            circlecolor.setBackgroundColor(Color.parseColor(coloral.get(position).back));




            return convertView;

        }
    }
    class color
    {
        String back;
        boolean flag;

        color(String back,boolean flag)
        {
            this.back=back;
            this.flag=flag;

        }
    }
}
