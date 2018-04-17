package com.mars.myguest.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.mars.myguest.R;
import com.mars.myguest.SplashActivity;

public class Register extends AppCompatActivity {
    FloatingActionButton waitclick;
    Button login_btn;
    EditText username,password,cpassword;
    public static EditText orgname;
    RelativeLayout rel_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        waitclick=(FloatingActionButton)findViewById(R.id.waitclick);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        cpassword=(EditText)findViewById(R.id.cpassword);
        orgname=(EditText)findViewById(R.id.orgname);
        login_btn=(Button)findViewById(R.id.login_btn);
        rel_register=(RelativeLayout)findViewById(R.id.rel_register);
        waitclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(username.getText().toString().trim().length()<10){
                showSnackBar("Enter Valid Phone Number");
            }
            else if(password.getText().toString().trim().length()<=0 || password.getText().toString().trim().length()<4){
                showSnackBar("Enter pin of 4 - 6 digit");
            }
            else if(password.getText().toString().trim().contentEquals(cpassword.getText().toString().trim())){
                showSnackBar("Pin and confirm pin must be same");
            }
                /*Intent intent=new Intent(Register.this,WaitingPage.class);
                startActivity(intent);*/
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }
    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(rel_register, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#0091EA"));
        snackbar.show();
    }
}
