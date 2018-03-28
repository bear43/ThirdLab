package humanResources;

import util.*;

import java.util.Date;
import java.util.Iterator;

import static util.Util.*;

public class Department implements EmployeeGroup
{
    private String name;


    private LinkedList<Employee> employees;

    public Department(String name)
    {
        //this(Name, DEFAULT_CAPACITY);
        this(name, null);
    }

    public Department(String name, Employee[] e)
    {
        this.name = name;
        //Employee[] employees = new Employee[e.size];
        //System.arraycopy(e, 0, employees, 0, getElemCount(e));
        employees = new LinkedList<Employee>(e);
    }

    public LinkedList<Employee> getEmployeeList()
    {
        return employees;
    }


    public void add(Employee employee) throws AlreadyAddedException
    {
        if(employee == null) throw new NullPointerException();
        if(employees.contains(employee)) throw new AlreadyAddedException();
        employees.add(employee);
    }

    public boolean remove(Employee employee)
    {
        Iterator<Employee> Iter = employees.iterator();
        while(Iter.hasNext())
        {
            if(Iter.next().compareByName(employee))
            {
                Iter.remove();
                return true;
            }
        }
        return false;
    }

    public boolean remove(String firstName, String lastName)
    {
        Employee current;
        Iterator<Employee> Iter = employees.iterator();
        while(Iter.hasNext())
        {
            current = Iter.next();
            if(current.firstName.equals(firstName)
                    && current.lastName.equals(lastName))
            {
                Iter.remove();
                return true;
            }
        }
        return false;
    }

    public int removeAll(JobTitlesEnum jobTitle)
    {
        Iterator<Employee> Iter = employees.iterator();
        int removedCounter = 0;
        while(Iter.hasNext())
            if(Iter.next().jobTitle == jobTitle)
            {
                Iter.remove();
                removedCounter++;
            }
        return removedCounter;
    }

    public Employee[] getEmployees()
    {
        return employees.toArray(Employee[].class);
    }

    public int getEmployeesQuantityByJob(String jobTitle)
    {
        int counter = 0;
        for(Employee e : employees.toArray(Employee[].class))
            if(e.getJobTitle().toString().equals(jobTitle)) counter++;
        return counter;
    }

    public int getEmployeesQuantityByJob(JobTitlesEnum jobTitle)
    {
        int counter = 0;
        for(Employee e : employees.toArray(Employee[].class))
            if(e.getJobTitle().equals(jobTitle)) counter++;
        return counter;
    }

    @Override
    public int getPartTimeEmployeeQuantity()
    {
        return getPartTimeEmployeeCount(employees);
    }

    @Override
    public int getStaffEmployeeQuantity()
    {
        return getStaffEmployeeCount(employees);
    }

    @Override
    public int getTravellingEmployeeQuantity()
    {
        return Util.getTravellingEmployeeQuantity(employees);
    }

    @Override
    public Employee[] getTravellingEmployeeOnDate(Date beginDate, Date endDate)
    {
        return Util.getTravellingEmployeeOnDate(employees, beginDate, endDate);
    }

    public Employee[] getEmployees(String jobTitle)
    {
        LinkedList<Employee> sorted = new LinkedList<Employee>();
        Employee current;
        for (int i = 0; i < employees.size(); i++)
        {
            current = employees.at(i);
            if (current.getJobTitle().toString().equals(jobTitle))
                sorted.add(current);
        }
        return sorted.toArray(Employee[].class);
    }

    public Employee[] getEmployees(JobTitlesEnum jobTitle)
    {
        LinkedList<Employee> sorted = new LinkedList<Employee>();
        Employee current;
        for (int i = 0; i < employees.size(); i++)
        {
            current = employees.at(i);
            if (current.getJobTitle().equals(jobTitle))
                sorted.add(current);
        }
        return sorted.toArray(Employee[].class);
    }

    public Employee getEmployee(String firstName, String lastName)
    {
        Employee current;
        for(int i = 0; i < employees.size(); i++)
        {
            current = employees.at(i);
            if (current.firstName.equals(firstName) && current.lastName.equals(lastName))
                return current;
        }
        return null;
    }

    private static Employee[] merge(Employee[] a, Employee[] b)
    {
        Employee[] ret = new Employee[a.length + b.length];//Сливаем размер массива, на выходе 2*size
        int a_pointer = 0;//Указатель на текущий элемент A
        int b_pointer = 0;//Указатель на текущий элемент B
        int ret_pointer = 0;//Указатель на текущий элемент Ret
        while(true)
        {
            if(a_pointer >= a.length && b_pointer >= b.length)
                break;
            if(a_pointer >= a.length)
            {
                ret[ret_pointer] = b[b_pointer];
                b_pointer++;
                ret_pointer++;
                continue;
            }
            if(b_pointer >= b.length)
            {
                ret[ret_pointer] = a[a_pointer];
                a_pointer++;
                ret_pointer++;
                continue;
            }
            if(a[a_pointer].getSalary() < b[b_pointer].getSalary())
            {
                ret[ret_pointer] = a[a_pointer];
                a_pointer++;
            }
            else
            {
                ret[ret_pointer] = b[b_pointer];
                b_pointer++;
            }
            ret_pointer++;
        }
        return ret;
    }

    static Employee[] mergeSort(Employee[] es, int low, int high)
    {
        if(es.length == 1) return es;
        int t = low + (high+low)/2;
        //mergeSort(es, t);//первая часть от 0 до половины
        Employee mod = null;
        if((high-low) % 2 == 1)
        {
            mod = es[high-low-1];//Сейвим лишний нечетный элемент
            es[high-low-1] = null;//Убираем его
            //es = absorbeNulls(es);//Выравниваем массив
        }
        Employee[] Newa = new Employee[t];
        Employee[] Newb = new Employee[t];
        System.arraycopy(es, 0, Newa, 0, t);
        System.arraycopy(es, t, Newb, 0, t);
        if(mod == null)
        {
            return merge(mergeSort(Newa, 0, t), mergeSort(Newb, 0, t));
        }
        else { es[high-low-1] = mod; return merge(merge(mergeSort(Newa, 0, t), mergeSort(Newb, 0, t)), new Employee[] {mod}); }
    }


    public Employee[] sortedEmployees()
    {
        Employee[] ret = mergeSort(employees.toArray(Employee[].class), 0, employees.size());
        fullReverse(ret, ret.length);
        return ret;
    }

    public Employee mostValuableEmployee()//Возвращает сотрудника с наибольшей ЗП
    {
        int max = 0;
        Employee employee = null;
        for(Employee e : employees)
        {
            if(e.getSalary() > max)
            {
                max = e.getSalary();
                employee = e;
            }
        }
        return employee;//Возвращаем сотрудника с макс. ЗП
    }

    public boolean hasEmployee(String firstName, String lastName)
    {
        for(Employee e : employees)
        {
            if(e.getFirstName().equals(firstName) && e.getLastName().equals(lastName))
                return true;//Нашли совпадение
        }
        return false;//Нет совпадений
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /* Метод возвращает массив должностей сотрудников, при этом без повторов */
    public JobTitlesEnum[] jobTitles()
    {
        LinkedList<JobTitlesEnum> jobTitles = new LinkedList<JobTitlesEnum>();
        for(Employee current : employees)
            if(!jobTitles.contains(current.jobTitle))
                jobTitles.add(current.jobTitle);
        return jobTitles.toArray(JobTitlesEnum[].class);
    }

    /* Метод возвращает массив сотрудников, которые хотя бы раз были в командировке */
    public Employee[] businessTravellers()
    {
        LinkedList<Employee> employees = new LinkedList<Employee>();
        BusinessTravel[] travels;
        for(Employee current : employees)
        {
            if (current instanceof StaffEmployee)
            {
                travels = ((StaffEmployee) (current)).getTravels();
                if(travels != null)
                    if (travels.length != 0)
                        employees.add(current);
            }
        }
        return employees.toArray(Employee[].class);
    }

    public int getSize()
    {
        return employees.size();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Department ").append(this.name).append(": ").append(employees.size()).append("\n");
        for(int i = 0; i < employees.size(); i++)
            sb.append(employees.at(i).toString()).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof  Department)
                && (this.name.equals(((Department) o).name))
                && (this.employees.equals(((Department) o).employees));
    }

    @Override
    public int hashCode()
    {
        int hash = name.hashCode();
        hash ^= employees.hashCode();
        return hash;
    }

    public int employeeQuantity()
    {
        return employees.size();
    }
}