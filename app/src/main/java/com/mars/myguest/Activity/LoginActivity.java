package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mars.myguest.Pojo.User;
import com.mars.myguest.R;
import com.mars.myguest.Util.APIManager;
import com.mars.myguest.Util.CheckInternet;
import com.mars.myguest.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button register_btn;
    FloatingActionButton signin;
    RelativeLayout login_rel;
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register_btn=(Button)findViewById(R.id.register_btn);
        signin=(FloatingActionButton)findViewById(R.id.signin);
        login_rel=(RelativeLayout)findViewById(R.id.login_rel);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(LoginActivity.this,Home.class);
                startActivity(intent);*/
                if(username.getText().toString().trim().length()<=0){
                    showSnackBar("Enter Valid Mobile Number");
                }
                else if(password.getText().toString().trim().length()<=0){
                    showSnackBar("Enter Pin");
                }
                else{
                    if(CheckInternet.getNetworkConnectivityStatus(LoginActivity.this)){
                        String mobile=username.getText().toString();
                        String loginpin=password.getText().toString();
                        LoginAPI(mobile,loginpin);
                    }
                    else{
                        showSnackBar("No Internet");
                    }

                }
            }
        });
    }

    private void LoginAPI(String mobile, String loginpin) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Validating. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("login_pin", loginpin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIManager().PostAPI(Constants.BASEURL + Constants.LOGIN, "users",jsonObject, User.class, this, new APIManager.APIManagerInterface() {
            @Override
            public void onSuccess(Object resultObj) {
                pd.dismiss();
                //Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();
                User user= (User) resultObj;
                String name=user.getName();
                String phone=user.getMobile();
                String user_id=user.getId();
                String hotel_id=user.getHotel_id();
                String user_type=user.getUser_type_id();

                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0); // 0 - for private mode
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.USER_ID, user_id);
                editor.putString(Constants.USER_NAME, name);
                editor.putString(Constants.USER_PHONE, phone);
                editor.putString(Constants.HOTEL_ID, hotel_id);
                editor.putString(Constants.USER_TYPE_ID, user_type);
                editor.commit();
                if(user_type.contentEquals("1")) {
                    Intent i = new Intent(LoginActivity.this, SuperadminDashboard.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                }
                else if(user_type.contentEquals("3")){
                    Intent i = new Intent(LoginActivity.this, Home.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                }
                else if(user_type.contentEquals("2")){
                    Intent i = new Intent(LoginActivity.this, AdminDashboard.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                }
                else{
                    showSnackBar("Invalid User Type");
                }
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
                .make(login_rel, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#0091EA"));
        snackbar.show();
    }
}
