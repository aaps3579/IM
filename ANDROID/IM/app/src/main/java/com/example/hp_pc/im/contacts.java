package com.example.hp_pc.im;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class contacts extends Fragment
{


    ListView lv;

    int flag=0;
    myadapter2 ad2;

    class myadapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            return GlobalApp.myal.size();
        }

        @Override
        public Object getItem(int position) {
            return GlobalApp.myal.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.single_row2, parent, false);
            mycontacts mt = GlobalApp.myal.get(position);


            ImageView imv = (ImageView) (convertView.findViewById(R.id.imageView2));
            TextView tvname = (TextView) (convertView.findViewById(R.id.textView12));
            TextView tvphone = (TextView) (convertView.findViewById(R.id.textView13));
            TextView tvstatus = (TextView) (convertView.findViewById(R.id.textView14));

                tvname.setText(mt.NAME);
                tvphone.setText(mt.PHONE);
                tvstatus.setText(mt.STATUS);
                imv.setImageBitmap(mt.bmp);



            return convertView;
        }
    }

    public void setMenuVisibility(boolean menuVisible)
    {
        super.setMenuVisibility(menuVisible);
        if (menuVisible && flag == 1)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < GlobalApp.myal.size(); i++) {
                        Log.d("PHONE",GlobalApp.myal.get(i).PHONE);
                        URL aURL = null;
                        try {
                            aURL = new URL("http://" + GlobalApp.IP + ":"+GlobalApp.PORT + "/IM/ProfilePics/" + GlobalApp.myal.get(i).PHONE + ".jpg");
                            URLConnection conn = aURL.openConnection();
                            conn.connect();
                            Log.d("MYMSG", "image fetched");
                            InputStream is = conn.getInputStream();
                            BufferedInputStream bis = new BufferedInputStream(is);

                            Bitmap tempbmp = BitmapFactory.decodeStream(bis);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            tempbmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);

                           GlobalApp.myal.get(i).bmp= BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
                            is.close();
                            bis.close();
                            baos.close();

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ad2.notifyDataSetChanged();

                                }
                            });

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }


            }).start();
    }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);

    }

    @Override
    public void onStart() {

        super.onStart();
        lv=(ListView)getActivity().findViewById(R.id.listView2);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                GlobalApp.fname=GlobalApp.myal.get(position).NAME;
                GlobalApp.fphone=GlobalApp.myal.get(position).PHONE;
                GlobalApp.fstatus=GlobalApp.myal.get(position).STATUS;
                Intent in=new Intent(getActivity().getApplicationContext(),chat_home.class);
                startActivity(in);

            }
        });
        ad2=new myadapter2();
        lv.setAdapter(ad2);
        flag=1;
    }


}
