package com.mars.myguest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mars.myguest.Activity.AllHotels;
import com.mars.myguest.Pojo.Hotels;
import com.mars.myguest.R;
import com.mars.myguest.Util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Amaresh on 4/18/18.
 */

public class AllHotelsAdapter extends BaseAdapter {
    Context _context;
    Holder holder;
    ArrayList<Hotels> hList;
    public AllHotelsAdapter(AllHotels allHotels, ArrayList<Hotels> hotelsArrayList) {
        this._context=allHotels;
        this.hList=hotelsArrayList;
    }

    @Override
    public int getCount() {
        return hList.size();
    }

    @Override
    public Object getItem(int i) {
        return hList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        TextView hotelname,hoteladdress,created;
        ImageView hotel_image;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Hotels _pos=hList.get(i);
        holder=new Holder();
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)_context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.hotel_adapter,viewGroup, false);
            holder.hotel_image=(ImageView) view.findViewById(R.id.hotel_image);
            holder.hotelname=(TextView)view.findViewById(R.id.hotelname);
            holder.hoteladdress=(TextView)view.findViewById(R.id.address);

            view.setTag(holder);
        }
        else {
            holder = (Holder) view.getTag();
        }
        holder.hoteladdress.setTag(i);
        holder.hotelname.setTag(i);
        holder.hotel_image.setTag(i);
        String photo1=_pos.getPhoto();
        if(photo1=="" || photo1==null || photo1.isEmpty()) {
        }
        else{
            Picasso.with(_context)
                    .load(photo1)
                    .placeholder(R.drawable.error)
                    .error(R.drawable.error).into(holder.hotel_image);
        }
        holder.hotelname.setText(_pos.getName());
        holder.hoteladdress.setText(_pos.getAddress());
        return view;
    }
}
