package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.mars.myguest.Adapter.HotelListAdapter;
import com.mars.myguest.Pojo.Hotel_List;
import com.mars.myguest.R;
import com.mars.myguest.Util.CheckInternet;
import com.mars.myguest.Util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Admin_Hotel_List extends AppCompatActivity {

    ListView hotel_list;
    SwipeRefreshLayout hotel_swipe;
    TextView hotel_text;
    SearchView hotel_search;
    RelativeLayout linn;
    HotelListAdapter adapter;
    ArrayList<Hotel_List> hotel_List;
    public static String  hotelid;
    String user_id;
    Toolbar user_tool;
    LinearLayout user_tool_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_hotel);

        user_id = Admin_Hotel_List.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);

        hotel_list=(ListView)findViewById(R.id.hotel_list);
        hotel_swipe=(SwipeRefreshLayout)findViewById(R.id.hotel_swipe);
        hotel_text=(TextView)findViewById(R.id.hotel_text);
        hotel_search=(SearchView)findViewById(R.id.hotel_search) ;
        linn=(RelativeLayout)findViewById(R.id.linn);
        user_tool=(Toolbar)findViewById(R.id.user_tool);
        user_tool_back=(LinearLayout)findViewById(R.id.user_tool_back);
        user_tool_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getHotelList();
        hotel_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hotel_List shops=(Hotel_List) hotel_List.get(i);
                hotelid=shops.getHotel_id();
                Intent intent=new Intent(Admin_Hotel_List.this, Admin_Room_List.class);
                startActivity(intent);
            }
        });
        hotel_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setQuestionList(newText);
                return false;
            }
        });
        hotel_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHotelList();
            }
        });
    }

    private void setQuestionList(String newText) {
        final ArrayList<Hotel_List> hotel_search = new ArrayList<>();
        if (newText != null && newText.trim().length() > 0) {
            for (int i = 0; i < hotel_List .size(); i++) {
                String q_title = hotel_List.get(i).getHotel_name();
                if (q_title != null && newText != null &&
                        q_title.toLowerCase().contains(newText.toLowerCase())) {
                    hotel_search.add(hotel_List.get(i));
                }
            }
        }else{
            hotel_search.addAll(hotel_List);
        }
        // create an Object for Adapter
        adapter = new HotelListAdapter(Admin_Hotel_List.this,hotel_search);
        hotel_list.setAdapter(adapter);
        //  mAdapter.notifyDataSetChanged();


        if (hotel_List.isEmpty()) {
            hotel_list.setVisibility(View.GONE);
            hotel_text.setVisibility(View.VISIBLE);

        } else {
            hotel_list.setVisibility(View.VISIBLE);
            hotel_text.setVisibility(View.GONE);
        }

        adapter.notifyDataSetChanged();
    }

    private void getHotelList() {
        hotel_swipe.setRefreshing(false);
        if (CheckInternet.getNetworkConnectivityStatus(this)){
            new HotelList().execute(user_id);

        }else {
            Snackbar snackbar = Snackbar
                    .make(linn, "No Internet", Snackbar.LENGTH_LONG);
            snackbar.show();        }
    }

    private class HotelList extends AsyncTask<String, Void, Void> {

        private static final String TAG = "ServiceProvider";
        ProgressDialog progressDialog;
        private String hotel_id, hotel_name, hotel_address, hotel_photo, hotel_isactive, created, modified,
                hotel_user_id,hotel_user_name,hotel_user_mobile,hotel_user_login_pin,hotel_user_hotel_id, hotel_user_type_id,
                hotel_user_status,hotel_user_created,hotel_user_modified;
        int server_status;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(Admin_Hotel_List.this, "Loading", "Please wait...");
            }

        }

        @Override
        protected Void doInBackground(String... params) {


            try {
                InputStream in = null;
                int resCode = -1;
                String link = Constants.BASEURL + Constants.HOTEL_LIST;
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setAllowUserInteraction(false);
                conn.setInstanceFollowRedirects(true);
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_id", params[0]);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    in = conn.getInputStream();
                }
                if (in == null) {
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String response = "", data = "";

                while ((data = reader.readLine()) != null) {
                    response += data + "\n";
                }

                Log.i(TAG, "Response : " + response);

                            /*
                            *
                            * {
                "hotels": [
                    {
                        "id": 2,
                        "name": "pure",
                        "address": "Bangalore , bangalore , karnataka , 560043",
                        "photo": "http://a2r.in/atithi/files/profile/file1524012686730194450.jpg",
                        "is_active": "Y",
                        "user_id": 3,
                        "created": "2018-04-18T00:51:26+00:00",
                        "modified": "2018-04-18T00:51:26+00:00",
                        "user": {
                            "id": 3,
                            "name": "avinash",
                            "mobile": "0000000000",
                            "login_pin": "1234",
                            "hotel_id": 1,
                            "user_type_id": 3,
                            "status": "Approved",
                            "created": "2018-04-18T01:51:26+00:00",
                            "modified": "2018-04-18T01:51:26+00:00"
                        }
                    }
                ]
            }*/

                if (response != null && response.length() > 0) {
                    JSONObject res = new JSONObject(response);
                    hotel_List = new ArrayList<>();

                        JSONArray splist = res.getJSONArray("hotels");
                       if(splist.length()>0){
                           server_status=1;
                        for (int i = 0; i < splist.length(); i++) {
                            JSONObject o_list_obj = splist.getJSONObject(i);
                            hotel_id = o_list_obj.getString("id");
                            hotel_name = o_list_obj.getString("name");
                            hotel_address = o_list_obj.getString("address");
                            hotel_photo = o_list_obj.getString("photo");
                            hotel_isactive = o_list_obj.getString("is_active");
                            created = o_list_obj.getString("created");
                            modified = o_list_obj.getString("modified");

                           JSONObject obj=o_list_obj.getJSONObject("user");
                           hotel_user_id=obj.getString("id");
                           hotel_user_name=obj.getString("name");
                           hotel_user_mobile=obj.getString("mobile");
                           hotel_user_login_pin=obj.getString("login_pin");
                           hotel_user_hotel_id=obj.getString("hotel_id");
                           hotel_user_type_id=obj.getString("user_type_id");
                           hotel_user_status=obj.getString("status");
                           hotel_user_created=obj.getString("created");
                           hotel_user_modified=obj.getString("modified");


                            Hotel_List h_list = new Hotel_List(hotel_id, hotel_name, hotel_address, hotel_photo,hotel_isactive, created, modified,
                                    hotel_user_id,hotel_user_name,hotel_user_mobile,hotel_user_login_pin,hotel_user_hotel_id, hotel_user_type_id,
                                    hotel_user_status,hotel_user_created,hotel_user_modified);
                            hotel_List.add(h_list);

                        }
                    }
                    else {
                           server_status=0;
                       }

                }
                return null;

            } catch (Exception exception) {
                Log.e(TAG, "LoginAsync : doInBackground", exception);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void user) {
            super.onPostExecute(user);
            progressDialog.cancel();

            if (server_status == 1) {
                //Collections.reverse(splist_);
                adapter = new HotelListAdapter(Admin_Hotel_List.this, hotel_List);
                hotel_list.setAdapter(adapter);
            } else {
                hotel_swipe.setVisibility(View.GONE);
                hotel_list.setVisibility(View.GONE);
                hotel_text.setVisibility(View.VISIBLE);
            }

        }
    }
}