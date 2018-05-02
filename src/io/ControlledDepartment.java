package io;

import humanResources.Department;
import humanResources.Employee;
import humanResources.EmployeeGroup;
import humanResources.JobTitlesEnum;

import java.io.IOException;
import java.text.ParseException;
import java.util.StringTokenizer;

import static util.Util.appendBoolToByteArray;
import static util.Util.appendIntToByteArray;
import static util.Util.appendToByteArray;

public class ControlledDepartment extends Department implements Textable<EmployeeGroup>, BinaryView
{

    protected boolean isChanged = false;

    public ControlledDepartment(String name) {
        super(name);
    }

    public ControlledDepartment(String name, Employee[] e) {
        super(name, e);
    }

    public ControlledDepartment(Department department)
    {
        super(department.getName(), department.toArray(Employee[].class));
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
    public int removeAll(JobTitlesEnum jobTitle)
    {
        int c = super.removeAll(jobTitle);
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
    public ControlledDepartment fromText(String text) throws IOException, ParseException
    {
        StringTokenizer st = new StringTokenizer(text, defaultFieldsDelimiter);
        if(st.nextToken().equals(this.getClass().getName()))
        {
            this.setName(st.nextToken());
            this.isChanged = st.nextToken().equals("true");
            while(st.hasMoreTokens())
                this.add(Employee.resurectObject(st.nextToken(), null));
        }
        return this;
    }

    public ControlledDepartment fromText(String text, FileSource source) throws IOException, ParseException
    {
        StringTokenizer st = new StringTokenizer(text, defaultFieldsDelimiter);
        if(st.nextToken().equals(this.getClass().getName()))
        {
            this.setName(st.nextToken());
            this.isChanged = st.nextToken().equals("true");
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


    @Override
    public byte[] toBinary(Source source) throws IOException
    {
        byte[] byteRepresentation = appendToByteArray(this.getClass().getName().getBytes(), this.getName().getBytes());
        byteRepresentation = appendBoolToByteArray(byteRepresentation, this.isChanged);
        for(Employee e : this.getEmployeeList())
            source.create(e);
        return byteRepresentation;
    }

    @Override
    public void fromBinary(byte[] rawBytes, Source source)
    {

    }

    @Override
    public int getBytesAmount()
    {
        return BinaryView.getBinarySizeOfString(this.getClass().getName()) +
                BinaryView.getBinarySizeOfString(this.getName()) +
                BinaryView.AMOUNT_OF_BYTES_TO_INT_TYPE;
    }
}
