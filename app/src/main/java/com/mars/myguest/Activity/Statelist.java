package com.mars.myguest.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.mars.myguest.Adapter.Room_List_Adapter;
import com.mars.myguest.Adapter.StateAdapter;
import com.mars.myguest.Pojo.Hotels;
import com.mars.myguest.Pojo.Room_List;
import com.mars.myguest.Pojo.States;
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
import java.util.List;

public class Statelist extends AppCompatActivity {
    SearchView sv_state;
    ListView lv_states;
    RelativeLayout state_rel;
    ArrayList<States> statesArrayList;
    StateAdapter stateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statelist);
        state_rel=(RelativeLayout)findViewById(R.id.state_rel);
        sv_state=(SearchView) findViewById(R.id.sv_state);
        lv_states=(ListView) findViewById(R.id.lv_states);
        getstatelist();
        lv_states.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    States states=(States) lv_states.getItemAtPosition(i);
                    NewGuestEntry.et_state.setText(states.getState_name());
                    NewGuestEntry.guest_state=states.getState_id();
                    NewGuestEntry.guest_country=states.getCountry_id();
                    Statelist.this.finish();
            }
        });
        sv_state.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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

    }
    private void setQuestionList(String newText) {
        final ArrayList<States> state_search = new ArrayList<>();
        if (newText != null && newText.trim().length() > 0) {
            for (int i = 0; i < statesArrayList.size(); i++) {
                String q_title = statesArrayList.get(i).getState_name();
                if (q_title != null && newText != null &&
                        q_title.toLowerCase().contains(newText.toLowerCase())) {
                    state_search.add(statesArrayList.get(i));
                }
            }
        } else {
            state_search.addAll(statesArrayList);
        }
        // create an Object for Adapter
        stateAdapter = new StateAdapter(Statelist.this, state_search);
        lv_states.setAdapter(stateAdapter);
        //  mAdapter.notifyDataSetChanged();


        if (statesArrayList.isEmpty()) {
            showSnackBar("No States Found");

        }
        stateAdapter.notifyDataSetChanged();
    }
    private void getstatelist() {
        statesArrayList=new ArrayList<>();
        if(CheckInternet.getNetworkConnectivityStatus(Statelist.this)){
            StateList stateList=new StateList();
            stateList.execute();

        }
        else{
            showSnackBar("No Interent");
        }
    }

    void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(state_rel, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#0091EA"));
        snackbar.show();
    }
    private class StateList extends AsyncTask<String, Void, Void> {

        private static final String TAG = "ServiceProvider";
        ProgressDialog progressDialog;

        int server_status;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(Statelist.this, "Loading", "Please wait...");
            }

        }

        @Override
        protected Void doInBackground(String... params) {


            try {
                InputStream in = null;
                int resCode = -1;
                String link = Constants.BASEURL + Constants.STATE_LIST;
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
                        .appendQueryParameter("", "");
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
                            * "states": [
        {
            "id": 2,
            "name": "Andhra Pradesh",
            "country_id": 1,
            "is_active": "Y",
            "created": null,
            "modified": null,
            "country": {
                "id": 1,
                "name": "India",
                "is_active": "Y",
                "created": null,
                "modified": null
            }
        },      }*/

                if (response != null && response.length() > 0) {
                    JSONObject res = new JSONObject(response);

                    JSONArray splist = res.getJSONArray("states");
                    if (splist.length() > 0) {
                        server_status = 1;
                        for (int i = 0; i < splist.length(); i++) {
                            JSONObject o_list_obj = splist.getJSONObject(i);
                            String state_id = o_list_obj.getString("id");
                            String state_name = o_list_obj.getString("name");
                            String is_active = o_list_obj.getString("is_active");
                            JSONObject obj = o_list_obj.getJSONObject("country");
                            String country_id = obj.getString("id");
                            String country_name = obj.getString("name");

                            States states = new States(state_id,state_name,is_active,country_id,country_name);
                            statesArrayList.add(states);

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
            if(server_status==1){
                stateAdapter = new StateAdapter(Statelist.this, statesArrayList);
                lv_states.setAdapter(stateAdapter);
            }
            else{
                showSnackBar("No states found");
            }

        }
    }
}
