package humanResources;

public interface GroupsManager
{
    int employeesQuantity();
    int groupsQuantity();
    void add(EmployeeGroup groupable);
    EmployeeGroup getEmployeeGroup(String name);
    EmployeeGroup[] getEmployeeGroups();
    int employeesQuantity(JobTitlesEnum jobTitle);
    EmployeeGroup getEmployeesGroup(String firstName, String lastName);
    Employee mostValuableEmployee();
    boolean remove(String groupName);
    int remove(EmployeeGroup group);
}
