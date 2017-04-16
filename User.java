package com.example.b2c_core;

/**
 *
 */
public class User
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

    public int getId()
    {
        return i;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof String)
        {
            String toCompare = (String)o;
            return uname.compareTo(toCompare) == 0;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return uname.hashCode();
    }

    public static User exampleUser()
    {
        return new User("Test"+ i++);
    }
}
