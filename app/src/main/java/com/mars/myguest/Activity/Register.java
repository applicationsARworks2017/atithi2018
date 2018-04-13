package com.mars.myguest.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mars.myguest.R;

public class Register extends AppCompatActivity {
    FloatingActionButton waitclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        waitclick=(FloatingActionButton)findViewById(R.id.waitclick);
        waitclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,WaitingPage.class);
                startActivity(intent);
            }
        });
    }
}
