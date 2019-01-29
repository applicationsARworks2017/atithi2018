package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mars.myguest.Adapter.AllHotelsAdapter;
import com.mars.myguest.Adapter.ExpensesAdapter;
import com.mars.myguest.Pojo.Expensedetails;
import com.mars.myguest.Pojo.Hotels;
import com.mars.myguest.R;
import com.mars.myguest.Util.APIManager;
import com.mars.myguest.Util.CheckInternet;
import com.mars.myguest.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Expenses extends AppCompatActivity {
    RelativeLayout ad_expense;
    String guest_id,guest_name,guest_room;
    TextView roomdetails;
    LinearLayout backex;
    ImageView addexpences;
    SwipeRefreshLayout swip_expenses;
    TextView tv_noexp,total_amount;
    public static TextView text_total;
    ListView lv_expenses;
    ArrayList<Expensedetails> expensedetailsArrayList;
    ExpensesAdapter expensesAdapter;
    android.support.v7.widget.Toolbar expensestool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        ad_expense=(RelativeLayout)findViewById(R.id.ad_expense);
        addexpences=(ImageView)findViewById(R.id.addexpences);
        roomdetails=(TextView)findViewById(R.id.roomdetails);
        text_total=(TextView)findViewById(R.id.text_total);
        swip_expenses=(SwipeRefreshLayout)findViewById(R.id.swip_expenses);
        total_amount=(TextView)findViewById(R.id.text_total);
        tv_noexp=(TextView)findViewById(R.id.tv_noexp);
        lv_expenses=(ListView)findViewById(R.id.lv_expenses);
        expensestool=(android.support.v7.widget.Toolbar)findViewById(R.id.expensestool);
       // setSupportActionBar(expensestool);
        expensestool.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        expensestool.setTitle("");
       // backex=(LinearLayout)expensestool.findViewById(R.id.backex);
        /*backex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        expensestool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // finish();
                Intent intent = new Intent(Expenses.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        text_total.setText("Total : Rs.0");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            guest_id = extras.getString("GUESTID");
            guest_name = extras.getString("GUESTNAME");
            guest_room = extras.getString("ROOMNUMBER");
            // and get whatever type user account id is
        }
        roomdetails.setText(guest_name+","+guest_room);
        addexpences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Expenses.this, Add_expenses.class);
                intent.putExtra("GUESTID",guest_id);
                intent.putExtra("GUESTNAME",guest_name);
                intent.putExtra("ROOMNUMBER",guest_room);
                startActivity(intent);
            }
        });
        getExpensesDetails();
        swip_expenses.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swip_expenses.setRefreshing(false);
                getExpensesDetails();
            }
        });
    }

    private void getExpensesDetails() {
        swip_expenses.setVisibility(View.VISIBLE);
        tv_noexp.setVisibility(View.GONE);
        if(CheckInternet.getNetworkConnectivityStatus(Expenses.this)){
            callAPI();
        }
        else{
            Toast.makeText(Expenses.this,"No Internet",Toast.LENGTH_LONG).show();
        }
    }

    private void callAPI() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Expenses. Please wait...");
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
                        expensesAdapter = new ExpensesAdapter(Expenses.this,expensedetailsArrayList );
                        lv_expenses.setAdapter(expensesAdapter);
                        pd.dismiss();
                        total_amount.setText("Total : Rs. "+expensedetailsArrayList.get(expensedetailsArrayList.size()-1).getTotal_amount());
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(),"No Expenses",Toast.LENGTH_LONG).show();
                        pd.dismiss();
                        swip_expenses.setVisibility(View.GONE);
                        tv_noexp.setVisibility(View.VISIBLE);
                    }
                });
    }
}
