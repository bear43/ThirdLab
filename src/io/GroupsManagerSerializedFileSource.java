package io;

import humanResources.AlreadyAddedException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;

import static util.Util.deserializeObject;
import static util.Util.serializeObject;

public class GroupsManagerSerializedFileSource<T extends Serializable> extends GroupsManagerFileSource<T>
{

    public GroupsManagerSerializedFileSource(String sourceTitle)
    {
        File f = new File(sourceTitle);
        f.mkdir();
        this.path = f.getAbsolutePath();
    }

    public GroupsManagerSerializedFileSource()
    {

    }

    @Override
    public void create(T object) throws IOException
    {
        File file = new File(getPath());
        file.mkdir();
        serializeObject(object, file.getAbsolutePath() + PATH_DELIMITER + ((Controllable) object).getName());
    }

    @Override
    public void delete(T object)
    {
        File file;
        if(object instanceof ControlledDepartmentManager || object instanceof ControlledProjectManager)
        {
            file = new File(getPath());
            file.delete();
        }
    }

    @Override
    public void store(T object) throws IOException
    {
        create(object);
    }

    @Override
    public void load(T object) throws IOException, ParseException
    {
        try
        {
            Object restorredObj = deserializeObject(((Controllable) object).getName());
            if(restorredObj instanceof ControlledProjectManager)
            {
                ((ControlledProjectManager)object).fromText(((ControlledProjectManager) restorredObj).toText());//Это дерьмо сосёт, ибо нет передачи ссылки на участок памяти для модификации содержимого
            }
            else if(restorredObj instanceof ControlledDepartmentManager)
            {
                ((ControlledDepartmentManager)object).fromText(((ControlledDepartmentManager) restorredObj).toText());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
