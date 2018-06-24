package com.mars.myguest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mars.myguest.Activity.Statelist;
import com.mars.myguest.Pojo.States;
import com.mars.myguest.R;

import java.util.ArrayList;

/**
 * Created by Amaresh on 6/24/18.
 */

public class StateAdapter extends BaseAdapter {
    Context _context;
    ArrayList<States> mylist;
    Holder holder;

    public StateAdapter(Statelist statelist, ArrayList<States> statesArrayList) {
        this._context=statelist;
        this.mylist=statesArrayList;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int i) {
        return mylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        TextView statename,countryname;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final States _pos = mylist.get(position);
        holder = new Holder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) _context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.state_list, viewGroup, false);
            holder.statename=(TextView)convertView.findViewById(R.id.state_name);
            holder.countryname=(TextView) convertView.findViewById(R.id.country_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.statename.setTag(position);
        holder.countryname.setTag(position);
        holder.statename.setText(_pos.getState_name());
        holder.countryname.setText(_pos.getCountry_name());



        return convertView;
    }
}
