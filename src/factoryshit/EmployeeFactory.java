package factoryshit;

import humanResources.Employee;
import humanResources.EmployeeGroup;
import humanResources.GroupsManager;

public abstract class EmployeeFactory
{
    static public EmployeeFactory getEmployeeFactory(GroupsFactoryTypesEnumeration factoryType)
    {
        switch(factoryType)
        {
            case TEXT_FILE_BASED_GROUPS_FACTORY:
                return new TextFileEmployeeFactory();
            case BINARY_FILE_BASED_GROUPS_FACTORY:
                return new BinaryFileEmployeeFactory();
            case SERIALIZED_FILE_BASED_GROUPS_FACTORY:
                return new SerializedEmployeeFactory();
        }
        return new OrdinaryEmployeeFactory();
    }
    public abstract EmployeeGroup createDepartment();
    public abstract EmployeeGroup createDepartment(String name);
    public abstract EmployeeGroup createDepartment(String name, Employee[] e);
    public abstract EmployeeGroup createProject();
    public abstract EmployeeGroup createProject(String name);
    public abstract EmployeeGroup createProject(String name, Employee[] e);
    public abstract GroupsManager createDepartemntManager();
    public abstract GroupsManager createDepartemntManager(String title);
    public abstract GroupsManager createDepartemntManager(String title, EmployeeGroup[] deps);
    public abstract GroupsManager createProjectManager();
    public abstract GroupsManager createProjectManager(String title);
    public abstract GroupsManager createProjectManager(String title, EmployeeGroup[] emps);
}
