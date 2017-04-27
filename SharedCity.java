package com.example.b2c_core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by alex on 4/21/17.
 */
public class SharedCity
{
    private User _right;
    private User _left;
    private City _city;

    private SharedCity()
    {
    }

    public SharedCity(User right, User left)
    {
        _left = left;
        _right = right;
        _city = new City();
    }

    public User getRightPlayer()
    {
        return _right;
    }

    public User getLeftPlayer()
    {
        return _left;
    }

    public City getCity()
    {
        return _city;
    }

    @JsonCreator
    public static SharedCity createSharedCity(@JsonProperty("rightPlayer") User right,
                                              @JsonProperty("leftPlayer")User left,
                                              @JsonProperty("city")City city)
    {
        SharedCity sc = new SharedCity();
        sc._right = right;
        sc._left = left;
        sc._city = city;
        return sc;
    }
}
