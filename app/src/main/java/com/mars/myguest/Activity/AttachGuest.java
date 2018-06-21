package com.mars.myguest.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mars.myguest.R;
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
import java.util.Date;

import static com.mars.myguest.Util.Constants.modifyOrientation;

public class AttachGuest extends AppCompatActivity {

    Button bt_addlogo,addnew;
    ImageView photo_iv,doc_front,doc_back;
    EditText et_name,_relation;
    TextView txtPercentage;
    RelativeLayout main_rel,rel_progress;
    String photo_type;
    private static String[] PERMISSIONS = {Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST = 1888;
    String imPath;
    File imageFile,imgfile_photo,imgfile_docfront,imgfile_docback;
    Uri picUri,pic_Uri1,pic_Uri2,pic_Uri3;
    Boolean picAvailable=false,docAvailable=false;
    String guest_id,name,relation;
    ProgressBar circleprogress,progressBar;
    ScrollView hotel_details;
    long totalSize = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_guest);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            guest_id = extras.getString("GUESTID");
            // and get whatever type user account id is
        }
        bt_addlogo=(Button)findViewById(R.id.bt_addlogo);
        addnew=(Button)findViewById(R.id.addnew);
        photo_iv=(ImageView) findViewById(R.id.photo_iv);
        doc_front=(ImageView) findViewById(R.id.doc_front);
        doc_back=(ImageView) findViewById(R.id.doc_back);
        et_name=(EditText)findViewById(R.id.et_name);
        hotel_details=(ScrollView)findViewById(R.id.mainscroll);
        txtPercentage=(TextView) findViewById(R.id.txtPercentage);
        circleprogress=(ProgressBar)findViewById(R.id.circleprogress);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        _relation=(EditText)findViewById(R.id._relation);
        main_rel=(RelativeLayout)findViewById(R.id.main_rel);
        rel_progress=(RelativeLayout)findViewById(R.id.rel_progress);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // here it is checking whether the permission is granted previously or not
            if (!hasPermissions(this, PERMISSIONS)) {
                //Permission is granted
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1);

            }
        }

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
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaidate();
            }
        });


    }

    private void vaidate() {
        if(picAvailable==false){
            showSnackBar("Add Photo");
        }
        else if(et_name.getText().toString().trim().length()<=0){
            showSnackBar("Enter Name");
        }
        else if(_relation.getText().toString().trim().length()<=0){
            showSnackBar("Enter Relation");
        }
        else {
            submitData();
        }
    }

    private void submitData() {
        name=et_name.getText().toString().trim();
        relation=_relation.getText().toString().trim();

        new UploadFileToServer().execute();

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
    private void camera(String string) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (cameraIntent.resolveActivity(AttachGuest.this.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile(photo_type);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(AttachGuest.this,
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
    private File createImageFile(String photo_type) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = AttachGuest.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
                Bitmap photo = MediaStore.Images.Media.getBitmap(AttachGuest.this.getContentResolver(), picUri);
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
    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(main_rel, message, Snackbar.LENGTH_LONG);
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
            HttpPost httppost = new HttpPost(Constants.BASEURL + Constants.ATTACH_GUEST);

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

                // Adding file data to http body
                entity.addPart("photo", new FileBody(photo));
                if(doc_1!=null) {
                    entity.addPart("document", new FileBody(doc_1));
                }
                if(doc_2!=null) {
                    entity.addPart("doc_2", new FileBody(doc_2));
                }

                // Extra parameters if you want to pass to server
                entity.addPart("guest_id", new StringBody(guest_id));
                entity.addPart("name", new StringBody(name));
                entity.addPart("relation", new StringBody(relation));
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
                    JSONObject ress = res.getJSONObject("res");
                    server_status = ress.optInt("status");
                    if (server_status == 1) {
                        server_message = "Attached Successfully";
                    } else {
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
            if(server_status==1){
                AttachGuest.this.finish();
            }
            else{
                showSnackBar(server_message);
            }
        }
    }
}
