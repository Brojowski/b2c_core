package com.example.b2c_core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by alex on 4/18/17.
 */
public class DraftTransferObject
{
    private BuildingType[] _tiles;
    private City[] _cities;

    private DraftTransferObject(){}

    public BuildingType[] getTiles()
    {
        return _tiles;
    }

    public City[] getCities()
    {
        return _cities;
    }

    @JsonCreator
    public static DraftTransferObject create(@JsonProperty("tiles") BuildingType[] tiles, @JsonProperty("cities") City[] cities)
    {
        DraftTransferObject dto = new DraftTransferObject();
        dto._tiles = tiles;
        dto._cities = cities;
        return dto;
    }
}
