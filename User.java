package com.example.b2c_core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 *
 */
public class User implements Serializable
{
    private static int i = 0;
    private String uname;

    private User(String username)
    {
        uname = username;
    }

    public String getUsername()
    {
        return uname;
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof User)
                && (this.hashCode() == o.hashCode());
    }

    @Override
    public String toString()
    {
        return uname;
    }

    @Override
    public int hashCode()
    {
        return uname.hashCode();
    }

    public static User exampleUser()
    {
        return new User("Test" + i++);
    }

    @JsonCreator
    public static User createUser(@JsonProperty("username") String uname)
    {
        return new User(uname);
    }
}
