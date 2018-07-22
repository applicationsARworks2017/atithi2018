package com.mars.myguest.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mars.myguest.Activity.NewGuestEntry;
import com.mars.myguest.Adapter.Guest_List_Adapter;
import com.mars.myguest.Pojo.Guest_List;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CircleImageView proimg;
    SwipeRefreshLayout swipe_allguest;
    ListView lv_guests;
    TextView no_guest;
    FloatingActionButton guestentry;
    private OnFragmentInteractionListener mListener;
    FrameLayout linn;
    ArrayList<Guest_List> guest_List;
    Guest_List_Adapter adapter;
    String hotel_id;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        hotel_id = getContext().getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.HOTEL_ID, null);

        proimg=(CircleImageView) view.findViewById(R.id.pro_img);
        proimg.setImageResource(R.drawable.pr);
        swipe_allguest=(SwipeRefreshLayout)view.findViewById(R.id.swipe_allguest);
        lv_guests=(ListView)view.findViewById(R.id.lv_guests);
        no_guest=(TextView)view.findViewById(R.id.no_guest);
        linn=(FrameLayout)view.findViewById(R.id.linn);
        guestentry=(FloatingActionButton)view.findViewById(R.id.guestentry);
        guestentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),NewGuestEntry.class);
                startActivity(intent);
            }
        });

        getGeusetList();
        swipe_allguest.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGeusetList();
            }
        });
        return view;
        //            holder.visitorsPic.setImageResource(R.drawable.no_image);

    }

    private void getGeusetList() {
        swipe_allguest.setRefreshing(false);
        if (CheckInternet.getNetworkConnectivityStatus(getContext())) {
            new GuestList().execute(hotel_id);

        } else {
            Snackbar snackbar = Snackbar
                    .make(linn, "No Internet", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class GuestList extends AsyncTask<String, Void, Void> {

        private static final String TAG = "ServiceProvider";
        ProgressDialog progressDialog;
        private String guest_id, first_name, last_name, mobile, address,
                city, photo, doc_1, doc_2, created,modified,signature;
        private  String tr_id,checkin_time,checkout_time,advance_amonut,total_amount,discount,admin_discount,payable_amount,no_of_days;
        int server_status;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(getContext(), "Loading", "Please wait...");
            }

        }

        @Override
        protected Void doInBackground(String... params) {


            try {
                InputStream in = null;
                int resCode = -1;
                String link = Constants.BASEURL + Constants.GUEST_LIST;
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
    "guests": [
        {
            "id": 1,
            "first_name": "Avinash",
            "last_name": "pathak",
            "mobile": "7205674061",
            "address": "MIG - 102",
            "pin": "751019",
            "city": "bhubaneswer",
            "state_id": 1,
            "country_id": 1,
            "photo": "http://a2r.in/atithi/files/guest/file1524449208441262809.jpg",
            "doc_1": "http://a2r.in/atithi/files/guest/file15244492101506905463.jpg",
            "doc_2": "http://a2r.in/atithi/files/guest/file15244492121314018502.jpg",
            "dob": "1988-01-15T00:00:00+00:00",
            "no_of_guest": 5,
            "signature": "http://a2r.in/atithi/files/guest/file15244492121138583141.jpg",
            "created": "2018-04-23T02:06:56+00:00",
            "modified": "2018-04-23T02:06:56+00:00",
            "country": {
                "id": 1,
                "name": "India",
                "is_active": "Y",
                "created": null,
                "modified": null
            },
            "state": {
                "id": 1,
                "name": "Odisha",
                "country_id": 1,
                "is_active": "Y",
                "created": null,
                "modified": null
            }
            "room": {
                "id": 1,
                "hotel_id": 1,
                "room_no": "102",
                "price": 200,
                "is_active": "Y",
                "created": "2018-04-18T00:22:40+00:00",
                "modified": "2018-04-18T00:25:30+00:00"
            },

        }
    ]*/

                if (response != null && response.length() > 0) {
                    JSONObject res = new JSONObject(response);
                    guest_List = new ArrayList<>();

                    JSONArray splist = res.getJSONArray("guests");
                    if (splist.length() > 0) {
                        server_status = 1;
                        for (int i = 0; i < splist.length(); i++) {
                            JSONObject o_list_obj = splist.getJSONObject(i);
                            JSONObject room_obj=o_list_obj.getJSONObject("room");
                            JSONArray tr_array=o_list_obj.getJSONArray("guest_transactions");
                            guest_id = o_list_obj.getString("id");
                            first_name = o_list_obj.getString("first_name");
                            last_name = o_list_obj.getString("last_name");
                            mobile = o_list_obj.getString("mobile");
                            address = o_list_obj.getString("address");
                            city = o_list_obj.getString("city");
                            photo = o_list_obj.getString("photo");
                            doc_1 = o_list_obj.getString("doc_1");
                            doc_2 = o_list_obj.getString("doc_2");
                            created = o_list_obj.getString("created");
                            modified = o_list_obj.getString("modified");
                            signature = o_list_obj.getString("signature");
                            String guest_status = o_list_obj.getString("status");
                            String room_id=room_obj.getString("id");
                            String room_no=room_obj.getString("room_no");
                            String price=room_obj.getString("price");
                            if(tr_array.length()>0){
                                int j;
                                for( j=0; j<tr_array.length();j++){
                                    JSONObject tr = tr_array.getJSONObject(j);
                                    tr_id=tr.getString("id");
                                    checkin_time=tr.getString("checkin");
                                    checkout_time=tr.getString("checkout");
                                    advance_amonut=tr.getString("advance_amonut");
                                    total_amount=tr.getString("total_amount");
                                    discount=tr.getString("discount");
                                    admin_discount=tr.getString("admin_discount");
                                    no_of_days=tr.getString("no_of_days");
                                    payable_amount=tr.getString("payable_amount");

                                }
                            }

                            /*JSONObject obj = o_list_obj.getJSONObject("country");
                            rooml_user_id = obj.getString("id");
                            room_user_name = obj.getString("name");
                            room_user_address = obj.getString("address");
                            room_user_photo = obj.getString("photo");
                            room_user_created = obj.getString("created");
                            room_user_modified = obj.getString("modified");*/


                            Guest_List g_list = new Guest_List(guest_id, first_name, last_name, mobile, address,
                                    city, photo, doc_1, doc_2, created,modified,signature,room_id,room_no,price
                                    ,tr_id,checkin_time,checkout_time,advance_amonut,total_amount,discount,admin_discount,no_of_days,payable_amount,guest_status);
                            guest_List.add(g_list);

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
//                Collections.reverse(guest_List);
                adapter = new Guest_List_Adapter(getContext(), guest_List);
                lv_guests.setAdapter(adapter);
            } else {
                swipe_allguest.setVisibility(View.GONE);
                lv_guests.setVisibility(View.GONE);
                no_guest.setVisibility(View.VISIBLE);
            }

        }
    }
}
