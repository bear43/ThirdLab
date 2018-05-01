package io;

import humanResources.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static util.Util.readUTFFile;

public class ControlledDepartmentManager extends DepartmentsManager implements Textable<ControlledDepartmentManager>
{

    protected FileSource<EmployeeGroup> source;

    public ControlledDepartmentManager(String title, EmployeeGroup[] deps) {
        super(title, deps);
        source = new GroupsManagerTextFileSource<>(title);
    }

    public ControlledDepartmentManager(String title) {
        super(title);
        source = new GroupsManagerTextFileSource<>(title);
    }

    public FileSource<EmployeeGroup> getSource() {
        return source;
    }

    public void setSource(FileSource<EmployeeGroup> source) {
        this.source = source;
    }

    @Override
    public void add(EmployeeGroup group) throws AlreadyAddedException
    {
        try
        {
            source.setPath(String.format("%s\\%s", source.getPath(), group.getFileName()));
            source.create(group);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        super.add(group);
    }


    @Override
    public int remove(EmployeeGroup group)
    {
        source.setPath(String.format("%s\\%s", source.getPath(), group.getFileName()));
        source.delete(group);
        return super.remove(group);
    }

    public void store() throws IOException
    {
        String root = source.getPath();
        for(EmployeeGroup group : this.getEmployeeGroups())
            if(group instanceof ControlledDepartment && ((ControlledDepartment) group).isChanged)
            {
                source.setPath(String.format("%s\\%s", source.getPath(), group.getFileName()));
                source.store(group);
                source.setPath(root);
            }
    }

    public void load() throws IOException, ParseException
    {
        String root = source.getPath();
        for(EmployeeGroup group : this.getEmployeeGroups())
        {
            source.setPath(String.format("%s\\%s", source.getPath(), group.getFileName()));
            source.load(group);
            source.setPath(root);
        }
    }

    @Override
    public String toText() {
        return null;
    }

    @Override
    public String toText(Source source){
        return null;
    }

    @Override
    public ControlledDepartmentManager fromText(String text)
    {
        return null;
    }

    @Override
    public ControlledDepartmentManager fromText(String text, FileSource source){
        return null;
    }

    @Override
    public String getFileName() {
        return super.getFileName();
    }

    public void restore() throws IOException, ParseException, AlreadyAddedException
    {
        File root = new File(source.getPath());
        File[] files = root.listFiles(File::isDirectory);
        String name;
        String textRepresentation = "";
        for(File directory : files)
        {
            name = directory.getName();
            source.setPath(directory.getAbsolutePath());
            textRepresentation = readUTFFile(source.getPath()+"\\"+name);
            super.add(new ControlledDepartment(name).fromText(textRepresentation, source));
            source.setPath(root.getAbsolutePath());
        }
    }
}
