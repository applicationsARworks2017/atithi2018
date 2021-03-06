package com.mars.myguest.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Amaresh on 4/14/18.
 */

public class Constants {

    public static String SHAREDPREFERENCE_KEY="atithi_sharedpreference";
    public static String USER_ID="user_id";
    public static String USER_NAME="user_name";
    public static String USER_PHONE="mobile";
    public static String HOTEL_ID="hotel_id";
    public static String USER_TYPE_ID="usertype_id";




    public static String BASEURL="https://a2r.in/atithi/";
    public static String LOGIN="Users/loginCheck.json";
    public static String ADD_HOTEL="Hotels/add.json";
    public static String HOTELLIST="Hotels/index.json";
    public static String EXP_LIST="Expences/index.json";
    public static String ADD_USERS="Users/add.json";
    public static String TRASANCTION="GuestTransactions/add.json";
    public static String HOTEL_LIST="Hotels/index.json";
    public static String ROOM_LIST="Rooms/index.json";
    public static String STATE_LIST="States/index.json";
    public static String ADD_ROOM="Rooms/add.json";
    public static String ADD_EXPENSE="Expences/add.json";
    public static String ADD_GUEST="Guests/add.json";
    public static String GUEST_LIST="Guests/index.json";
    public static String ATTACH_GUEST="GuestDetails/add.json";
    public static String _GUEST_EDIT="GuestTransactions/edit.json";
    public static String ATTACH_GUEST_LIST="GuestDetails/index.json";



    // for avoid rotation of image
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    public static File bitmaptofile(Bitmap bitmap, String name, Context context) {
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            // Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return imageFile;
    }
    public static String getOurDate(String ourDate)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aa"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
        }
        catch (Exception e)
        {
            ourDate = "00-00-0000 00:00";
        }
        return ourDate;
    }
}
