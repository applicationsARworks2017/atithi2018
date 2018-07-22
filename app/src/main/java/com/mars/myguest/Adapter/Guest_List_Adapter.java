package com.mars.myguest.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.myguest.Activity.AttachGuest;
import com.mars.myguest.Activity.Expenses;
import com.mars.myguest.Activity.GuestDetails;
import com.mars.myguest.Pojo.Guest_List;
import com.mars.myguest.R;
import com.mars.myguest.Util.Constants;
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
        TextView guset_name,gu_mobile,g_addrss,room_price, room_no,g_intime,ad_text;
        ImageView g_img1,g_img2,pro_img,iv_checkout;
        TextView expense,checkout_yime;
        LinearLayout g_img3,g_img4,g_im5,advancelin;
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
            holder.ad_text=(TextView)convertView.findViewById(R.id.ad_text);
            holder.g_intime=(TextView) convertView.findViewById(R.id.g_intime);
            holder.g_img1=(ImageView) convertView.findViewById(R.id.g_img1);
            holder.g_img2=(ImageView) convertView.findViewById(R.id.g_img2);
            holder.g_img3=(LinearLayout) convertView.findViewById(R.id.g_img3);
            holder.g_img4=(LinearLayout) convertView.findViewById(R.id.g_img4);
            holder.g_im5=(LinearLayout) convertView.findViewById(R.id.g_im5);
            holder.pro_img=(ImageView) convertView.findViewById(R.id.pro_img);
            holder.expense=(TextView) convertView.findViewById(R.id.expense);
            holder.checkout_yime=(TextView) convertView.findViewById(R.id.checkout_yime);
            holder.iv_checkout=(ImageView) convertView.findViewById(R.id.iv_checkout);

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
        holder.g_im5.setTag(position);
        holder.pro_img.setTag(position);
        holder.expense.setTag(position);
        holder.ad_text.setTag(position);
        holder.checkout_yime.setTag(position);
        holder.iv_checkout.setTag(position);

        holder.guset_name.setText("Name:"+" "+_pos.getFirst_name());
        holder.gu_mobile.setText("Mobile:"+" "+_pos.getMobile());
        holder.g_addrss.setText("Add: "+_pos.getAddress());
        holder.room_price.setText("Price: Rs. "+_pos.getPrice());
        holder.room_no.setText(_pos.getRoom_no());
        holder.ad_text.setText("Advance : Rs."+_pos.getAdvance_amonut());
        holder.g_intime.setText(Constants.getOurDate(_pos.getCheckin_time()));
        if(_pos.getGuest_status().contentEquals("Checkin")){
            holder.iv_checkout.setVisibility(View.VISIBLE);
            holder.checkout_yime.setVisibility( View.GONE);
        }
        else{
            holder.iv_checkout.setVisibility(View.GONE);
            holder.checkout_yime.setVisibility( View.VISIBLE);
            holder.checkout_yime.setText(Constants.getOurDate((_pos.getCheckout_time())));
        }
        if(!_pos.getPhoto().isEmpty()) {
            Picasso.with(context).load(_pos.getPhoto())
                    .resize(300,300).into(holder.pro_img);
        }
        else {
            holder.pro_img.setImageResource(R.drawable.no_image);
        }if(!_pos.getDoc_1().isEmpty()) {
            Picasso.with(context).load(_pos.getDoc_1())
                    .resize(300,300).into(holder.g_img1);
        }
        else {
            holder.g_img2.setImageResource(R.drawable.no_image);
        }if(!_pos.getDoc_2().isEmpty()) {
            Picasso.with(context).load(_pos.getDoc_2())
                    .resize(300,300).into(holder.g_img2);
        }

        holder.g_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,GuestDetails.class);
                intent.putExtra("GNAME",_pos.getFirst_name());
                context.startActivity(intent);
            }
        });

        holder.expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Expenses.class);
                intent.putExtra("GUESTID",_pos.getGuest_id());
                intent.putExtra("GUESTNAME",_pos.getFirst_name());
                intent.putExtra("ROOMNUMBER",_pos.getRoom_no());
                context.startActivity(intent);
            }
        });
        holder.g_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,AttachGuest.class);
                intent.putExtra("GUESTID",_pos.getGuest_id());
                context.startActivity(intent);
            }
        });
        holder.pro_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showImagedialoug(_pos.getPhoto());
            }
        });holder.g_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showImagedialoug(_pos.getDoc_1());
            }
        });holder.g_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showImagedialoug(_pos.getDoc_2());
            }
        });


        return convertView;
    }

    private void showImagedialoug(String uri) {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.visitor_image);
        ImageView im_visitor=(ImageView)dialog.findViewById(R.id.im_visitor);
        TextView tv_details=(TextView)dialog.findViewById(R.id.tv_details);
        TextView ok=(TextView)dialog.findViewById(R.id.ok);
        if(uri!=null) {
            Picasso.with(context).load(uri).into(im_visitor);
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
