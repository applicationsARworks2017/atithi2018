package com.mars.myguest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mars.myguest.Activity.Admin_Hotel_List;
import com.mars.myguest.Pojo.Hotel_List;
import com.mars.myguest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HotelListAdapter extends BaseAdapter{
    Context context;
    ArrayList<Hotel_List> hotel_list;
    Holder holder;

    public HotelListAdapter(Admin_Hotel_List hotelList, ArrayList<Hotel_List> hotel_list) {
        this.context=hotelList;
        this.hotel_list=hotel_list;

    }

    @Override
    public int getCount() {
        return hotel_list.size();
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
        TextView hotelname, hoteladdress;
        ImageView img_circle;
        public Holder() {
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Hotel_List _pos = hotel_list.get(position);
        holder = new Holder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.all_hotel_list, viewGroup, false);
            holder.hotelname=(TextView)convertView.findViewById(R.id.hotelname);
            holder.hoteladdress=(TextView)convertView.findViewById(R.id.hoteladdress);
            holder.img_circle=(ImageView)convertView.findViewById(R.id.logo);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.hotelname.setTag(position);
        holder.hoteladdress.setTag(position);
        holder.img_circle.setTag(position);

        holder.hotelname.setText(_pos.getHotel_name());
        holder.hoteladdress.setText(_pos.getHotel_address());
        if(!_pos.getHotel_photo().isEmpty()) {
            Picasso.with(context).load(_pos.getHotel_photo()).into(holder.img_circle);
        }
        else {
            holder.img_circle.setImageResource(R.drawable.no_image);
        }

        return convertView;
    }
}
