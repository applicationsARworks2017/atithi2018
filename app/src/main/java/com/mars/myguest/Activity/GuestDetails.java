package com.mars.myguest.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mars.myguest.R;

public class GuestDetails extends AppCompatActivity {
    ImageView guest_pic;
    String guest_name;
    Toolbar g_details_tool;
    TextView tool_name;
    ImageView tool_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_details);
        g_details_tool=(Toolbar)findViewById(R.id.g_details_tool);
        tool_back=(ImageView)g_details_tool.findViewById(R.id.tool_back);
        tool_name=(TextView)g_details_tool.findViewById(R.id.tool_name);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            guest_name = extras.getString("GNAME");
            // and get whatever type user account id is
        }
        tool_name.setText(guest_name);
        tool_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuestDetails.this.finish();
            }
        });
    }
}
