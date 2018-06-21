package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.mars.myguest.Adapter.Room_List_Adapter;
import com.mars.myguest.Pojo.Room_List;
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
import java.util.Collections;

public class RoomList extends AppCompatActivity {
    ListView room_list;
    SwipeRefreshLayout room_swipe;
    TextView room_text;
    SearchView room_search;
    RelativeLayout linn;
    Room_List_Adapter adapter;
    ArrayList<Room_List> room_List;
    RelativeLayout rel_rooms;
    String hotel_id;
    String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            page = extras.getString("PAGE");
            // and get whatever type user account id is
        }
        hotel_id = RoomList.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.HOTEL_ID, null);
        room_list = (ListView) findViewById(R.id.room_list);
        room_swipe = (SwipeRefreshLayout) findViewById(R.id.room_swipe);
        room_text = (TextView) findViewById(R.id.room_text);
        room_search = (SearchView) findViewById(R.id.search_rooms);
        rel_rooms = (RelativeLayout) findViewById(R.id.rel_rooms);
        getRoomlList();
        room_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(page.contentEquals("New Guest")) {
                    Room_List rooms = (Room_List) room_list.getItemAtPosition(i);
                    NewGuestEntry.et_room.setText(rooms.getRoom_no());
                    NewGuestEntry.room_id = rooms.getRoom_id();
                    NewGuestEntry.et_room.setText(rooms.getRoom_no());
                    NewGuestEntry.et_price.setText(rooms.getRoom_price());
                    NewGuestEntry.et_fprice.setText(rooms.getRoom_price());
                    RoomList.this.finish();
                }


            }
        });

    }
    private void getRoomlList() {
        room_swipe.setRefreshing(false);
        if (CheckInternet.getNetworkConnectivityStatus(this)) {
            new MyRoomList().execute(hotel_id);

        } else {
            Snackbar snackbar = Snackbar
                    .make(linn, "No Internet", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    private class MyRoomList extends AsyncTask<String, Void, Void> {

        private static final String TAG = "ServiceProvider";
        ProgressDialog progressDialog;
        private String room_id, hotel_id, room_no, room_price, room_isactive,
                created, modified, rooml_user_id, room_user_name, room_user_address,
                room_user_photo, room_user_created, room_user_modified;
        int server_status;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(RoomList.this, "Loading", "Please wait...");
            }

        }

        @Override
        protected Void doInBackground(String... params) {


            try {
                InputStream in = null;
                int resCode = -1;
                String link = Constants.BASEURL + Constants.ROOM_LIST;
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
                        .appendQueryParameter("hotel_id", params[0]);
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
    "rooms": [
        {
            "id": 1,
            "hotel_id": 1,
            "room_no": "102",
            "price": 200,
            "is_active": "Y",
            "created": "2018-04-18T00:22:40+00:00",
            "modified": "2018-04-18T00:25:30+00:00",
            "hotel": {
                "id": 1,
                "name": "gfdsgfdsg",
                "address": "gfdgfdggfdg",
                "photo": "http://a2r.in/atithi/files/profile/file15236756231976992687.jpg",
                "is_active": "Y",
                "created": "2018-04-14T03:13:43+00:00",
                "modified": "2018-04-14T03:13:43+00:00"
            }
        },

    ]
}
            }*/

                if (response != null && response.length() > 0) {
                    JSONObject res = new JSONObject(response);
                    room_List = new ArrayList<>();

                    JSONArray splist = res.getJSONArray("rooms");
                    if (splist.length() > 0) {
                        server_status = 1;
                        for (int i = 0; i < splist.length(); i++) {
                            JSONObject o_list_obj = splist.getJSONObject(i);
                            room_id = o_list_obj.getString("id");
                            hotel_id = o_list_obj.getString("hotel_id");
                            room_no = o_list_obj.getString("room_no");
                            room_price = o_list_obj.getString("price");
                            room_isactive = o_list_obj.getString("is_active");
                            created = o_list_obj.getString("created");
                            modified = o_list_obj.getString("modified");

                            JSONObject obj = o_list_obj.getJSONObject("hotel");
                            rooml_user_id = obj.getString("id");
                            room_user_name = obj.getString("name");
                            room_user_address = obj.getString("address");
                            room_user_photo = obj.getString("photo");
                            room_user_created = obj.getString("created");
                            room_user_modified = obj.getString("modified");


                            Room_List r_list = new Room_List(room_id, hotel_id, room_no, room_price, room_isactive,
                                    created, modified, rooml_user_id, room_user_name, room_user_address,
                                    room_user_photo, room_user_created, room_user_modified);
                            room_List.add(r_list);

                        }
                    } else {
                        server_status = 0;
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
                Collections.reverse(room_List);
                adapter = new Room_List_Adapter(RoomList.this, room_List);
                room_list.setAdapter(adapter);
            } else {
                room_swipe.setVisibility(View.GONE);
                room_list.setVisibility(View.GONE);
                room_text.setVisibility(View.VISIBLE);
            }

        }
    }
}
