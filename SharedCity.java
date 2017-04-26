package com.example.b2c_core;

/**
 * Created by alex on 4/21/17.
 */
public class SharedCity
{
    private User _left;
    private User _right;
    private City _city;

    public SharedCity(User p1, User p2)
    {
        _left = p1;
        _right = p2;
        _city = new City();
    }

    public User getPlayer1()
    {
        return _left;
    }

    public User getPlayer2()
    {
        return _right;
    }

    public City getCity()
    {
        return _city;
    }
}
