package com.mars.myguest.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.mars.myguest.R;

public class SuperadminDashboard extends AppCompatActivity {
    CardView card_profile,card_addhotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_dashboard);
        card_profile=(CardView)findViewById(R.id.card_profile);
        card_addhotels=(CardView)findViewById(R.id.card_addhotels);
        card_addhotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SuperadminDashboard.this,AddNewHotel.class);
                startActivity(intent);
            }
        });
        card_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SuperadminDashboard.this,ProfileSA.class);
                startActivity(intent);
            }
        });
    }
}
