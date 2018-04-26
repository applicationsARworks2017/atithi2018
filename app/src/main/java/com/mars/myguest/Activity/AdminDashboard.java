package com.mars.myguest.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.mars.myguest.R;

public class AdminDashboard extends AppCompatActivity {
    RelativeLayout managehotel,manageguest,managerequest,manageaccounts,manage_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        managehotel=(RelativeLayout)findViewById(R.id.managehotel);
        manageguest=(RelativeLayout)findViewById(R.id.manageguest);
        managerequest=(RelativeLayout)findViewById(R.id.managerequest);
        manageaccounts=(RelativeLayout)findViewById(R.id.manageaccounts);
        manage_profile=(RelativeLayout)findViewById(R.id.manage_profile);


        managehotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminDashboard.this,Admin_Hotel_List.class);
                startActivity(i);

            }
        }); manageguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); managerequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); manageaccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); manage_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminDashboard.this,Admin_Profile.class);
                startActivity(i);
            }
        });
    }
}
