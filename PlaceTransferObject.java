package com.example.b2c_core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by alex on 4/18/17.
 */
public class PlaceTransferObject
{
    private BuildingType[] _myTiles;
    private BuildingType[][] _othersToPlace;
    private City[] _myCities;
    private City[] _otherCities;

    private PlaceTransferObject()
    {
    }

    public BuildingType[] getMyTiles()
    {
        return _myTiles;
    }

    public BuildingType[][] getTilesToPlace()
    {
        return _othersToPlace;
    }

    public City[] getMyCities()
    {
        return _myCities;
    }

    public City[] getOtherCities()
    {
        return _otherCities;
    }

    @JsonCreator
    public static PlaceTransferObject create(@JsonProperty("myTiles") BuildingType[] myTiles,
                                             @JsonProperty("tilesToPlace") BuildingType[][] othersToPlace,
                                             @JsonProperty("myCities") City[] myCities,
                                             @JsonProperty("otherCities") City[] _otherCities)
    {
        PlaceTransferObject pto = new PlaceTransferObject();
        pto._myTiles = myTiles;
        pto._othersToPlace = othersToPlace;
        pto._myCities = myCities;
        pto._otherCities = _otherCities;
        return pto;
    }
}
