package com.example.faiyaz.ixigohackthontrip.model;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by Faiyaz on 08-Apr-17.
 */
public class PlacesList {
    @Key
    public String status;

    @Key
    public List<Place> results;
}
