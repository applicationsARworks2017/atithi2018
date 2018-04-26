package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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


public class Admin_Room_List extends AppCompatActivity {

    ListView room_list;
    SwipeRefreshLayout room_swipe;
    TextView room_text;
    SearchView room_search;
    RelativeLayout linn;
    Room_List_Adapter adapter;
    ArrayList<Room_List> room_List;
    Toolbar user_tool;
    LinearLayout user_tool_back;
    FloatingActionButton float_btn_room_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_room_list);

        room_list = (ListView) findViewById(R.id.room_list);
        room_swipe = (SwipeRefreshLayout) findViewById(R.id.room_swipe);
        room_text = (TextView) findViewById(R.id.room_text);
        room_search = (SearchView) findViewById(R.id.room_search);
        linn = (RelativeLayout) findViewById(R.id.linn);
        user_tool = (Toolbar) findViewById(R.id.user_tool);
        user_tool_back = (LinearLayout) findViewById(R.id.user_tool_back);
        user_tool_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        float_btn_room_add=(FloatingActionButton)findViewById(R.id.float_btn_room_add);
        float_btn_room_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Admin_Room_List.this,Add_Room.class);
                startActivity(i);
            }
        });
        getRoomlList();
        room_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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
        room_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRoomlList();
            }
        });
    }

    private void setQuestionList(String newText) {
        final ArrayList<Room_List> room_search = new ArrayList<>();
        if (newText != null && newText.trim().length() > 0) {
            for (int i = 0; i < room_List.size(); i++) {
                String q_title = room_List.get(i).getRoom_no();
                if (q_title != null && newText != null &&
                        q_title.toLowerCase().contains(newText.toLowerCase())) {
                    room_search.add(room_List.get(i));
                }
            }
        } else {
            room_search.addAll(room_List);
        }
        // create an Object for Adapter
        adapter = new Room_List_Adapter(Admin_Room_List.this, room_search);
        room_list.setAdapter(adapter);
        //  mAdapter.notifyDataSetChanged();


        if (room_List.isEmpty()) {
            room_list.setVisibility(View.GONE);
            room_text.setVisibility(View.VISIBLE);

        } else {
            room_list.setVisibility(View.VISIBLE);
            room_text.setVisibility(View.GONE);
        }

        adapter.notifyDataSetChanged();
    }

    private void getRoomlList() {
        room_swipe.setRefreshing(false);
        if (CheckInternet.getNetworkConnectivityStatus(this)) {
            new RoomList().execute(Admin_Hotel_List.hotelid);

        } else {
            Snackbar snackbar = Snackbar
                    .make(linn, "No Internet", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private class RoomList extends AsyncTask<String, Void, Void> {

        private static final String TAG = "ServiceProvider";
        ProgressDialog progressDialog;
        private String room_id, hotel_id, room_no, room_price, room_isactive,
                created, modified, rooml_user_id, room_user_name, room_user_address,
                room_user_photo, room_user_created, room_user_modified;
        int server_status;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(Admin_Room_List.this, "Loading", "Please wait...");
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
                adapter = new Room_List_Adapter(Admin_Room_List.this, room_List);
                room_list.setAdapter(adapter);
            } else {
                room_swipe.setVisibility(View.GONE);
                room_list.setVisibility(View.GONE);
                room_text.setVisibility(View.VISIBLE);
            }

        }
    }
}
