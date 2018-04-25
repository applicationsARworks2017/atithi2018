package com.mars.myguest.Activity;

import android.app.ProgressDialog;
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

import com.mars.myguest.Pojo.User;
import com.mars.myguest.R;
import com.mars.myguest.SplashActivity;
import com.mars.myguest.Util.APIManager;
import com.mars.myguest.Util.CheckInternet;
import com.mars.myguest.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    FloatingActionButton waitclick;
    Button login_btn;
    EditText username,password,cpassword,name;
    public static EditText orgname;
    public static String hotel_id;
    RelativeLayout rel_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        waitclick=(FloatingActionButton)findViewById(R.id.waitclick);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        cpassword=(EditText)findViewById(R.id.cpassword);
        name=(EditText)findViewById(R.id.name);
        orgname=(EditText)findViewById(R.id.orgname);
        login_btn=(Button)findViewById(R.id.login_btn);
        rel_register=(RelativeLayout)findViewById(R.id.rel_register);
        orgname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,AllHotels.class);
                intent.putExtra("PAGE","registration");
                startActivity(intent);
            }
        });
        waitclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(username.getText().toString().trim().length()<10){
                showSnackBar("Enter Valid Phone Number");
            }
            else if(name.getText().toString().trim().length()<0){
                    showSnackBar("Enter Name");
            }
            else if(password.getText().toString().trim().length()<=0 || password.getText().toString().trim().length()<4){
                showSnackBar("Enter pin of 4 - 6 digit");
            }
            else if(!password.getText().toString().trim().contentEquals(cpassword.getText().toString().trim())){
                showSnackBar("Pin and confirm pin must be same");
            }
            else if(orgname.getText().toString().trim().length()<=0){
                showSnackBar("Enter Hotel name");    
            }
            else{
                if(CheckInternet.getNetworkConnectivityStatus(Register.this)) {
                    submitAPI(username.getText().toString().trim(),password.getText().toString().trim(),name.getText().toString().trim());
                }
                else{
                    showSnackBar("Check Internet");
                }
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

    private void submitAPI(String phone,String pin,String name) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Registering. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mobile", phone);
            jsonObject.put("login_pin", pin);
            jsonObject.put("name", name);
            jsonObject.put("hotel_id", hotel_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIManager().ModifyAPI(Constants.BASEURL + Constants.ADD_USERS, "res", jsonObject, Register.this, new APIManager.APIManagerInterface() {
            @Override
            public void onSuccess(Object resultObj) {
                pd.dismiss();
                Intent intent=new Intent(Register.this,WaitingPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                showSnackBar(error);
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
