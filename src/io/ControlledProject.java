package io;

import humanResources.EmployeeGroup;
import humanResources.Project;
import humanResources.Employee;

import java.io.IOException;
import java.text.ParseException;
import java.util.StringTokenizer;

public class ControlledProject extends Project implements Textable<EmployeeGroup>
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

    @Override
    public String toText(Source source) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(defaultFieldsDelimiter).//Имя класса
                append(this.getName()).append(defaultFieldsDelimiter).//Имя объекта
                append(this.isChanged);//Состояние переменной
        try {

            for (Employee e : this.getEmployeeList()) {
                source.create(e);
                sb.append(defaultFieldsDelimiter).append(e.getFileName());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public ControlledProject fromText(String text) throws IOException, ParseException
    {
        StringTokenizer st = new StringTokenizer(text, defaultFieldsDelimiter);
        if(st.nextToken().equals(this.getClass().getName()))
        {
            this.setName(st.nextToken());
            this.isChanged = st.nextToken().equals("True");
            while(st.hasMoreTokens())
                this.add(Employee.resurectObject(st.nextToken(), null));
        }
        return this;
    }

    public ControlledProject fromText(String text, FileSource source) throws IOException, ParseException
    {
        StringTokenizer st = new StringTokenizer(text, defaultFieldsDelimiter);
        if(st.nextToken().equals(this.getClass().getName()))
        {
            this.setName(st.nextToken());
            this.isChanged = st.nextToken().equals("True");
            while(st.hasMoreTokens())
                this.add(Employee.resurectObject(st.nextToken(), source));
        }
        return this;
    }

    @Override
    public String getFileName()
    {
        return super.getFileName();
    }

}

