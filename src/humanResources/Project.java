package humanResources;

import io.FileSource;
import io.Source;
import util.LinkedList;
import util.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import static util.Util.*;

public class Project implements EmployeeGroup
{
    private final static String defaultExtension = "prj";
    private String name;
    private LinkedList<Employee> employees;

    public Project(String name, Employee[] employees)
    {
        this.name = name;
        this.employees = new LinkedList<>(employees);
    }

    public Project(String name)
    {
        this(name, null);
    }

    @Override
    public int size() {
        return employees.size();
    }

    @Override
    public void add(Employee employee)
    {
        try {
            if (employee == null) throw new NullPointerException();
            if (employees.contains(employee)) throw new AlreadyAddedException();
            employees.add(employee);
        }
        catch(AlreadyAddedException ex)
        {
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public boolean remove(int index) {
        return employees.remove(index);
    }

    @Override
    public Employee[] sortedEmployees()
    {
        //Employee[] sorted = Department.mergeSort(employees.toArray(Employee[].class), 0, employees.size());
        //fullReverse(sorted, employees.size());
        Employee[] ret = employees.toArray(Employee[].class);
        Arrays.sort(ret);
        return ret;
    }

    @Override
    public Employee[] getEmployees() {
        return employees.toArray(Employee[].class);
    }

    @Override
    public int employeeQuantity() {
        return employees.size();
    }

    @Override
    public boolean remove(String firstName, String lastName)
    {
        Iterator<Employee> iterator = employees.iterator();
        while(iterator.hasNext())
        {
            if(iterator.next().compareByName(firstName, lastName))
            {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Employee employee) {
        return employees.remove(employee);
    }

    @Override
    public Employee at(int index)
    {
        return employees.at(index);
    }

    @Override
    public Employee pop_back()
    {
        return employees.pop_back();
    }

    @Override
    public boolean contains(Employee obj)
    {
        return employees.contains(obj);
    }

    @Override
    public int indexOf(Employee obj)
    {
        return employees.indexOf(obj);
    }

    @Override
    public Employee[] toArray(Class<Employee[]> type)
    {
        return employees.toArray(Employee[].class);
    }

    @Override
    public Employee getEmployee(String firstName, String lastName)
    {
        for(Employee e : employees)
        {
            if(e.compareByName(firstName, lastName))
                return e;
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Employee mostValuableEmployee()
    {
        Employee best = employees.at(0);
        for(Employee e : employees)
        {
            if(best.getSalary() < e.getSalary())
                best = e;
        }
        return best;
    }

    @Override
    public int getEmployeesQuantityByJob(JobTitlesEnum jobtitle)
    {
        int counter = 0;
        for(Employee employee : employees)
            if(employee.getJobTitle().equals(jobtitle))
                counter++;
        return counter;
    }

    @Override
    public int getPartTimeEmployeeQuantity()
    {
        return getPartTimeEmployeeCount(employees);
    }

    @Override
    public int getStaffEmployeeQuantity()
    {
        return getStaffEmployeeCount(employees);
    }

    @Override
    public int getTravellingEmployeeQuantity()
    {
        return Util.getTravellingEmployeeQuantity(employees);
    }

    @Override
    public Employee[] getTravellingEmployeeOnDate(Date beginDate, Date endDate)
    {
        return Util.getTravellingEmployeeOnDate(employees, beginDate, endDate);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Project ");
        if(name != null) sb.append(name);
        sb.append(" : ");
        if(employees.size() != 0) sb.append(employees.size());
        sb.append("\n");
        sb.append(employees.toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof Project) &&
                this.name.equals(((Project) obj).name) &&
                this.employees.equals(((Project) obj).employees);
    }

    @Override
    public int hashCode()
    {
        int hash = name.hashCode();
        hash ^= employees.hashCode();
        return hash;
    }

    @Override
    public Iterator<Employee> iterator() {
        return employees.iterator();
    }

    @Override
    public int removeAll(Employee obj)
    {
        int counter = 0;
        Iterator<Employee> iter = employees.iterator();
        while(iter.hasNext())
        {
            if(iter.next().equals(obj))
            {
                iter.remove();
                counter++;
            }
        }
        return counter;
    }

    @Override
    public String toText() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(defaultFieldsDelimiter).//Имя класса
                append(this.getName()).append(defaultFieldsDelimiter);//Имя объекта
        for(Employee e : this)
            sb.append(e.getFileName()).append(defaultFieldsDelimiter);
        return sb.toString();
    }

    @Override
    public String toText(Source source)
    {
        return null;
    }

    @Override
    public EmployeeGroup fromText(String text) throws IOException, ParseException {
        StringTokenizer st = new StringTokenizer(text, defaultFieldsDelimiter);
        if(st.nextToken().equals(this.getClass().getName()))
        {
            this.setName(st.nextToken());
            while(st.hasMoreTokens())
                this.add(Employee.resurectObject(st.nextToken(), null));
        }
        return this;
    }

    @Override
    public EmployeeGroup fromText(String text, FileSource source){
        return null;
    }

    @Override
    public String getFileName()
    {
        return this.name;
    }
}
