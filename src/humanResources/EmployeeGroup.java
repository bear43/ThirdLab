package humanResources;

public interface EmployeeGroup
{
    void add(Employee employee);
    Employee[] sortedEmployees();
    Employee[] getEmployees();
    int employeeQuantity();
    boolean remove(String firstName, String lastName);
    boolean remove(Employee employee);
    Employee getEmployee(String firstName, String lastName);
    String getName();
    void setName(String name);
    Employee mostValuableEmployee();
    int getEmployeesQuantityByJob(JobTitlesEnum jobTitle);
}
