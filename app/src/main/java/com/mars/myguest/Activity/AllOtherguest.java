package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mars.myguest.Adapter.AllHotelsAdapter;
import com.mars.myguest.Adapter.Guest_List_Adapter;
import com.mars.myguest.Adapter.OtherGuestAdapter;
import com.mars.myguest.Pojo.Guest_List;
import com.mars.myguest.Pojo.Hotels;
import com.mars.myguest.Pojo.OtherGuest;
import com.mars.myguest.R;
import com.mars.myguest.Util.APIManager;
import com.mars.myguest.Util.CheckInternet;
import com.mars.myguest.Util.Constants;

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
import java.util.ArrayList;

public class AllOtherguest extends AppCompatActivity {
    String guest_id;
    RelativeLayout linn;
    ArrayList<OtherGuest> guestList;
    OtherGuestAdapter otherGuestAdapter;
    ListView others_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_otherguest);
        linn=(RelativeLayout)findViewById(R.id.linn);
        others_list=(ListView) findViewById(R.id.others_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            guest_id = extras.getString("GUEST_ID");
        }
        if(CheckInternet.getNetworkConnectivityStatus(AllOtherguest.this)){
            getAllOthers();
        }
        else{
            showSnackBar("No Internet");
        }

    }

    private void getAllOthers() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Guests. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("guest_id",guest_id );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIManager().postJSONArrayAPI(Constants.BASEURL+Constants.ATTACH_GUEST_LIST,"guestDetails",jsonObject, OtherGuest.class,this,
                new APIManager.APIManagerInterface() {
                    @Override
                    public void onSuccess(Object resultObj) {
                        guestList=(ArrayList<OtherGuest>) resultObj;
                        otherGuestAdapter = new OtherGuestAdapter(AllOtherguest.this,guestList );
                        others_list.setAdapter(otherGuestAdapter);
                        pd.cancel();
                    }

                    @Override
                    public void onError(String error) {
                        showSnackBar(error);
                        pd.dismiss();
                    }
                });
    }

    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(linn, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#0091EA"));
        snackbar.show();
    }



}
