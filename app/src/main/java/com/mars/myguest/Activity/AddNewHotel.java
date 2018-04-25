package com.mars.myguest.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mars.myguest.R;
import com.mars.myguest.Util.CheckInternet;
import com.mars.myguest.Util.Constants;
import com.mars.myguest.Util.MultipartUtility;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.mars.myguest.Util.Constants.modifyOrientation;

public class AddNewHotel extends AppCompatActivity {
    Button add_hotel;
    EditText et_hotelname,et_address,et_city,et_state,et_pincode;
    RelativeLayout rel_addnew;
    ImageView addlogo;
    String imPath;
    File imageFile;
    Uri picUri=null;
    Boolean photoCap=false;
    Boolean picAvailable=false;
    int chooseaction;
    Uri pic_Uri;
    private static final int CAMERA_REQUEST = 1888;
    private static int RESULT_LOAD_IMAGE = 1986;
    private static String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS,Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_hotel);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // here it is checking whether the permission is granted previously or not
            if (!hasPermissions(this, PERMISSIONS)) {
                //Permission is granted
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1);

            }
        }

        add_hotel=(Button)findViewById(R.id.add_hotel);
        addlogo=(ImageView)findViewById(R.id.addlogo);
        rel_addnew=(RelativeLayout)findViewById(R.id.rel_addnew);
        et_hotelname=(EditText)findViewById(R.id.et_hotelname);
        et_address=(EditText)findViewById(R.id.et_address);
        et_city=(EditText)findViewById(R.id.et_city);
        et_state=(EditText)findViewById(R.id.et_state);
        et_pincode=(EditText)findViewById(R.id.et_pincode);
        add_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(et_hotelname.getText().toString().trim().length()<=0){
                showSnackBar("Enter Hotel name");
            }
            else if(et_address.getText().toString().trim().length()<=0){
                showSnackBar("Enter Full Address");
            }
            else if(et_city.getText().toString().trim().length()<=0){
                showSnackBar("Enter City");
            }
            else if(et_state.getText().toString().trim().length()<=0){
                showSnackBar("Enter State");
            }
            else {
                String h_name=et_hotelname.getText().toString().trim();
                String h_address=et_address.getText().toString().trim();
                String h_city=et_city.getText().toString().trim();
                String h_state=et_state.getText().toString().trim();
                String h_pincode=et_pincode.getText().toString().trim();
                String complete_address=h_address+" , "+h_city+" , "+h_state+" , "+h_pincode;
                addHotels(complete_address,h_name);
            }
            }
        });
        addlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AddNewHotel.this);
                dialog.setContentView(R.layout.chooseaction);
                LinearLayout camera = (LinearLayout) dialog.findViewById(R.id.camera);
                LinearLayout gallery = (LinearLayout) dialog.findViewById(R.id.gallery);
                dialog.show();
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chooseaction=1;
                        dialog.cancel();
                        captureimage("gallery");

                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chooseaction=2;
                        dialog.cancel();
                        captureimage("camera");

                    }
                });
            }
        });
    }

    private void addHotels(String complete_address, String h_name) {
        if(CheckInternet.getNetworkConnectivityStatus(AddNewHotel.this)){
            if(addlogo.getDrawable()!=null && picAvailable==true) {
                Bitmap bitmap = ((BitmapDrawable) addlogo.getDrawable()).getBitmap();
                imageFile = persistImage(bitmap, h_name);
            }

            AddHotels addHotels=new AddHotels();
            addHotels.execute(complete_address,h_name);
        }
        else{
            showSnackBar("No Internet");
        }
    }
    private  File persistImage(Bitmap bitmap, String name) {
        File filesDir = AddNewHotel.this.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return imageFile;
    }

    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(rel_addnew, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#0091EA"));
        snackbar.show();
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
    private void captureimage(String action) {
        if (action.contentEquals("camera")) {

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (cameraIntent.resolveActivity(AddNewHotel.this.getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(AddNewHotel.this,
                                "com.mars.myguest",
                                photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            } else {
                imPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
                imageFile = new File(imPath);
                picUri = Uri.fromFile(imageFile); // convert path to Uri
                photoCap = true;
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
        else{
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = AddNewHotel.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imPath = image.getAbsolutePath();
        imageFile = new File(imPath);
        picUri = Uri.fromFile(image); // convert path to Uri
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(AddNewHotel.this.getContentResolver(), picUri);
                Bitmap c_photo= Bitmap.createScaledBitmap(photo,300,300,true);
                Bitmap perfectImage=modifyOrientation(c_photo,imPath);
                picAvailable = true;
                addlogo.setImageBitmap(perfectImage);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = AddNewHotel.this.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            pic_Uri = Uri.parse(String.valueOf(new File(picturePath)));
            picAvailable=true;
            addlogo.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }
    private class AddHotels extends AsyncTask<String, Void, Void> {

        private static final String TAG = "SynchMobnum";
        private ProgressDialog progressDialog = null;
        int server_status;
        String id, otp_no, name;
        String server_message;
        String photo;
        String charset = "UTF-8";
        String link = Constants.BASEURL + Constants.ADD_HOTEL;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(AddNewHotel.this, "Loading", "Please wait...");
            }
        }

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(String... params) {

            try {
                String address = params[0];
                String name = params[1];

                MultipartUtility multipart = new MultipartUtility(link, charset);
                multipart.addFormField("name", name);
                multipart.addFormField("address", address);
                if (imageFile != null) {
                    multipart.addFilePart("photo", imageFile);

                }
                List<String> response = multipart.finish();
                String res = "";
                for (String line : response) {
                    res = res + line + "\n";
                }
                Log.i(TAG, res);

                /**
                 * {
                 "hotel": {
                 "name": "gfdsgfdsg",
                 "address": "gfdgfdggfdg",
                 "photo": "file15240127551920757733.jpg",
                 "created": "2018-04-18T00:52:35+00:00",
                 "modified": "2018-04-18T00:52:35+00:00",
                 "id": 4
                 },
                 "res": {
                 "message": "The Hotel has been saved.",
                 "status": 1
                 }
                 }
                 * */
                if (res != null && res.length() > 0) {
                    JSONObject ress = new JSONObject(res.trim());
                    JSONObject sec=ress.getJSONObject("res");
                    server_status = sec.optInt("status");
                    if (server_status == 1) {
                        server_message = ress.optString("message");
                    } else {
                        server_message = "Error while saved";
                    }
                }
                return null;

            } catch (SocketTimeoutException exception) {
                server_message = "Connectivity issues,please try again";
                Log.e("SynchMobnum : doInBackground", exception.toString());
            } catch (ConnectException exception) {
                server_message = "Connectivity issues,please try again";
                Log.e("SynchMobnum : doInBackground", exception.toString());
            } catch (MalformedURLException exception) {
                server_message = "Connectivity issues,please try again";
                Log.e("SynchMobnum : doInBackground", exception.toString());
            } catch (IOException exception) {
                server_message = "Connectivity issues,please try again";
                Log.e("SynchMobnum : doInBackground", exception.toString());
            } catch (Exception exception) {
                server_message = "Connectivity issues,please try again";
                Log.e("SynchMobnum : doInBackground", exception.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void user) {
            super.onPostExecute(user);
            progressDialog.cancel();
            showSnackBar(server_message);
            if (server_status == 1) {
            AddNewHotel.this.finish();
            }
        }
    }
}
