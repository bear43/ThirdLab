package io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public interface Source<T>
{
    void create(T object) throws IOException;
    void delete(T object);
    void store(T object) throws IOException;
    void load(T object) throws IOException, ParseException;
}
