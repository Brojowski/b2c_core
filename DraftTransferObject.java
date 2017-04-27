package com.example.b2c_core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by alex on 4/18/17.
 */
public class DraftTransferObject implements Serializable
{
    public User currentUser;
    public BuildingType[] availableTiles;
    public SharedCity leftCity;
    public SharedCity rightCity;
    public SharedCity[] otherCities;
}
