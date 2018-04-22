package com.example.hp_pc.im;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class all_contacts extends Fragment {


    ListView lv;
    TextView tv,tv1;
    int flag=0;
    myadapter ad;
    myreceiver mybr;
    ImageButton call,message;
    class myadapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return GlobalApp.al.size();
        }

        @Override
        public Object getItem(int position)
        {
            return GlobalApp.al.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            convertView=inflater.inflate(R.layout.single_row, parent, false);
           final contact ct=GlobalApp.al.get(position);
            call=(ImageButton) convertView.findViewById(R.id.call);
            message=(ImageButton) convertView.findViewById(R.id.message);



                convertView.setBackgroundColor(Color.parseColor("#f9f9f9"));


           final  TextView tvname = (TextView)(convertView.findViewById(R.id.textView10));
            final TextView tvphone = (TextView)(convertView.findViewById(R.id.textView11));
            tvname.setText(ct.NAME);
            tvphone.setText(ct.PHONE);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + ct.PHONE));
                    startActivity(callIntent);
                }
            });
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("smsto:"+ct.PHONE);
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    startActivity(smsIntent);
                }
            });
            return convertView;
        }
    }


   public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible && flag==1)
        {
            ad.notifyDataSetChanged();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        return inflater.inflate(R.layout.fragment_all_contacts, container, false);

    }

    @Override
    public void onStart() {

        super.onStart();
        lv=(ListView)getActivity().findViewById(R.id.listView);
        ad=new myadapter();

        mybr = new myreceiver();
        flag=1;
    }

    @Override
    public void onResume()
    {
        IntentFilter inf = new IntentFilter();
        inf.addAction("test");
        getActivity().registerReceiver(mybr, inf);
        super.onResume();
    }

    @Override
    public void onPause()
    {

        getActivity().unregisterReceiver(mybr);
        super.onPause();

    }
    class myreceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            lv.setAdapter(ad);
        }
    }

}
