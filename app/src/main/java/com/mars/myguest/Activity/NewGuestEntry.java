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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.mars.myguest.R;
import com.mars.myguest.SplashActivity;
import com.mars.myguest.Util.AndroidMultiPartEntity;
import com.mars.myguest.Util.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

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
    EditText et_phone,et_name,et_dob,et_address,et_guest_no,et_city,et_state,et_discount;
    ImageView photo_iv,doc_front,doc_back;
    public static EditText et_room,et_price,et_fprice;
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
    RelativeLayout rel_newguest,rel_progress;
    private static String[] PERMISSIONS = {Manifest.permission.CAMERA};
    Double dis_price,act_price,final_amount;
    private ProgressBar progressBar;
    long totalSize = 0;
    TextView txtPercentage;
    LinearLayout layback;
    ProgressBar circleprogress;
    String guest_name,guest_mobile,guest_address,guest_city,guest_dob,guest_no,guest_state,guest_country;





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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtPercentage=(TextView) findViewById(R.id.txtPercentage);
        rel_newguest=(RelativeLayout)findViewById(R.id.rel_newguest);
        rel_progress=(RelativeLayout)findViewById(R.id.rel_progress);
        hotel_details=(LinearLayout)findViewById(R.id.hotel_details);
        userdetails=(LinearLayout)findViewById(R.id.userdetails);
        layback=(LinearLayout)findViewById(R.id.layback);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_guest_no=(EditText)findViewById(R.id.et_guest_no);
        et_name=(EditText)findViewById(R.id.et_name);
        et_dob=(EditText)findViewById(R.id.et_dob);
        et_address=(EditText)findViewById(R.id.et_address);
        et_room=(EditText)findViewById(R.id.et_room);
        et_price=(EditText)findViewById(R.id.et_price);
        et_discount=(EditText)findViewById(R.id.et_discount);
        et_fprice=(EditText)findViewById(R.id.et_fprice);
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
        layback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userdetails.setVisibility(View.VISIBLE);
                hotel_details.setVisibility(View.GONE);
            }
        });
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // NewGuestEntry.this.finish();
                if(et_room.getText().toString().trim().length()<=0){
                    showSnackBar("Select Room");
                }
                else{


                    /*
                    *
                    * first_name:Avinash

                    * */
                   // showSnackBar("Done");
                     guest_name=et_name.getText().toString().trim();
                     guest_mobile=et_phone.getText().toString().trim();
                     guest_address=et_address.getText().toString().trim();
                     guest_city=et_city.getText().toString().trim();
                     guest_dob=et_dob.getText().toString().trim();
                     guest_no=et_guest_no.getText().toString().trim();
                     guest_state="1";
                     guest_country="1";

                    new UploadFileToServer().execute();

                }

            }
        });
        et_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewGuestEntry.this,RoomList.class);
                startActivity(intent);
            }
        });
        et_discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 if(et_discount.getText().toString().trim().length()>1) {
                     dis_price = Double.valueOf(et_discount.getText().toString().trim());
                     act_price = Double.valueOf(et_price.getText().toString().trim());
                     final_amount = act_price - dis_price;
                     et_fprice.setText(String.valueOf(final_amount));
                 }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        else if(et_guest_no.getText().toString().trim().length()<=0){
            showSnackBar("Enter Number Of Guest");
        }else if(et_address.getText().toString().trim().length()<=0){
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
    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        int server_status;
        String server_message;

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            //circleprogress.setVisibility(View.VISIBLE);
            hotel_details.setVisibility(View.GONE);
            rel_progress.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible

            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constants.BASEURL+Constants.ADD_GUEST);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                /*
                *
                * last_name:pathak
mobile:7205674061
address:MIG - 102
pin:751019
city:bhubaneswer
state_id:1
country_id:1
dob:15-01-1988
photo:file upload
doc_1:file upload
doc_2:file upload
signature:file_upload
no_of_guest:5
                * */

                File photo = imgfile_photo;
                File doc_1 = imgfile_docfront;
                File doc_2 = imgfile_docfront;
                File signature = signature_photo;

                // Adding file data to http body
                entity.addPart("photo", new FileBody(photo));
                entity.addPart("doc_1", new FileBody(doc_1));
                entity.addPart("doc_2", new FileBody(doc_2));
                entity.addPart("signature", new FileBody(signature));

                // Extra parameters if you want to pass to server
                entity.addPart("first_name", new StringBody(guest_name));
                entity.addPart("mobile", new StringBody(guest_mobile));
                entity.addPart("address", new StringBody(guest_address));
                entity.addPart("city", new StringBody(guest_city));
                entity.addPart("state_id", new StringBody(guest_state));
                entity.addPart("country_id", new StringBody(guest_country));
                entity.addPart("dob", new StringBody(guest_dob));
                entity.addPart("no_of_guest", new StringBody(guest_no));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    JSONObject res = new JSONObject(responseString.trim());
                    JSONObject ress=res.getJSONObject("res");
                     server_status= ress.optInt("status");
                    if(server_status==1) {
                        server_message = "Room Added Successfully";
                    }
                    else{
                        server_message = "Failed";
                    }
                } else {
                    server_message = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                server_message = e.toString();
            } catch (IOException e) {
                server_message = e.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result) {
         //   circleprogress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            txtPercentage.setVisibility(View.INVISIBLE);

            Log.e("ADD GUEST", "Response from server: " + result);

            // showing the server response in an alert dialog
            if(server_status==1) {
                showSnackBar("Guest Checkin Done");
               // NewGuestEntry.this.finish();
                Intent intent = new Intent(NewGuestEntry.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
            else{
                showSnackBar("Error while Checkin");
                hotel_details.setVisibility(View.VISIBLE);
                rel_progress.setVisibility(View.GONE);

            }

            super.onPostExecute(result);
        }

    }

}
