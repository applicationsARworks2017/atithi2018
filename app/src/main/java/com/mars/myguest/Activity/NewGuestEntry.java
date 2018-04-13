package com.mars.myguest.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mars.myguest.R;

public class NewGuestEntry extends AppCompatActivity {
    LinearLayout hotel_details,userdetails;
    Button submit,checkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guest_entry);
        hotel_details=(LinearLayout)findViewById(R.id.hotel_details);
        userdetails=(LinearLayout)findViewById(R.id.userdetails);
        submit=(Button)findViewById(R.id.submit);
        checkin=(Button)findViewById(R.id.checkin);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userdetails.setVisibility(View.GONE);
                hotel_details.setVisibility(View.VISIBLE);
            }
        });
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewGuestEntry.this.finish();
            }
        });
    }
}
