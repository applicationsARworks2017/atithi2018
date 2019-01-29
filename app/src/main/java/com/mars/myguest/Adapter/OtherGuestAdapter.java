package com.mars.myguest.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mars.myguest.Activity.AllOtherguest;
import com.mars.myguest.Pojo.Hotel_List;
import com.mars.myguest.Pojo.OtherGuest;
import com.mars.myguest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Amaresh on 7/31/18.
 */

public class OtherGuestAdapter extends BaseAdapter {
    ArrayList<OtherGuest> myList;
    Context _context;
    Holder holder;
    public OtherGuestAdapter(AllOtherguest allOtherguest, ArrayList<OtherGuest> guestList) {
        this.myList=guestList;
        this._context=allOtherguest;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        ImageView g_im;
        TextView g_name,g_rel,g_doc;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final OtherGuest _pos = myList.get(i);
        holder = new Holder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) _context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.all_other_list, viewGroup, false);
            holder.g_name=(TextView)convertView.findViewById(R.id.g_name);
            holder.g_rel=(TextView)convertView.findViewById(R.id.g_rel);
            holder.g_doc=(TextView)convertView.findViewById(R.id.g_doc);
            holder.g_im=(ImageView)convertView.findViewById(R.id.g_im);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.g_name.setTag(i);
        holder.g_rel.setTag(i);
        holder.g_doc.setTag(i);
        holder.g_im.setTag(i);

        holder.g_name.setText(_pos.getName());
        holder.g_rel.setText(_pos.getRelation());
        if(!_pos.getPhoto().isEmpty()) {
            Picasso.with(_context).load(_pos.getPhoto()).into(holder.g_im);
        }
        else {
            holder.g_im.setImageResource(R.drawable.no_image);
        }

        holder.g_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagedialoug(_pos.getDocument());
            }
        });
        return convertView;
    }



    private void showImagedialoug(String uri) {
        final Dialog dialog=new Dialog(_context);
        dialog.setContentView(R.layout.visitor_image);
        ImageView im_visitor=(ImageView)dialog.findViewById(R.id.im_visitor);
        TextView tv_details=(TextView)dialog.findViewById(R.id.tv_details);
        TextView ok=(TextView)dialog.findViewById(R.id.ok);
        if(uri!=null) {
            Picasso.with(_context).load(uri).into(im_visitor);
        }
        else {
            im_visitor.setImageResource(R.drawable.no_image);
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
