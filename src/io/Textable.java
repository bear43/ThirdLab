package io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static util.Util.*;

public interface Textable<T> extends Pathable
{
    String defaultFieldsDelimiter = "\n";
    String toText();
    String toText(Source source) throws IOException;
    T fromText(String text) throws IOException, ParseException;
    T fromText(String text, FileSource source) throws IOException, ParseException;
    static String getClassFromFile(String filename) throws IOException
    {
        return readUTFLine(filename);
    }
}
