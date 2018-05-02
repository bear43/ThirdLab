package io;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static io.BinaryView.*;

public class GroupsManagerBinaryFileSource<T extends BinaryView> extends GroupsManagerFileSource<T>
{
    public static String currentPath;

    public GroupsManagerBinaryFileSource(String path)
    {
        File f = new File(path);
        f.mkdir();
        this.path = f.getAbsolutePath();
    }

    public GroupsManagerBinaryFileSource(String additionPath, GroupsManagerTextFileSource oldSource)
    {
        File f = new File(oldSource.path + "\\" + additionPath);
        f.mkdir();
        this.path = f.getAbsolutePath();
    }

    public GroupsManagerBinaryFileSource()
    {
    }

    @Override
    public void create(T object) throws IOException
    {
        writeBinaryFile(String.format("%s\\%s", this.path, object.getFileName()), object.toBinary(this), true);
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
        object.fromBinary(readBinaryFile(String.format("%s\\%s", this.path, object.getFileName())), this);
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
