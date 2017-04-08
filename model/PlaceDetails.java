package com.example.faiyaz.ixigohackthontrip.model;

import com.google.api.client.util.Key;

import java.io.Serializable;

/**
 * Created by Faiyaz on 08-Apr-17.
 */
public class PlaceDetails implements Serializable {
    @Key
    public String status;

    @Key
    public Place result;

    @Override
    public String toString() {
        if (result!=null) {
            return result.toString();
        }
        return super.toString();
    }
}
