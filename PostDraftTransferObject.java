package com.example.b2c_core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by alex on 4/18/17.
 */

public class PostDraftTransferObject
{
    private BuildingType[] _selectedTiles;

    public BuildingType[] getSelectedTiles()
    {
        return _selectedTiles;
    }

    private PostDraftTransferObject(){}

    @JsonCreator
    public static PostDraftTransferObject create(@JsonProperty("_selectedTiles") BuildingType[] selectedTiles)
    {
        PostDraftTransferObject pdto = new PostDraftTransferObject();
        pdto._selectedTiles = selectedTiles;
        return pdto;
    }

}
