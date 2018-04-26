package com.mars.myguest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mars.myguest.Activity.Admin_Room_List;
import com.mars.myguest.Pojo.Room_List;
import com.mars.myguest.R;

import java.util.ArrayList;

public class Room_List_Adapter extends BaseAdapter {
    Context context;
    Holder holder;
    ArrayList<Room_List> room_list;

    public Room_List_Adapter(Admin_Room_List admin_room_list, ArrayList<Room_List> room_list) {
        this.context=admin_room_list;
        this.room_list=room_list;
    }

    @Override
    public int getCount() {
        return room_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class Holder {
        TextView room_price, room_bed;
        TextView room_no;
        public Holder() {
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Room_List _pos = room_list.get(position);
        holder = new Holder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.all_room_list, viewGroup, false);
            holder.room_price=(TextView)convertView.findViewById(R.id.room_price);
            holder.room_bed=(TextView)convertView.findViewById(R.id.room_bed);
            holder.room_no=(TextView) convertView.findViewById(R.id.room_no);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.room_price.setTag(position);
        holder.room_bed.setTag(position);
        holder.room_no.setTag(position);

        holder.room_price.setText("Room Price:"+" "+_pos.getRoom_price());
        holder.room_bed.setText("No. of Bed:"+" "+"Not Given");
        holder.room_no.setText(_pos.getRoom_no());



        return convertView;
    }
}
