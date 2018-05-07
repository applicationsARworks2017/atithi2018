package com.mars.myguest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mars.myguest.Pojo.Guest_List;
import com.mars.myguest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Guest_List_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Guest_List> guest_list;
    Holder holder;
    public Guest_List_Adapter(Context context, ArrayList<Guest_List> guest_list) {
        this.context=context;
        this.guest_list=guest_list;
    }

    @Override
    public int getCount() {
        return guest_list.size();
    }

    @Override
    public Object getItem(int i) {
        return guest_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder {
        TextView guset_name,gu_mobile,g_addrss,room_price, room_no,g_intime;
        ImageView g_img1,g_img2,g_img3,g_img4;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Guest_List _pos = guest_list.get(position);
        holder = new Holder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.fragment_guest, viewGroup, false);
            holder.guset_name=(TextView)convertView.findViewById(R.id.g_name);
            holder.gu_mobile=(TextView)convertView.findViewById(R.id.g_phone);
            holder.g_addrss=(TextView)convertView.findViewById(R.id.g_address);
            holder.room_price=(TextView)convertView.findViewById(R.id.g_price);
            holder.room_no=(TextView)convertView.findViewById(R.id.g_romno);
            holder.g_intime=(TextView) convertView.findViewById(R.id.g_intime);
            holder.g_img1=(ImageView) convertView.findViewById(R.id.g_img1);
            holder.g_img2=(ImageView) convertView.findViewById(R.id.g_img2);
            holder.g_img3=(ImageView) convertView.findViewById(R.id.g_img3);
            holder.g_img4=(ImageView) convertView.findViewById(R.id.g_img4);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.guset_name.setTag(position);
        holder.gu_mobile.setTag(position);
        holder.g_addrss.setTag(position);
        holder.room_price.setTag(position);
        holder.room_no.setTag(position);
        holder.g_intime.setTag(position);
        holder.g_img1.setTag(position);
        holder.g_img2.setTag(position);
        holder.g_img3.setTag(position);
        holder.g_img4.setTag(position);

        holder.guset_name.setText("Name:"+" "+_pos.getFirst_name()+" "+_pos.getLast_name());
        holder.gu_mobile.setText("Mobile:"+" "+_pos.getMobile());
        holder.g_addrss.setText(_pos.getAddress()+","+" "+_pos.getCity());
        holder.room_price.setText("Not Given");
        holder.room_no.setText("Not Given");
        holder.g_intime.setText("Not Given");
        if(!_pos.getPhoto().isEmpty()) {
            Picasso.with(context).load(_pos.getPhoto()).into(holder.g_img1);
        }
        else {
            holder.g_img1.setImageResource(R.drawable.no_image);
        }if(!_pos.getDoc_1().isEmpty()) {
            Picasso.with(context).load(_pos.getDoc_1()).into(holder.g_img2);
        }
        else {
            holder.g_img2.setImageResource(R.drawable.no_image);
        }if(!_pos.getDoc_2().isEmpty()) {
            Picasso.with(context).load(_pos.getPhoto()).into(holder.g_img3);
        }
        else {
            holder.g_img3.setImageResource(R.drawable.no_image);
        }if(!_pos.getSignature().isEmpty()) {
            Picasso.with(context).load(_pos.getPhoto()).into(holder.g_img4);
        }
        else {
            holder.g_img4.setImageResource(R.drawable.no_image);
        }


        return convertView;
    }
}
