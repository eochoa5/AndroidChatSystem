package com.example.edwin.androidchatapp;

/**
 * Created by Edwin on 4/21/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{
    ArrayList<String> msgList;
    Context context;

    private static LayoutInflater inflater=null;
    public ListAdapter(MainActivity mainActivity, ArrayList<String> msgList) {
        // TODO Auto-generated constructor stub
        this.msgList=msgList;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView tv2;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_listview, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textViewUsername);
        holder.tv2=(TextView) rowView.findViewById(R.id.textViewMessage);
        holder.tv.setText("User 1: ");
        //holder.img.setImageResource(imageId[position]);
        holder.tv2.setText(msgList.get(position));
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rowView;
    }

}