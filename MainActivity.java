package com.example.faiyaz.ixigohackthontrip;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.faiyaz.ixigohackthontrip.app.AppController;
import com.example.faiyaz.ixigohackthontrip.model.PlaceFindToSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SelectCityFragment.SubmitActionCommunicator {
     android.support.v4.app.FragmentManager fragmentManager;
    String url="http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=";
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState==null){
            SelectCityFragment selectCityFragment = new SelectCityFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_menu,selectCityFragment,"selectcity");
            fragmentTransaction.commit();
            selectCityFragment.setSubmitActionCommunicator(this);
        }
    }

    @Override
    public void submitCityName(String cityname) {
          url = url+""+cityname;
          showProgressDialog();

        JsonArrayRequest placesFind = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("MainActivity",response.toString());
                hidePDialog();
                //parsing JSON.
                for(int i=0;i<response.length();i++){
                    try{
                        JSONObject obj = response.getJSONObject(i);

                        PlaceFindToSearch placeFindToSearch = new PlaceFindToSearch();
                        placeFindToSearch.setText(obj.getString("text"));
                        placeFindToSearch.setUrl(obj.getString("url"));
                        placeFindToSearch.set_id(obj.getString("_id"));
                        placeFindToSearch.setLat(obj.getString("lat"));
                        placeFindToSearch.setLon(obj.getString("lon"));

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(placesFind);
    }

    private void showProgressDialog(){
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
