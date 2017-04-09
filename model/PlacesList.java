package com.example.faiyaz.ixigohackthontrip.model;

import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Faiyaz on 08-Apr-17.
 */
public class PlacesList implements Serializable{
    @Key
    public String status;

    @Key
    public List<Place> results;
}
