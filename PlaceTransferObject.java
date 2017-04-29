package com.example.b2c_core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by alex on 4/18/17.
 */
public class PlaceTransferObject implements Serializable
{
    public User currentUser;
    public Map<User, BuildingType[]> tiles;
    public SharedCity leftCity;
    public SharedCity rightCity;
    public SharedCity[] otherCities;
}
