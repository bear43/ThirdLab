package factoryshit;

import humanResources.*;

public class OrdinaryEmployeeFactory extends EmployeeFactory
{

    @Override
    public EmployeeGroup createDepartment() {
        return new Department("");
    }

    @Override
    public EmployeeGroup createDepartment(String name) {
        return new Department(name);
    }

    @Override
    public EmployeeGroup createDepartment(String name, Employee[] e)
    {
        return new Department(name, e);
    }

    @Override
    public EmployeeGroup createProject()
    {
        return new Project("");
    }

    @Override
    public EmployeeGroup createProject(String name) {
        return new Project(name);
    }

    @Override
    public EmployeeGroup createProject(String name, Employee[] e)
    {
        return new Project(name, e);
    }

    @Override
    public GroupsManager createDepartemntManager() {
        return new DepartmentsManager("");
    }
    @Override
    public GroupsManager createDepartemntManager(String title) {
        return new DepartmentsManager(title);
    }
    @Override
    public GroupsManager createDepartemntManager(String title, EmployeeGroup[] deps)
    {
        return new DepartmentsManager(title, deps);
    }

    @Override
    public GroupsManager createProjectManager() {
        return new ProjectsManager();
    }

    @Override
    public GroupsManager createProjectManager(String title) {
        ProjectsManager man =  new ProjectsManager();
        man.setName(title);
        return man;
    }

    @Override
    public GroupsManager createProjectManager(String title, EmployeeGroup[] emps) {
        ProjectsManager man =  new ProjectsManager();
        man.setName(title);
        try {
            for (EmployeeGroup group : emps)
                man.add(group);
        }
        catch(AlreadyAddedException ex)
        {
            ex.printStackTrace();
        }
        return man;
    }
}
