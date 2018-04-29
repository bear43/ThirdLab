package io;

import humanResources.Project;
import humanResources.Employee;
import humanResources.JobTitlesEnum;

public class ControlledProject extends Project
{

    protected boolean isChanged = false;

    public ControlledProject(String name) {
        super(name);
    }

    public ControlledProject(String name, Employee[] e) {
        super(name, e);
    }

    public ControlledProject(Project project)
    {
        super(project.getName(), project.toArray(Employee[].class));
    }

    public boolean isChanged() {
        return isChanged;
    }

    @Override
    public void add(Employee employee)
    {
        super.add(employee);
        isChanged = true;
    }

    @Override
    public boolean remove(int index)
    {
        isChanged = super.remove(index);
        return isChanged;
    }

    @Override
    public boolean remove(Employee employee) {
        isChanged = super.remove(employee);
        return isChanged;
    }

    @Override
    public boolean remove(String firstName, String lastName)
    {
        isChanged = super.remove(firstName, lastName);
        return isChanged;
    }

    @Override
    public int removeAll(Employee obj)
    {
        int c = super.removeAll(obj);
        isChanged = c > 0;
        return c;
    }

}

