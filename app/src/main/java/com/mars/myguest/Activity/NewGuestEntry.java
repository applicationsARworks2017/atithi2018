package com.mars.myguest.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.mars.myguest.R;
import com.mars.myguest.Util.Constants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.mars.myguest.Util.Constants.modifyOrientation;

public class NewGuestEntry extends AppCompatActivity {
    LinearLayout hotel_details,userdetails;
    Button submit,checkin,bt_addlogo;
    EditText et_phone,et_name,et_dob,et_address,et_city,et_state;
    ImageView photo_iv,doc_front,doc_back;
    SignaturePad signaturePad;
    private static final int CAMERA_REQUEST = 1888;
    String imPath,checkin_id;
    File imageFile,imgfile_photo,imgfile_docfront,imgfile_docback,signature_photo;
    Uri picUri=null,pic_Uri1=null,pic_Uri2=null,pic_Uri3=null;
    Boolean picAvailable=false;
    Button clearpad;
    Boolean docAvailable=false;
    Boolean sign=false;
    Calendar myCalendar;
    String photo_type;
    RelativeLayout rel_newguest;
    private static String[] PERMISSIONS = {Manifest.permission.CAMERA};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guest_entry);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // here it is checking whether the permission is granted previously or not
            if (!hasPermissions(this, PERMISSIONS)) {
                //Permission is granted
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1);

            }
        }
        myCalendar= Calendar.getInstance();

        rel_newguest=(RelativeLayout)findViewById(R.id.rel_newguest);
        hotel_details=(LinearLayout)findViewById(R.id.hotel_details);
        userdetails=(LinearLayout)findViewById(R.id.userdetails);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_name=(EditText)findViewById(R.id.et_name);
        et_dob=(EditText)findViewById(R.id.et_dob);
        et_address=(EditText)findViewById(R.id.et_address);
        et_state=(EditText)findViewById(R.id.et_state);
        photo_iv=(ImageView)findViewById(R.id.photo_iv);
        doc_front=(ImageView)findViewById(R.id.doc_front);
        doc_back=(ImageView)findViewById(R.id.doc_back);
        signaturePad=(SignaturePad)findViewById(R.id.signatory);
        et_city=(EditText)findViewById(R.id.et_city);
        submit=(Button)findViewById(R.id.submit);
        bt_addlogo=(Button)findViewById(R.id.bt_addlogo);
        checkin=(Button)findViewById(R.id.checkin);
        clearpad=(Button)findViewById(R.id.clearpad);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();
                /*userdetails.setVisibility(View.GONE);
                hotel_details.setVisibility(View.VISIBLE);*/
            }
        });
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewGuestEntry.this.finish();
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datepickerDialog= new DatePickerDialog(NewGuestEntry.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datepickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                datepickerDialog.show();
            }
        });
        bt_addlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo_type="guestpic";
                camera("guestpic");
            }
        });
        doc_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo_type="docfront";
                camera("docfront");
            }
        });
        doc_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo_type="docback";
                camera("docback");
            }
        });
        clearpad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signaturePad.clear();
                sign=false;

            }
        });
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
               // sign=true;
                //Toast.makeText(CheckinActivity1.this, "OnStartSigning", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSigned() {
                sign=true;
                clearpad.setEnabled(true);
                submit.setEnabled(true);

            }

            @Override
            public void onClear() {
                sign=false;
                clearpad.setEnabled(false);
                submit.setEnabled(false);

            }
        });


    }

    private void camera(String string) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (cameraIntent.resolveActivity(NewGuestEntry.this.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile(photo_type);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(NewGuestEntry.this,
                            "com.mars.myguest",
                            photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        }
        else {
            imPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
            imageFile = new File(imPath);
            if(string.contentEquals("guestpic")){
                imgfile_photo=imageFile;
            }
            else if(string.contentEquals("docfront")){
                imgfile_docfront=imageFile;
            }else if(string.contentEquals("docback")){
                imgfile_docback=imageFile;
            }
            picUri = Uri.fromFile(imageFile); // convert path to Uri
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_dob.setText(sdf.format(myCalendar.getTime()));

    }
    private File createImageFile(String photo_type) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = NewGuestEntry.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imPath = image.getAbsolutePath();
        imageFile = new File(imPath);
        if(photo_type.contentEquals("guestpic")){
            imgfile_photo=imageFile;
        }else if(photo_type.contentEquals("docfront")){
            imgfile_docfront=imageFile;
        }else if(photo_type.contentEquals("docback")){
            imgfile_docback=imageFile;
        }
        picUri = Uri.fromFile(image); // convert path to Uri
        return image;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            // imPath=picUri.getPath();
            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(NewGuestEntry.this.getContentResolver(), picUri);
                Bitmap c_photo = Bitmap.createScaledBitmap(photo, 500, 500, true);
                Bitmap perfectImage = modifyOrientation(c_photo, imPath);
                if (photo_type.contentEquals("guestpic")) {
                    picAvailable = true;
                    pic_Uri1=picUri;
                    photo_iv.setImageBitmap(perfectImage);
                }else if (photo_type.contentEquals("docfront")) {
                    docAvailable=true;
                    pic_Uri2=picUri;
                    doc_front.setImageBitmap(perfectImage);
                }else if (photo_type.contentEquals("docback")) {
                    docAvailable=true;
                    pic_Uri3=picUri;
                    doc_back.setImageBitmap(perfectImage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void validate() {
        Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
        signature_photo= Constants.bitmaptofile(signatureBitmap,"sign",NewGuestEntry.this);
        if(picAvailable==false){
            showSnackBar("Add One Photo of the Guest");
        }
        else if(et_name.getText().toString().trim().length()<=0){
            showSnackBar("Enter Phone Number");
        }
        else if(et_name.getText().toString().trim().length()<=0){
            showSnackBar("Enter Name");
        }
        else if(et_dob.getText().toString().trim().length()<=0){
            showSnackBar("Enter DOB");
        }
        else if(et_address.getText().toString().trim().length()<=0){
            showSnackBar("Enter Address");
        }else if(et_city.getText().toString().trim().length()<=0){
            showSnackBar("Enter City");
        }else if(et_state.getText().toString().trim().length()<=0){
            showSnackBar("Enter State");
        }else if(docAvailable==false){
            showSnackBar("Add ID proof of the Guest");
        }else if(sign==false){
            showSnackBar("Take sign of Guest");
        }
        else{
            userdetails.setVisibility(View.GONE);
            hotel_details.setVisibility(View.VISIBLE);
        }

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(rel_newguest, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#0091EA"));
        snackbar.show();
    }

}
