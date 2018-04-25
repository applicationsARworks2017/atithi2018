package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mars.myguest.Adapter.AllHotelsAdapter;
import com.mars.myguest.Pojo.Hotels;
import com.mars.myguest.R;
import com.mars.myguest.Util.APIManager;
import com.mars.myguest.Util.CheckInternet;
import com.mars.myguest.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllHotels extends AppCompatActivity {
    ListView lv_hotels;
    SwipeRefreshLayout swipehotels;
    RelativeLayout allHotels;
    ArrayList<Hotels> hotelsArrayList;
    AllHotelsAdapter allHotelsAdapter;
    String page_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hotels);
        swipehotels=(SwipeRefreshLayout)findViewById(R.id.swipehotels);
        lv_hotels=(ListView)findViewById(R.id.lv_hotels);
        allHotels=(RelativeLayout)findViewById(R.id.rel_allhotels);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            page_name = extras.getString("PAGE");
            // and get whatever type user account id is
        }
        if(CheckInternet.getNetworkConnectivityStatus(AllHotels.this)){
            callAPI();
        }
        else{
            showSnackBar("No Internet");
        }
        lv_hotels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(page_name.contentEquals("registration")){
                    Hotels hotels=(Hotels) lv_hotels.getItemAtPosition(i);
                    Register.orgname.setText(hotels.getName());
                    Register.hotel_id=hotels.getId();
                    AllHotels.this.finish();
                }
            }
        });
    }

    private void callAPI() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Hotels. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("","" );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIManager().postJSONArrayAPI(Constants.BASEURL+Constants.HOTELLIST,"hotels",jsonObject, Hotels.class,this,
                new APIManager.APIManagerInterface() {
                    @Override
                    public void onSuccess(Object resultObj) {
                        hotelsArrayList=(ArrayList<Hotels>) resultObj;
                        allHotelsAdapter = new AllHotelsAdapter(AllHotels.this,hotelsArrayList );
                        lv_hotels.setAdapter(allHotelsAdapter);
                        pd.cancel();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                });
    }

    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(allHotels, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#0091EA"));
        snackbar.show();
    }
}
