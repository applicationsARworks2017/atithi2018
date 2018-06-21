package com.mars.myguest.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mars.myguest.R;

public class Expenses extends AppCompatActivity {
    RelativeLayout ad_expense;
    String guest_id,guest_name,guest_room;
    TextView roomdetails;
    LinearLayout backex;
    android.support.v7.widget.Toolbar expensestool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        ad_expense=(RelativeLayout)findViewById(R.id.ad_expense);
        roomdetails=(TextView)findViewById(R.id.roomdetails);
        expensestool=(android.support.v7.widget.Toolbar)findViewById(R.id.expensestool);
        backex=(LinearLayout)expensestool.findViewById(R.id.backex);
        backex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            guest_id = extras.getString("GUESTID");
            guest_name = extras.getString("GUESTNAME");
            guest_room = extras.getString("ROOMNUMBER");
            // and get whatever type user account id is
        }
        roomdetails.setText(guest_name+","+guest_room);
        ad_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Expenses.this, Add_expenses.class);
                intent.putExtra("GUESTID",guest_id);
                intent.putExtra("GUESTNAME",guest_name);
                intent.putExtra("ROOMNUMBER",guest_room);
                startActivity(intent);
            }
        });
    }
}
