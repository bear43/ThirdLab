package humanResources;

import util.LinkedList;

import java.util.Date;

public interface EmployeeGroup
{
    void add(Employee employee) throws AlreadyAddedException;
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
    int getPartTimeEmployeeQuantity();
    int getStaffEmployeeQuantity();
    int getTravellingEmployeeQuantity();
    Employee[] getTravellingEmployeeOnDate(Date beginDate, Date endDate);
}
