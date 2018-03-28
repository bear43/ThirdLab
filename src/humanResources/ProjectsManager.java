package humanResources;

import util.DoubleLinkedList;

import java.util.Date;
import java.util.Iterator;

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
        for(EmployeeGroup group : groups)
            employeeCounter += group.employeeQuantity();
        return employeeCounter;
    }

    @Override
    public int groupsQuantity()
    {
        return groups.size();
    }

    @Override
    public void add(EmployeeGroup groupable) throws AlreadyAddedException
    {
        if(groups.contains(groupable)) throw new AlreadyAddedException();
        groups.add(groupable);
    }

    @Override
    public EmployeeGroup getEmployeeGroup(String name)
    {
        for(EmployeeGroup group : groups)
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
        for(EmployeeGroup currentGroup : groups)
            employeeCounter += currentGroup.getEmployeesQuantityByJob(jobTitle);
        return employeeCounter;
    }

    @Override
    public EmployeeGroup getEmployeesGroup(String firstName, String lastName)
    {
        for(EmployeeGroup currentGroup : groups)
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
        for(EmployeeGroup currentGroup : groups)
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

    @Override
    public int getPartTimeEmployeeQuantity() {
        return 0;
    }

    @Override
    public int getStaffEmployeeQuantity() {
        return 0;
    }

    @Override
    public int getTravellingEmployeeQuantity() {
        return 0;
    }

    @Override
    public Employee[] getTravellingEmployeeOnDate(Date beginDage, Date endDate) {
        return new Employee[0];
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(EmployeeGroup eg : groups)
            sb.append(eg.toString()).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof ProjectsManager)
                && (this.groups.equals(((ProjectsManager) obj).groups));
    }

    @Override
    public int hashCode()
    {
        return groups.hashCode();
    }

}
