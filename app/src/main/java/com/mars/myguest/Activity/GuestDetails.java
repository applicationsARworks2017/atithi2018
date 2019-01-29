package com.mars.myguest.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mars.myguest.R;
import com.squareup.picasso.Picasso;

public class GuestDetails extends AppCompatActivity {
    ImageView guest_pic,im_doc1,im_doc2,im_sign;
    String guest_name,guest_id,guest_phone,guest_image,doc1,doc2,signature,address,dob;
    Toolbar g_details_tool;
    TextView tool_name;
    ImageView tool_back;
    Button bt_viewothers;
    TextView tv_name,tv_phone,tv_dob,tv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_details);
        g_details_tool=(Toolbar)findViewById(R.id.g_details_tool);
        tool_back=(ImageView)g_details_tool.findViewById(R.id.tool_back);
        guest_pic=(ImageView)findViewById(R.id.guest_pic);
        im_doc1=(ImageView)findViewById(R.id.doc1);
        im_doc2=(ImageView)findViewById(R.id.g_img2);
        im_sign=(ImageView)findViewById(R.id.sign);
        tool_name=(TextView)g_details_tool.findViewById(R.id.tool_name);
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_phone=(TextView)findViewById(R.id.tv_phone);
        tv_dob=(TextView)findViewById(R.id.tv_dob);
        tv_address=(TextView)findViewById(R.id.tv_address);
        bt_viewothers=(Button) findViewById(R.id.bt_viewothers);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            guest_name = extras.getString("GNAME");
            guest_id = extras.getString("G_ID");
            guest_phone = extras.getString("G_PHONE");
            guest_image = extras.getString("G_PIC");
            doc1 = extras.getString("G_DOC1");
            doc2 = extras.getString("G_DOC2");
            signature = extras.getString("G_SIGN");
            address = extras.getString("G_ADD");
            dob = extras.getString("G_DOB");
        }
        tool_name.setText(guest_name);
        tv_name.setText("Name: "+guest_name);
        tv_phone.setText("Phone: "+guest_phone);
        tv_address.setText("Address: "+address);
        tv_dob.setText("DOB: "+dob);
        tool_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuestDetails.this.finish();
            }
        });
        if(!guest_image.contentEquals( "" )){
                Picasso.with(GuestDetails.this).load(guest_image)
                        .resize(300,300).into(guest_pic);
        }
        if(!doc1.contentEquals("")){
            Picasso.with(GuestDetails.this).load(doc1)
                    .resize(300,300).into(im_doc1);
        }
        if(!doc2.contentEquals("")){
            Picasso.with(GuestDetails.this).load(doc2)
                    .resize(300,300).into(im_doc2);
        }
        if(!signature.contentEquals("")){
            Picasso.with(GuestDetails.this).load(signature)
                    .resize(300,300).into(im_sign);
        }
        bt_viewothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestDetails.this, AllOtherguest.class);
                intent.putExtra("GUEST_ID",guest_id);
                startActivity(intent);
            }
        });
    }
}
