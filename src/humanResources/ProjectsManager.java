package humanResources;

import util.DoubleLinkedList;

import java.util.Iterator;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

public class ProjectsManager implements GroupsManager
{

    private DoubleLinkedList<EmployeeGroup> groups;

    private ProjectsManager(EmployeeGroup[] groups)
    {
        this.groups = new DoubleLinkedList<EmployeeGroup>(groups);
    }

    public ProjectsManager()
    {
        this(new EmployeeGroup[0]);
    }

    @Override
    public int employeesQuantity()
    {
        int employeeCounter = 0;
        for(EmployeeGroup group : groups) //todo iterator
            employeeCounter += group.employeeQuantity();
        return employeeCounter;
    }

    @Override
    public int groupsQuantity()
    {
        return groups.size();
    }

    @Override
    public void add(EmployeeGroup groupable)
    {
        groups.add(groupable);
    }

    @Override
    public EmployeeGroup getEmployeeGroup(String name)
    {
        for(EmployeeGroup group : groups)//tododed
        {
            if(group.getName().equals(name))
                return group;
        }
        return null;
    }

    @Override
    public EmployeeGroup[] getEmployeeGroups()
    {
        return groups.toArray(EmployeeGroup[].class);
    }

    @Override
    public int employeesQuantity(JobTitlesEnum jobTitle)
    {
        int employeeCounter = 0;
        for(EmployeeGroup currentGroup : groups) //todo iterator TODODED
            employeeCounter += currentGroup.getEmployeesQuantityByJob(jobTitle);
        return employeeCounter;
    }

    @Override
    public EmployeeGroup getEmployeesGroup(String firstName, String lastName)
    {
        for(EmployeeGroup currentGroup : groups) //todo iterator TODODED
            if(currentGroup.getEmployee(firstName, lastName) != null)
                return currentGroup;
        return null;
    }

    @Override
    public Employee mostValuableEmployee()
    {
        if(groups == null || groups.size() == 0) return null;
        Employee best = groups.at(0).mostValuableEmployee();
        Employee current;
        for(EmployeeGroup currentGroup : groups) //todo iterator TODO
        {
            current = currentGroup.mostValuableEmployee();
            if(current.getSalary() > best.getSalary())
                best = current;
        }
        return best;
    }

    @Override
    public boolean remove(String groupName)
    {
        Iterator<EmployeeGroup> Iter = groups.iterator();
        while(Iter.hasNext())
        {
            if(Iter.next().getName().equals(groupName))
            {
                Iter.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public int remove(EmployeeGroup group)
    {
        //return groups.removeAll(group);
        return 0;
    }

    //todo toString() equals() hashCode()

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(EmployeeGroup eg : groups)
            sb.append(eg.toString()).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
