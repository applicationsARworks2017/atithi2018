package com.mars.myguest.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.mars.myguest.R;

public class AdminDashboard extends AppCompatActivity {
    RelativeLayout managehotel,manageguest,managerequest,manageaccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    }
}
