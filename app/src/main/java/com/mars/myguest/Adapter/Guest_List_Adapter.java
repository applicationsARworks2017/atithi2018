package com.mars.myguest.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mars.myguest.Activity.AttachGuest;
import com.mars.myguest.Activity.Checkout;
import com.mars.myguest.Activity.Expenses;
import com.mars.myguest.Activity.GuestDetails;
import com.mars.myguest.Activity.Home;
import com.mars.myguest.Activity.Register;
import com.mars.myguest.Activity.WaitingPage;
import com.mars.myguest.Pojo.Expensedetails;
import com.mars.myguest.Pojo.Guest_List;
import com.mars.myguest.R;
import com.mars.myguest.Util.APIManager;
import com.mars.myguest.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Guest_List_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Guest_List> guest_list;
    Holder holder;
    ArrayList<Expensedetails> expensedetailsArrayList;
    String hotel_id;
    String expenses_amount,check_in,day_no,checkout;
    String adavance_amount,admin_discount,room_amount,expenses_status,expenses_amount_new;

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

            hotel_id = context.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.HOTEL_ID, null);

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
                intent.putExtra("G_ID",_pos.getGuest_id());
                intent.putExtra("G_PHONE",_pos.getGuest_id());
                intent.putExtra("G_PIC",_pos.getPhoto());
                intent.putExtra("G_DOC1",_pos.getDoc_1());
                intent.putExtra("G_DOC2",_pos.getDoc_2());
                intent.putExtra("G_SIGN",_pos.getSignature());
                intent.putExtra("G_DOB",_pos.getDob());
                intent.putExtra("G_ADD",_pos.getAddress()+","+_pos.getState()+","+_pos.getCountry());
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

        holder.iv_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
                String days;

                try {
                    //Date date1 = myFormat.parse(_pos.getCheckin_time().substring(0,10));
                    Date date1 = myFormat.parse(_pos.getCheckin_time());
                    Date date2 =new Date();
                    long diff = date2.getTime() - date1.getTime();
                    days=String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                    final String guest_id= _pos.getGuest_id();
                    final String guest_name= _pos.getFirst_name();
                    final String trans_id= _pos.getTr_id();
                     check_in= _pos.getCheckin_time();
                     checkout= myFormat.format(date2);
                    final String actualdate= myFormat.format(date2);
                    final String room_id= _pos.getRoom_id();
                     day_no= days;




                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.setMessage("Loading Details. Please wait...");
                    pd.show();

                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("guest_id",guest_id );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new APIManager().postJSONArrayAPI(Constants.BASEURL+Constants.EXP_LIST,"expences",jsonObject, Expensedetails.class,context,
                            new APIManager.APIManagerInterface() {
                                @Override
                                public void onSuccess(Object resultObj) {
                                    expensedetailsArrayList =(ArrayList<Expensedetails>) resultObj;
                                    pd.dismiss();
                                    expenses_amount=expensedetailsArrayList.get(expensedetailsArrayList.size()-1).getTotal_amount();
                                    //.UpdateGuestTransaction updateGuestTransaction = new Checkout.UpdateGuestTransaction();
                                    //updateGuestTransaction.execute(trans_id,chekin_time,checkout_time,hotel_id,room_id,days);
                                    calltocalculation(guest_id,guest_name,trans_id,check_in,actualdate,room_id,day_no);

                                }


                                @Override
                                public void onError(String error) {
                                   // tv_expenses.setText("No expenses");
                                    //tv_expenses.setText("Expenses : Rs. 0");
                                    expenses_amount="0";
                                    //Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                    //Checkout.UpdateGuestTransaction updateGuestTransaction = new Checkout.UpdateGuestTransaction();
                                    //updateGuestTransaction.execute(trans_id,chekin_time,checkout_time,hotel_id,room_id,days);
                                    calltocalculation(guest_id,guest_name,trans_id,check_in,actualdate,room_id,day_no);

                                }
                            });


                    /*Intent intent = new Intent(context,Checkout.class);
                    intent.putExtra("GUEST_ID",_pos.getGuest_id());
                    intent.putExtra("GUEST_NAME",_pos.getFirst_name());
                    intent.putExtra("TRANS_ID",_pos.getTr_id());
                    intent.putExtra("CHECKIN_TIME",_pos.getCheckin_time());
                    intent.putExtra("CHECKOUT_TIME",myFormat.format(date2));
                    intent.putExtra("ROOM_ID",_pos.getRoom_id());
                    intent.putExtra("ROOM_NO",_pos.getRoom_no());
                    intent.putExtra("DAYS",days);
                    context.startActivity(intent);*/
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return convertView;
    }

    private void calltocalculation(String guest_id, String guest_name, String trans_id, String check_in, String actualdate, String room_id, String day_no) {

        UpdateGuestTransaction updateGuestTransaction = new UpdateGuestTransaction();
        updateGuestTransaction.execute(trans_id,check_in,checkout,hotel_id,room_id,day_no);
    }

    /*private void callToAPI(final String tr_id, final String checkin_time, String format, String hotel_id, String room_id, final String days, final String name) {


        UpdateGuestTransaction updateGuestTransaction = new UpdateGuestTransaction();
        updateGuestTransaction.execute(tr_id,checkin_time,format,hotel_id,room_id,days,name);
    }*/

    /*private void showFinalDialouge(String guest_id, String first_name, String checkin_time, String days) {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.guest_chk_out_details);
        TextView tv_checkin_time=(TextView)dialog.findViewById(R.id.tv_checkin_time);
        TextView tv_nodays=(TextView)dialog.findViewById(R.id.tv_nodays);
        TextView tv_admindiscount=(TextView)dialog.findViewById(R.id.tv_admindiscount);
        TextView tv_discount=(TextView)dialog.findViewById(R.id.tv_discount);

        tv_checkin_time.setText(checkin_time);
        tv_nodays.setText(days);
        tv_admindiscount.setText("0");
        tv_discount.setText("0");
        dialog.show();

    }*/

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

    private class UpdateGuestTransaction extends AsyncTask<String, Void, Void> {

        private static final String TAG = "Get Details";
        int server_status;
        String server_message;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(context, "Loading", "Please wait...");
            }

        }

        @Override
        protected Void doInBackground(String... params) {


            try {
                InputStream in = null;
                int resCode = -1;
                String link = Constants.BASEURL + Constants._GUEST_EDIT;
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setAllowUserInteraction(false);
                conn.setInstanceFollowRedirects(true);
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("id", params[0])
                        .appendQueryParameter("checkin", params[1])
                        .appendQueryParameter("checkout", params[2])
                        .appendQueryParameter("hotel_id", params[3])
                        .appendQueryParameter("room_id", params[4])
                        .appendQueryParameter("no_of_days", params[5]);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    in = conn.getInputStream();
                }
                if (in == null) {
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String response = "", data = "";

                while ((data = reader.readLine()) != null) {
                    response += data + "\n";
                }

                Log.i(TAG, "Response : " + response);


                /*
                *
                * {
    "res": {
        "message": "The guest details has been updated.",
        "status": 1
    },
    "guestTransaction": {
        "id": 6,
        "guest_id": 17,
        "room_id": 7,
        "hotel_id": 1,
        "checkin": "2018-08-14T03:20:00+00:00",
        "checkout": "17-08-2018 01:11 PM",
        "advance_amonut": 100,
        "total_amount": 4999.95,
        "discount": null,
        "expense_amount": 100,
        "admin_discount": null,
        "payable_amount": null,
        "no_of_days": 3,
        "added_by": 3,
        "expense_status": "1",
        "created": "2018-08-13T11:20:30+00:00",
        "modified": "2018-08-19T03:49:24+00:00"
    }
}
                * */

                if (response != null && response.length() > 0) {
                    JSONObject res = new JSONObject(response);


                    JSONObject response_obj = res.getJSONObject("res");
                    server_status=response_obj.getInt("status");
                    if(server_status==1){

                        JSONObject tr_object = res.getJSONObject("guestTransaction");
                        adavance_amount = tr_object.getString("advance_amonut");
                        room_amount = tr_object.getString("total_amount");
                        admin_discount = tr_object.getString("admin_discount");
                        expenses_status = tr_object.getString("expense_status");
                        //discount = tr_object.getString("advance_amonut");

                    }
                    else{
                        server_message="No data found ! Try again";
                    }

                }
                return null;

            } catch(Exception exception){
                Log.e(TAG, "LoginAsync : doInBackground", exception);
            }

            return null;
        }

        @Override
        protected void onPostExecute (Void user){
            super.onPostExecute(user);
            progressDialog.cancel();
            if(server_status==1) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.guest_chk_out_details);
                TextView tv_checkin_time=(TextView)dialog.findViewById(R.id.tv_checkin_time);
                TextView tv_nodays=(TextView)dialog.findViewById(R.id.tv_nodays);
                TextView tv_admindiscount=(TextView)dialog.findViewById(R.id.tv_admindiscount);
                TextView tv_expenses=(TextView)dialog.findViewById(R.id.tv_expenses);
                TextView tv_discount=(TextView)dialog.findViewById(R.id.tv_discount);
                TextView tv_room_advance=(TextView)dialog.findViewById(R.id.tv_room_advance);
                TextView tv_room_amount=(TextView)dialog.findViewById(R.id.tv_room_amount);
                TextView tv_payble_amount=(TextView)dialog.findViewById(R.id.tv_payble_amount);

                tv_checkin_time.setText(check_in);
                tv_nodays.setText(day_no);
                tv_admindiscount.setText("0");
                tv_discount.setText("Discount : 0");
                tv_discount.setVisibility(View.GONE);
                dialog.show();


                if (expenses_status != null) {
                    if (expenses_status.contentEquals("1")) {
                        tv_expenses.setText("Expenses Paid");
                        expenses_amount="0";
                        tv_expenses.setTextColor(Color.parseColor("#3DC52A"));
                    }
                }
                if(! admin_discount.contentEquals("null") ) {
                    tv_admindiscount.setText("Admin Discount: Rs. "+admin_discount);
                }
                else{
                    admin_discount="0";
                    tv_admindiscount.setText("Admin Discount: Rs. 0");

                }
                if(adavance_amount!=null){
                    tv_room_advance.setText("Advance:  Rs. "+adavance_amount);
                }
                else{
                    adavance_amount="0";
                    tv_room_advance.setText("Advance: Rs. 0");
                }
                tv_checkin_time.setText("Checkin Time: "+check_in);
                tv_nodays.setText("No Of Days: "+day_no);
                tv_room_amount.setText("Room Cost Rs.: "+room_amount);
                Float total_payble=(Float.valueOf(room_amount) + Float.valueOf(expenses_amount))
                        - (Float.valueOf(admin_discount) + Float.valueOf(adavance_amount));
                tv_payble_amount.setText("Total payble amount: "+ String.valueOf(total_payble));
            }
            else{

            }

        }
    }

}
