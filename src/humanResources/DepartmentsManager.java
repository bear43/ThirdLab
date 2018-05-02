package humanResources;

import sun.awt.image.ImageWatched;
import util.LinkedList;
import util.Sizeable;
import util.Util;

import java.util.Date;
import java.util.Iterator;

import static util.Util.*;

public class DepartmentsManager implements GroupsManager
{
    private String name;//Название организации

    private LinkedList<EmployeeGroup> departments;//Массив с отделами

    public DepartmentsManager(String title, EmployeeGroup[] deps)//На вход может податься массив с null
    {
        if(deps == null) throw new NullPointerException();
        else
        {
            System.arraycopy(deps, 0, deps, 0, shiftNulls(deps));
            departments = new LinkedList<>(deps);//Создаем новый массив
        }
        name = title;
    }

    public DepartmentsManager(String title)
    {
        this(title, new EmployeeGroup[0]);
    }

    public void add(EmployeeGroup group) throws AlreadyAddedException//Добавляет отдел
    {
        if(group == null) throw new NullPointerException();
        if(departments.contains(group)) throw new AlreadyAddedException();
        departments.add(group);
    }

    public boolean remove(String title)//Удаляет отдел по названию
    {
        Iterator<EmployeeGroup> Iter = departments.iterator();
        while(Iter.hasNext())
            if(Iter.next().getName().equals(title))
            {
                Iter.remove();
                return true;
            }
        return false;
    }

    public int remove(EmployeeGroup group)
    {
        int counter = 0;
        Iterator<EmployeeGroup> Iter = departments.iterator();
        while(Iter.hasNext())
            if(Iter.next().equals(group))
            {
                Iter.remove();
                counter++;
            }
        return counter;
    }

    @Override
    public int getPartTimeEmployeeQuantity()
    {
        int counter = 0;
        for(EmployeeGroup group : departments)
            counter += Util.getPartTimeEmployeeCount(group.getEmployees(), group.employeeQuantity());
        return counter;
    }

    @Override
    public int getStaffEmployeeQuantity()
    {
        int counter = 0;
        for(EmployeeGroup group : departments)
            counter += Util.getStaffEmployeeCount(group.getEmployees(), group.employeeQuantity());
        return counter;
    }

    @Override
    public int getTravellingEmployeeQuantity()
    {
        int counter = 0;
        for(EmployeeGroup group : departments)
            counter += Util.getTravellingEmployeeQuantity(group.getEmployees(), group.employeeQuantity());
        return counter;
    }

    @Override
    public Employee[] getTravellingEmployeeOnDate(Date beginDage, Date endDate)
     {
        LinkedList<Employee> travellingEmployees = new LinkedList<>();
        for(EmployeeGroup group : departments)
            travellingEmployees.add(Util.getTravellingEmployeeOnDate(group.getEmployees(), group.employeeQuantity(), beginDage, endDate));
        return travellingEmployees.toArray(Employee[].class);
    }

    public EmployeeGroup getEmployeeGroup(String name)//Получение отдела по названию
    {
        for(EmployeeGroup group : departments)
            if(group.getName().equals(name))
                return group;
        return null;//Не нашли, возвращаем null
    }

    public EmployeeGroup[] getEmployeeGroups()//Получение всех отделов
    {
        return departments.toArray(EmployeeGroup[].class);
    }

    public int employeesQuantity()//Количество людей в организации
    {
        int employeeCounter = 0;
        for(EmployeeGroup group : departments) employeeCounter += group.employeeQuantity();//Получаем количество людей в отделе и увеличиваем счетчик
        return employeeCounter;
    }

    public int employeesQuantity(JobTitlesEnum jobtitle)//Количество людей в организации по профессии
    {
        int peopleCounter = 0;
        for(EmployeeGroup group : departments)
            peopleCounter += group.getEmployeesQuantityByJob(jobtitle);
        return peopleCounter;
    }

    public Employee mostValuableEmployee()//Возвращает лучшего сотрудника организации
    {
        Employee bestEmployee = null;
        Employee currentEmployee;
        for(EmployeeGroup group : departments)
        {
            currentEmployee = group.mostValuableEmployee();
            if(bestEmployee == null || bestEmployee.getSalary() < currentEmployee.getSalary())
                bestEmployee = currentEmployee;
        }
        return bestEmployee;
    }

    public EmployeeGroup getEmployeesGroup(String firstname, String secondname)//Возвращает отдел, в котором работает человек
    {
        for(EmployeeGroup group : departments)
            if(group.getEmployee(firstname, secondname) != null) return group;//Нашли человека, возвращаем отдел
        return null;//Ничего не нашли
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof DepartmentsManager)
                && (this.name.equals(((DepartmentsManager)obj).name)
                && (this.departments.equals(((DepartmentsManager) obj).departments)));
    }

    @Override
    public int hashCode()
    {
        return name.hashCode() ^ departments.hashCode();
    }


    @Override
    public int groupsQuantity()
    {
        return departments.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName()
    {
        return String.format("%s", this.name);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(EmployeeGroup group : departments)
            sb.append(group.toString()).append("\n");
        return sb.toString();
    }
}
