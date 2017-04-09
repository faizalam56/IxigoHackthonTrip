package com.example.faiyaz.ixigohackthontrip;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.faiyaz.ixigohackthontrip.model.Place;
import com.example.faiyaz.ixigohackthontrip.model.PlaceFindToSearch;
import com.example.faiyaz.ixigohackthontrip.model.PlacesList;
import com.example.faiyaz.ixigohackthontrip.network.GooglePlaces;
import com.example.faiyaz.ixigohackthontrip.util.AlertDialogManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceListActivity extends AppCompatActivity {

    Boolean isInternetPresent = false;
    AlertDialogManager alert = new AlertDialogManager();

    GooglePlaces googlePlaces;
    PlacesList nearPlaces;

    Button btnShowOnMap;
    ProgressDialog pDialog;
    ListView lv;
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
    List<PlaceFindToSearch> placeList;
    double lat,lon;

    public static String KEY_REFERENCE = "reference";
    public static String KEY_NAME = "name";
    public static String KEY_VICINITY = "vicinity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        if(!isConnectingToInternet()){
            alert.showAlertDialog(this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            return;
        }

        lv = (ListView) findViewById(R.id.list);
        btnShowOnMap = (Button)findViewById(R.id.btn_show_map);

        placeList = getPlaceDetails();

        for(int i=0;i<placeList.size();i++){
            lat = placeList.get(i).getLat();
            lon = placeList.get(i).getLon();
        }

        new LoadPlaces().execute();
        btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),PlacesMapActivity.class);
                i.putExtra("user_latitude", lat);
                i.putExtra("user_longitude",lon);
                i.putExtra("near_places", nearPlaces);
                startActivity(i);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();
                Intent in = new Intent(getApplicationContext(), SinglePlaceActivity.class);

                in.putExtra(KEY_REFERENCE, reference);
                startActivity(in);
            }
        });
    }

   private boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
   }

    private List<PlaceFindToSearch> getPlaceDetails(){
        List<PlaceFindToSearch>  list = new ArrayList<PlaceFindToSearch>();
        PlaceFindToSearch placeFindToSearch = new PlaceFindToSearch();
        placeFindToSearch.getText();
        placeFindToSearch.getUrl();
        placeFindToSearch.get_id();
        placeFindToSearch.getLat();
        placeFindToSearch.getLon();

        list.add(placeFindToSearch);

        return list;
    }

    class LoadPlaces extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PlaceListActivity.this);
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {

            googlePlaces = new GooglePlaces();

            try {
                String types = "cafe|restaurant"; // Listing places only cafes, restaurants
                double radius = 1000;
                nearPlaces = googlePlaces.search(lat,lon, radius, types);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            runOnUiThread(new Runnable() {
                public void run() {

                    String status = nearPlaces.status;
                    if(status.equals("OK")){
                        if (nearPlaces.results != null) {
                            for (Place p : nearPlaces.results) {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(KEY_REFERENCE, p.reference);
                                map.put(KEY_NAME, p.name);
                                placesListItems.add(map);
                            }
                            ListAdapter adapter = new SimpleAdapter(PlaceListActivity.this, placesListItems,
                                    R.layout.list_item,
                                    new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
                                    R.id.reference, R.id.name });
                            lv.setAdapter(adapter);
                        }
                    }
                    else if(status.equals("ZERO_RESULTS")){
                        alert.showAlertDialog(PlaceListActivity.this, "Near Places",
                                "Sorry no places found. Try to change the types of places",
                                false);
                    }
                    else if(status.equals("UNKNOWN_ERROR"))
                    {
                        alert.showAlertDialog(PlaceListActivity.this, "Places Error",
                                "Sorry unknown error occured.",
                                false);
                    }
                    else if(status.equals("OVER_QUERY_LIMIT"))
                    {
                        alert.showAlertDialog(PlaceListActivity.this, "Places Error",
                                "Sorry query limit to google places is reached",
                                false);
                    }
                    else if(status.equals("REQUEST_DENIED"))
                    {
                        alert.showAlertDialog(PlaceListActivity.this, "Places Error",
                                "Sorry error occured. Request is denied",
                                false);
                    }
                    else if(status.equals("INVALID_REQUEST"))
                    {
                        alert.showAlertDialog(PlaceListActivity.this, "Places Error",
                                "Sorry error occured. Invalid Request",
                                false);
                    }
                    else
                    {
                        alert.showAlertDialog(PlaceListActivity.this, "Places Error",
                                "Sorry error occured.",
                                false);
                    }
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_place_list,menu);
        return true;
    }
}
