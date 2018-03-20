package humanResources;

import util.LinkedList;

import java.util.Iterator;

import static util.Util.*;

public class Project implements EmployeeGroup
{
    private String name;
    private LinkedList<Employee> employees;

    public Project(String name, Employee[] employees)
    {
        this.name = name;
        this.employees = new LinkedList<Employee>(employees);
    }

    public Project(String name)
    {
        this(name, null);
    }

    @Override
    public void add(Employee employee)
    {
        employees.add(employee);
    }

    @Override
    public Employee[] sortedEmployees()
    {
        Employee[] sorted = Department.mergeSort(employees.toArray(Employee[].class), 0, employees.size());
        fullReverse(sorted, employees.size());
        return sorted;
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
    public boolean remove(String firstName, String lastName)//todo Iterator done
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
    public Employee mostValuableEmployee() //todo iterator done
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
    public int getEmployeesQuantityByJob(JobTitlesEnum jobtitle)//todo Iterator done
    {
        int counter = 0;
        for(Employee employee : employees)
            if(employee.getJobTitle().equals(jobtitle))
                counter++;
        return counter;
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
        //todo через && и с вызовом equals() на списке CHECK
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
}
