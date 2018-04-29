package io;

import humanResources.AlreadyAddedException;
import humanResources.DepartmentsManager;
import humanResources.EmployeeGroup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class ControlledProjectManager extends DepartmentsManager
{

    protected Source<EmployeeGroup> source;

    public ControlledProjectManager(String title, EmployeeGroup[] deps) {
        super(title, deps);
    }

    public ControlledProjectManager(String title) {
        super(title);
    }

    public Source<EmployeeGroup> getSource() {
        return source;
    }

    public void setSource(Source<EmployeeGroup> source) {
        this.source = source;
    }

    @Override
    public void add(EmployeeGroup group) throws AlreadyAddedException
    {
        source = new GroupsManagerTextFileSource<>(group.getFileName());
        try {
            source.create(group);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        super.add(group);
    }


    @Override
    public int remove(EmployeeGroup group)
    {
        source = new GroupsManagerTextFileSource<>(group.getFileName());
        source.delete(group);
        return super.remove(group);
    }

    public void store() throws IOException
    {
        for(EmployeeGroup group : this.getEmployeeGroups())
            if(group instanceof ControlledDepartment && ((ControlledDepartment) group).isChanged)
            {
                source = new GroupsManagerTextFileSource<>(group.getFileName());
                source.store(group);
            }
    }

    public void load() throws IOException, ParseException
    {
        for(EmployeeGroup group : this.getEmployeeGroups())
        {
            source = new GroupsManagerTextFileSource<>(group.getFileName());
            source.load(group);
        }
    }
}