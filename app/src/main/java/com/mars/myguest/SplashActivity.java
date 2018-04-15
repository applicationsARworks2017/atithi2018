package com.mars.myguest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mars.myguest.Activity.AdminDashboard;
import com.mars.myguest.Activity.Home;
import com.mars.myguest.Activity.LoginActivity;
import com.mars.myguest.Activity.SuperadminDashboard;
import com.mars.myguest.Util.Constants;

public class SplashActivity extends AppCompatActivity {
    String user_id,user_type;

    int SPLASH_INTERVAL_TIME=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        user_id = SplashActivity.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);
        user_type = SplashActivity.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_TYPE_ID,null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user_id == null || user_id.trim().length() < 0) {

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
                else {
                    if(user_type.contentEquals("1")){
                        Intent intent = new Intent(SplashActivity.this, SuperadminDashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                    else if(user_type.contentEquals("2")){
                        Intent intent = new Intent(SplashActivity.this, AdminDashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                    else if(user_type.contentEquals("3")){
                        Intent intent = new Intent(SplashActivity.this, Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                }
                //finish();
            }
        }, SPLASH_INTERVAL_TIME);
    }
}
