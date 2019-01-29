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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mars.myguest.R;
import com.mars.myguest.SplashActivity;
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

public class Add_expenses extends AppCompatActivity {
    EditText room_num, exp_details,exp_amount;
    Button addexpenses;
    FrameLayout addlin;
    String guest_id, guest_name, guest_room;
    Spinner spin_expense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_expenses);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            guest_id = extras.getString("GUESTID");
            guest_name = extras.getString("GUESTNAME");
            guest_room = extras.getString("ROOMNUMBER");
            // and get whatever type user account id is
        }

        room_num = (EditText) findViewById(R.id.room_num);
        room_num.setEnabled(false);
        room_num.setText(guest_room);
        spin_expense=(Spinner)findViewById(R.id.spin_expense);
        exp_details = (EditText) findViewById(R.id.exp_details);
        exp_amount = (EditText) findViewById(R.id.exp_amount);
        addexpenses = (Button) findViewById(R.id.add);
        addlin = (FrameLayout) findViewById(R.id.addlin);
        addexpenses=(Button)findViewById(R.id.addexpenses);
        addexpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String details="No Details";
                String spinvalue=spin_expense.getSelectedItem().toString();
                if(spinvalue.contentEquals(" - - Select Type - - ")) {
                    showSnackBar("Please Select Type");
                }
                else if(exp_amount.getText().toString().trim().length()<=0) {
                    showSnackBar("Please Enter the Amount");
                }
                else {
                    if(exp_details.getText().toString().trim().length()>0) {
                        details= exp_details.getText().toString().trim();
                    }
                    addexpenses(guest_id,spinvalue+": "+details,exp_amount.getText().toString().trim());
                }
            }
        });
    }

    private void addexpenses(String guest_id, String s, String exp_amount) {

        if (CheckInternet.getNetworkConnectivityStatus(Add_expenses.this)) {
            new addexpenses().execute(guest_id,s,exp_amount);

        } else {
            showSnackBar("No Internet");
        }
    }

    private class addexpenses extends AsyncTask<String, Void, Void> {

        private static final String TAG = "ServiceProvider";
        ProgressDialog progressDialog;

        int server_status;
        String server_message;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(Add_expenses.this, "Loading", "Please wait...");
            }

        }

        @Override
        protected Void doInBackground(String... params) {


            try {
                InputStream in = null;
                int resCode = -1;
                String link = Constants.BASEURL + Constants.ADD_EXPENSE;
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
                        .appendQueryParameter("guest_id", params[0])
                        .appendQueryParameter("details", params[1])
                        .appendQueryParameter("amount", params[2]);
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
                * "res": {
        "message": "The expences has been saved.",
        "status": 1
    }
                * */


                    if (response != null && response.length() > 0) {
                        JSONObject res = new JSONObject(response.trim());
                        JSONObject ress = res.getJSONObject("res");
                        server_status = ress.optInt("status");
                        if (server_status == 1) {
                            server_message = "Expenses Added";
                        } else {
                            server_message = "Failed";
                        }

                    }
                    return null;

                } catch(Exception exception){
                    Log.e(TAG, "LoginAsync : doInBackground", exception);
                    server_message = "Connectivity Isuue";
                }

                return null;
            }

            @Override
            protected void onPostExecute (Void user){
                super.onPostExecute(user);
                progressDialog.cancel();

                if (server_status == 1) {
                    Toast.makeText(Add_expenses.this, server_message, Toast.LENGTH_SHORT).show();
                    //showSnackBar(server_message);
                   // Add_expenses.this.finish();
                    Intent intent = new Intent(Add_expenses.this, Expenses.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("GUESTID",guest_id);
                    intent.putExtra("GUESTNAME",guest_name);
                    intent.putExtra("ROOMNUMBER",guest_room);
                    startActivity(intent);
                } else {
                    showSnackBar(server_message);
                }


            }

        }

    void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(addlin, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#0091EA"));
        snackbar.show();
    }
}

