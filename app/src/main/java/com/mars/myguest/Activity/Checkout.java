package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mars.myguest.Adapter.ExpensesAdapter;
import com.mars.myguest.Pojo.Expensedetails;
import com.mars.myguest.R;
import com.mars.myguest.Util.APIManager;
import com.mars.myguest.Util.CheckInternet;
import com.mars.myguest.Util.Constants;

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
import java.util.ArrayList;

public class Checkout extends AppCompatActivity {

    String guest_id,guest_name,trans_id,chekin_time,checkout_time,room_id,days,room_no,hotel_id;
    TextView tv_guest_details,tv_checkin_time,tv_nodays,tv_admindiscount,tv_discount,tv_payble_amount,tv_room_amount,tv_room_advance;
    public static TextView tv_expenses;
    Button bt_dis_req,bt_cancel,bt_checkout;
    ArrayList<Expensedetails> expensedetailsArrayList;
    String adavance_amount,admin_discount,room_amount,expenses_status,expenses_amount;
    Float total_payble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        hotel_id = Checkout.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.HOTEL_ID, null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            guest_id = extras.getString("GUEST_ID");
            guest_name = extras.getString("GUEST_NAME");
            trans_id = extras.getString("TRANS_ID");
            chekin_time = extras.getString("CHECKIN_TIME");
            checkout_time = extras.getString("CHECKOUT_TIME");
            room_id = extras.getString("ROOM_ID");
            days = extras.getString("DAYS");
            room_no = extras.getString("ROOM_NO");
        }

        tv_guest_details=(TextView)findViewById(R.id.tv_guest_details);
        tv_checkin_time=(TextView)findViewById(R.id.tv_checkin_time);
        tv_nodays=(TextView)findViewById(R.id.tv_nodays);
        tv_admindiscount=(TextView)findViewById(R.id.tv_admindiscount);
        tv_discount=(TextView)findViewById(R.id.tv_discount);
        tv_room_amount=(TextView)findViewById(R.id.tv_room_amount);
        tv_room_advance=(TextView)findViewById(R.id.tv_room_advance);
        tv_payble_amount=(TextView)findViewById(R.id.tv_payble_amount);
        tv_expenses=(TextView)findViewById(R.id.tv_expenses);
        bt_dis_req=(Button)findViewById(R.id.bt_dis_req);
        bt_cancel=(Button)findViewById(R.id.bt_cancel);
        bt_checkout=(Button)findViewById(R.id.bt_checkout);

        tv_guest_details.setText(guest_name+", Room- "+room_no);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout.this.finish();
            }
        });

        getExpensesDetails();
    }

    private void getExpensesDetails() {
        if(CheckInternet.getNetworkConnectivityStatus(Checkout.this)){
            callAPI();
        }
        else{
            Toast.makeText(Checkout.this,"No Internet",Toast.LENGTH_LONG).show();
        }
    }

    private void callAPI() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Details. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("guest_id",guest_id );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIManager().postJSONArrayAPI(Constants.BASEURL+Constants.EXP_LIST,"expences",jsonObject, Expensedetails.class,this,
                new APIManager.APIManagerInterface() {
                    @Override
                    public void onSuccess(Object resultObj) {
                        expensedetailsArrayList=(ArrayList<Expensedetails>) resultObj;
                        pd.dismiss();
                        tv_expenses.setText("Expenses : Rs. "+expensedetailsArrayList.get(expensedetailsArrayList.size()-1).getTotal_amount());
                        expenses_amount=expensedetailsArrayList.get(expensedetailsArrayList.size()-1).getTotal_amount();

                        UpdateGuestTransaction updateGuestTransaction = new UpdateGuestTransaction();
                        updateGuestTransaction.execute(trans_id,chekin_time,checkout_time,hotel_id,room_id,days);
                    }


                    @Override
                    public void onError(String error) {
                        tv_expenses.setText("No expenses");
                        tv_expenses.setText("Expenses : Rs. 0");
                        expenses_amount="0";
                        //Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                        pd.dismiss();
                        UpdateGuestTransaction updateGuestTransaction = new UpdateGuestTransaction();
                        updateGuestTransaction.execute(trans_id,chekin_time,checkout_time,hotel_id,room_id,days);

                    }
                });

    }




    private class UpdateGuestTransaction extends AsyncTask<String, Void, Void> {

        private static final String TAG = "Get Details";
        int server_status;
        String server_message;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(Checkout.this, "Loading", "Please wait...");
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
                    tv_checkin_time.setText("Checkin Time: "+chekin_time);
                    tv_nodays.setText("No Of Days: "+days);
                    tv_room_amount.setText("Room Cost Rs.: "+room_amount);
                    total_payble=(Float.valueOf(room_amount) + Float.valueOf(expenses_amount))
                                - (Float.valueOf(admin_discount) + Float.valueOf(adavance_amount));
                    tv_payble_amount.setText("Total payble amount: "+ String.valueOf(total_payble));
                }
                else{

                }

            }
        }
}
