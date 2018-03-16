package humanResources;

import util.DoubleLinkedList;

public class ProjectsManager implements GroupsManager
{

    private DoubleLinkedList<EmployeeGroup> groups;

    public ProjectsManager(EmployeeGroup[] groups)
    {
        this.groups = new DoubleLinkedList<EmployeeGroup>(groups, EmployeeGroup[].class);
    }

    public ProjectsManager()
    {
        this(null);
    }

    @Override
    public int employeesQuantity()
    {
        int employeeCounter = 0;
        for(EmployeeGroup group : groups.toArray(EmployeeGroup[].class)) //todo iterator
            employeeCounter += group.employeeQuantity();
        return employeeCounter;
    }

    @Override
    public int groupsQuantity()
    {
        return groups.length();
    }

    @Override
    public void add(EmployeeGroup groupable)
    {
        groups.push(groupable);
    }

    @Override
    public EmployeeGroup getEmployeeGroup(String name)
    {
        EmployeeGroup[] groupArray = groups.toArray(EmployeeGroup[].class);
        for(int i = 0; i < groups.length(); i++) //todo iterator
            if(groupArray[i].getName().equals(name))
                return groupArray[i];
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
        for(EmployeeGroup currentGroup : groups.toArray(EmployeeGroup[].class)) //todo iterator
            employeeCounter += currentGroup.getEmployeesQuantityByJob(jobTitle);
        return employeeCounter;
    }

    @Override
    public EmployeeGroup getEmployeesGroup(String firstName, String lastName)
    {
        for(EmployeeGroup currentGroup : groups.toArray(EmployeeGroup[].class)) //todo iterator
            if(currentGroup.getEmployee(firstName, lastName) != null)
                return currentGroup;
        return null;
    }

    @Override
    public Employee mostValuableEmployee()
    {
        if(groups == null || groups.length() == 0) return null;
        Employee best = groups.pop_at(0).mostValuableEmployee();
        Employee current;
        for(EmployeeGroup currentGroup : groups.toArray(EmployeeGroup[].class)) //todo iterator
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
        EmployeeGroup[] groupsArray = groups.toArray(EmployeeGroup[].class);
        for(int i = 0; i < groups.length(); i++) //todo iterator
        {
            if(groupsArray[i].getName().equals(groupName))
                return groups.remove(i);
        }
        return false;
    }

    @Override
    public int remove(EmployeeGroup group)
    {
        return groups.removeAll(group); //todo
    }

    //todo toString() equals() hashCode()
}
