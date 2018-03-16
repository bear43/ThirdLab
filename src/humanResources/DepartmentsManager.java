package humanResources;

import util.Sizeable;
import static util.Util.*;

public class DepartmentsManager extends Sizeable implements GroupsManager
{
    private String name;//Название организации

    private EmployeeGroup[] departments;//Массив с отделами

    static final int DEFAULT_CAPACITY = 8;//Размерность массива с отделами по умолчанию

    public DepartmentsManager(String title, EmployeeGroup[] deps)//На вход может податься массив с null
    {
        if(deps == null)
            departments = new EmployeeGroup[DEFAULT_CAPACITY];
        else
        {
            size = shiftNulls(deps);//Сдвигаем все нужное, а также узнаем реальный размер
            departments = new EmployeeGroup[size];//Создаем новый массив
            System.arraycopy(deps, 0, departments, 0, size);//Копируем
        }
        name = title;
    }

    public DepartmentsManager(String title)
    {
        this(title, null);
    }

    public void add(EmployeeGroup group)//Добавляет отдел
    {
        if(group == null) return;
        if(size >= departments.length) expand(departments, Department[].class);
        departments[size] = group;
        size++;
    }

    public boolean remove(String title)//Удаляет отдел по названию
    {
        for(int i = 0; i < size; i++)//Перечисляем все "существующие" отделы
        {
            if(departments[i].getName().equals(title))//Нашли номер элемента в массиве с таковым названием отдела
            {
                departments[i] = null;//Удаляем на него ссылку
                size--;
                shiftNulls(departments);//Сдвигаем то, что удалили
                return true;//Если предполагается, что отделы могут иметь одинаковые название, то это убираем
            }
        }
        return false;
    }

    public int remove(EmployeeGroup group)
    {
        int counter = 0;
        for(int i = 0; i < size; i++)
        {
            if(departments[i].equals(group))
            {
                departments[i] = null;
                size--;
                shiftNulls(departments);
                counter++;
            }
        }
        return counter;
    }

    public EmployeeGroup getEmployeeGroup(String name)//Получение отдела по названию
    {
        for(int i = 0; i < size; i++)//Перебираем все отделы
        {
            if(departments[i].getName().equals(name))//нашли нужный отдел
            {
                return departments[i];//возвращаем отдел
            }
        }
        return null;//Не нашли, возвращаем null
    }

    public EmployeeGroup[] getEmployeeGroups()//Получение всех отделов
    {
        return departments;
    }

    public int employeesQuantity()//Количество людей в организации
    {
        int employeeCounter = 0;
        for(int i = 0; i < size; i++) employeeCounter += departments[i].employeeQuantity();//Получаем количество людей в отделе и увеличиваем счетчик
        return employeeCounter;
    }

    public int employeesQuantity(JobTitlesEnum jobtitle)//Количество людей в организации по профессии
    {
        int peopleCounter = 0;
        for(int i = 0; i < size; i++)
            peopleCounter += departments[i].getEmployeesQuantityByJob(jobtitle);
        return peopleCounter;
    }

    public Employee mostValuableEmployee()//Возвращает лучшего сотрудника организации
    {
        Employee bestEmployee = departments[0].mostValuableEmployee();//Лучший сотрудник организации
        Employee currentEmployee = null;//Лучший сотрудник одного из отделов организации
        for(int i = 0; i < size; i++)
        {
            currentEmployee = departments[i].mostValuableEmployee();
            if(currentEmployee != null &&
                    currentEmployee.getSalary() > bestEmployee.getSalary())
                bestEmployee = currentEmployee;
        }
        return bestEmployee;
    }

    public EmployeeGroup getEmployeesGroup(String firstname, String secondname)//Возвращает отдел, в котором работает человек
    {
        for(int i = 0; i < size; i++)
            if(departments[i].getEmployee(firstname, secondname) != null) return departments[i];//Нашли человека, возвращаем отдел
        return null;//Ничего не нашли
    }

    private boolean isEqual(EmployeeGroup[] groups)
    {
        EmployeeGroup[] copiedGroups = new EmployeeGroup[groups.length];
        System.arraycopy(groups, 0, copiedGroups, 0, groups.length);
        int realSize = shiftNulls(copiedGroups);
        if(size != realSize) return false;
        else
            for (int i = 0; i < realSize; i++)
                if (!departments[i].equals(copiedGroups[i])) return false;
        return true;
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof DepartmentsManager)
                && (this.name.equals(((DepartmentsManager)obj).name)
                && (isEqual(((DepartmentsManager)obj).departments)));
    }

    @Override
    public int hashCode()
    {
        int depHash = departments[0].hashCode();
        for(int i = 1; i < size; i++)
            depHash ^= departments[i].hashCode();
        return name.hashCode() ^ depHash;
    }


    @Override
    public int groupsQuantity()
    {
        return size;
    }
}
