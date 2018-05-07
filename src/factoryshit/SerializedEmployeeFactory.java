package factoryshit;

import humanResources.Employee;
import humanResources.EmployeeGroup;
import humanResources.GroupsManager;
import io.*;

public class SerializedEmployeeFactory extends EmployeeFactory
{

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public EmployeeGroup createDepartment() {
        return new ControlledDepartment("");
    }

    @Override
    public EmployeeGroup createDepartment(String name) {
        return new ControlledDepartment(name);
    }

    @Override
    public EmployeeGroup createDepartment(String name, Employee[] e) {
        return new ControlledDepartment(name, e);
    }

    @Override
    public EmployeeGroup createProject() {
        return new ControlledProject("");
    }

    @Override
    public EmployeeGroup createProject(String name) {
        return new ControlledProject(name);
    }

    @Override
    public EmployeeGroup createProject(String name, Employee[] e) {
        return new ControlledProject(name, e);
    }

    @Override
    public GroupsManager createDepartemntManager() {
        ControlledDepartmentManager cdm = new ControlledDepartmentManager("");
        cdm.setSource(new GroupsManagerSerializedFileSource<>(path));
        return cdm;
    }

    @Override
    public GroupsManager createDepartemntManager(String title) {
        ControlledDepartmentManager cdm = new ControlledDepartmentManager(title);
        cdm.setSource(new GroupsManagerSerializedFileSource<>(path));
        return cdm;
    }

    @Override
    public GroupsManager createDepartemntManager(String title, EmployeeGroup[] deps) {
        ControlledDepartmentManager cdm = new ControlledDepartmentManager(title, deps);
        cdm.setSource(new GroupsManagerSerializedFileSource<>(path));
        return cdm;
    }

    @Override
    public GroupsManager createProjectManager() {
        ControlledProjectManager cdm = new ControlledProjectManager("");
        cdm.setSource(new GroupsManagerSerializedFileSource<>(path));
        return cdm;
    }

    @Override
    public GroupsManager createProjectManager(String title) {
        ControlledProjectManager cdm = new ControlledProjectManager(title);
        cdm.setSource(new GroupsManagerSerializedFileSource<>(path));
        return cdm;
    }

    @Override
    public GroupsManager createProjectManager(String title, EmployeeGroup[] emps) {
        ControlledProjectManager cdm = new ControlledProjectManager(title, emps);
        cdm.setSource(new GroupsManagerSerializedFileSource<>(path));
        return cdm;
    }
}
