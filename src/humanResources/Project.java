package humanResources;

import util.LinkedList;

import static util.Util.*;

public class Project implements EmployeeGroup
{
    private String name;
    private LinkedList<Employee> employees;

    public Project(String name, Employee[] employees)
    {
        this.name = name;
        this.employees = new LinkedList<Employee>(employees, Employee[].class);
    }

    public Project(String name)
    {
        this(name, null);
    }

    @Override
    public void add(Employee employee)
    {
        employees.push(employee);
    }

    @Override
    public Employee[] sortedEmployees()
    {
        Employee[] sorted = Department.mergeSort(employees.toArray(Employee[].class), 0, employees.length());
        fullReverse(sorted, employees.length());
        return sorted;
    }

    @Override
    public Employee[] getEmployees() {
        return employees.toArray(Employee[].class);
    }

    @Override
    public int employeeQuantity() {
        return employees.length();
    }

    @Override
    public boolean remove(String firstName, String lastName) {
        for(int i = 0; i < employees.length(); i++)
            if (employees.pop_at(i).compareByName(firstName, lastName)) //todo iterator
                return employees.remove(i);
        return false;
    }

    @Override
    public boolean remove(Employee employee) {
        return employees.remove(employee);
    }

    @Override
    public Employee getEmployee(String firstName, String lastName)
    {
        Employee employee;
        for(int i = 0; i < employees.length(); i++)
        {
            employee = employees.pop_at(i); //todo iterator
            if (employee.compareByName(firstName, lastName)) return employee;
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
    public Employee mostValuableEmployee() {
        Employee current;
        Employee best = employees.pop_begin();
        for(int i = 1; i < employees.length(); i++) //todo iterator
        {
            current = employees.pop_at(i);
            if(current.getSalary() > best.getSalary()) best = current;
        }
        return best;
    }

    @Override
    public int getEmployeesQuantityByJob(JobTitlesEnum jobtitle)
    {
        int counter = 0;
        for(Employee e : employees.toArray(Employee[].class)) //todo iterator
            if(e.getJobTitle().equals(jobtitle)) counter++;
        return counter;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Project ");
        if(name != null) sb.append(name);
        sb.append(" : ");
        if(employees.length() != 0) sb.append(employees.length());
        sb.append("\n");
        sb.append(employees.toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        //todo через && и с вызовом equals() на списке
        if(!(obj instanceof Project)) return false;
        Project comparing = (Project)obj;
        if(!this.name.equals(comparing.name)) return false;
        if(this.employees.length() != comparing.employees.length()) return false;
        for(int i = 0; i < employees.length(); i++)
            if(!employees.contains(comparing.employees.pop_at(i))) return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = name.hashCode();
        hash ^= employees.hashCode();
        return hash;
    }
}
