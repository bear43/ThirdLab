package io;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;

public class GroupsManagerSerializedFileSource<T extends Serializable> extends GroupsManagerFileSource<T>
{

    @Override
    public void create(T object) throws IOException
    {

    }

    @Override
    public void delete(T object)
    {

    }

    @Override
    public void store(T object) throws IOException
    {

    }

    @Override
    public void load(T object) throws IOException, ParseException
    {

    }
}
