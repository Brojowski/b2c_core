package com.example.b2c_core;

/**
 * Created by alex on 4/21/17.
 */
public class SharedCity
{
    private User _right;
    private User _left;
    private City _city;

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
}
