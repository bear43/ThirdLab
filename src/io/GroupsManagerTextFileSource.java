package io;

import humanResources.EmployeeGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static util.Util.readUTFFile;
import static util.Util.writeUTFFile;

public class GroupsManagerTextFileSource<T extends Textable> extends GroupsManagerFileSource<T>
{

    public static String currentPath;

    T loadedObject;

    public GroupsManagerTextFileSource(String path)
    {
        File f = new File(path);
        f.mkdir();
        this.path = f.getAbsolutePath();
    }

    public GroupsManagerTextFileSource(String additionPath, GroupsManagerTextFileSource oldSource)
    {
        File f = new File(oldSource.path + "\\" + additionPath);
        f.mkdir();
        this.path = f.getAbsolutePath();
    }

    public GroupsManagerTextFileSource()
    {
    }

    @Override
    public void create(T object) throws IOException
    {
        writeUTFFile(String.format("%s\\%s", this.path, object.getFileName()), object.toText(this));
    }

    @Override
    public void delete(T object)
    {
        File file = new File(String.format("%s\\%s", this.path, object.getFileName()));
        file.delete();
    }

    @Override
    public void store(T object) throws IOException
    {
        create(object);
    }

    @Override
    public void load(T object) throws IOException, ParseException
    {
        object.fromText(readUTFFile(String.format("%s\\%s", this.path, object.getFileName())));
    }

    @Override
    public void setPath(String path)
    {
        File f = new File(path);
        if(!f.isDirectory()) f.mkdir();
        this.path = f.getAbsolutePath();
        currentPath = this.path;
    }
}
