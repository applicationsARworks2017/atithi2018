package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mars.myguest.R;
import com.mars.myguest.Util.CheckInternet;
import com.mars.myguest.Util.Constants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Add_Room extends AppCompatActivity {
    EditText room_no, room_no_bed, room_price;
    Button add;
    RelativeLayout linn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_room);

        room_no = (EditText) findViewById(R.id.room_no);
        room_no_bed = (EditText) findViewById(R.id.room_no_bed);
        room_price = (EditText) findViewById(R.id.room_price);
        add = (Button) findViewById(R.id.add);
        linn = (RelativeLayout) findViewById(R.id.linn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(room_no.getText().toString().trim().length()<=0){
                    showSnackBar("Enter Room Number");
                }
                else if(room_price.getText().toString().trim().length()<=0){
                    showSnackBar("Enter Price");
                }
                addroom();
            }
        });
    }

    private void addroom() {
        String rom_no = room_no.getText().toString().trim();
        String no_bed =room_no_bed.getText().toString().trim();
        String rom_price = room_price.getText().toString().trim();
        if (CheckInternet.getNetworkConnectivityStatus(Add_Room.this)) {
            String hotel_id = Admin_Hotel_List.hotelid;
            new addrom().execute(hotel_id,String.valueOf(rom_no) ,String.valueOf(rom_price) );

        } else {
            showSnackBar("No Internet");
        }
    }

    private class addrom extends AsyncTask<String, Void, Void> {

        private static final String TAG = "ServiceProvider";
        ProgressDialog progressDialog;

        int server_status;
        String server_message;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(Add_Room.this, "Loading", "Please wait...");
            }

        }

        @Override
        protected Void doInBackground(String... params) {


            try {
                InputStream in = null;
                int resCode = -1;
                String link = Constants.BASEURL + Constants.ADD_ROOM;
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
                        .appendQueryParameter("hotel_id", params[0])
                        .appendQueryParameter("room_no", params[1])
                        .appendQueryParameter("price", params[2]);
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
   {
    "room": {
        "hotel_id": 1,
        "room_no": "102",
        "price": 100,
        "created": "2018-04-18T00:22:40+00:00",
        "modified": "2018-04-18T00:22:40+00:00",
        "id": 1
    },
    "res": {
        "message": "The room has been saved.",
        "status": 1
    }
}*/

                if (response != null && response.length() > 0) {
                    JSONObject res = new JSONObject(response.trim());
                    JSONObject ress=res.getJSONObject("res");
                    server_status = ress.optInt("status");
                    if(server_status==1) {
                        server_message = "Room Added Successfully";
                    }
                    else{
                        server_message = "Failed";
                    }

                }
                return null;

            } catch (Exception exception) {
                Log.e(TAG, "LoginAsync : doInBackground", exception);
                server_message="Connectivity Isuue";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void user) {
            super.onPostExecute(user);
            progressDialog.cancel();

            if (server_status == 1) {
                Toast.makeText(Add_Room.this,server_message,Toast.LENGTH_SHORT).show();
                //showSnackBar(server_message);
                Add_Room.this.finish();
            }
            else{
                showSnackBar(server_message);
            }


        }

    }
    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(linn, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#0091EA"));
        snackbar.show();
    }
}

