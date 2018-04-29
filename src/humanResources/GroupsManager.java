package humanResources;

import util.List;

import java.util.Date;

public interface GroupsManager
{
    int employeesQuantity();
    int groupsQuantity();
    void add(EmployeeGroup groupable) throws AlreadyAddedException;
    EmployeeGroup getEmployeeGroup(String name);
    EmployeeGroup[] getEmployeeGroups();
    int employeesQuantity(JobTitlesEnum jobTitle);
    EmployeeGroup getEmployeesGroup(String firstName, String lastName);
    Employee mostValuableEmployee();
    boolean remove(String groupName);
    int remove(EmployeeGroup group);
    int getPartTimeEmployeeQuantity();
    int getStaffEmployeeQuantity();
    int getTravellingEmployeeQuantity();
    Employee[] getTravellingEmployeeOnDate(Date beginDage, Date endDate);
}
