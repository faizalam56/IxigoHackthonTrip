package com.example.faiyaz.ixigohackthontrip;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.faiyaz.ixigohackthontrip.model.PlaceFindToSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faiyaz on 08-Apr-17.
 */
public class PlaceListFragment extends Fragment {
    ListView lv;
    Button btnShowOnMap;
    PlaceListFragmentCommunication communication;
    List<PlaceFindToSearch> placeList;
    double lat,lon;

    public void setPlaceListFragmentCommunication(PlaceListFragmentCommunication communication){
        this.communication=communication;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeList = getPlaceDetails();

        for(int i=0;i<placeList.size();i++){
             lat = placeList.get(i).getLat();
            lon = placeList.get(i).getLon();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.placelist_fragment, container, false);
        lv = (ListView) getActivity().findViewById(R.id.list);
        btnShowOnMap = (Button) getActivity().findViewById(R.id.btn_show_map);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  communication.showMap(lat,lon);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 communication.showSinglePlaceDetails("reference");
            }
        });
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
    interface PlaceListFragmentCommunication{
        public void showMap(double lat,double lon);
        public void showSinglePlaceDetails(String reference);
    }
}
