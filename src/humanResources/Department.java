package humanResources;

import util.*;

import static util.Util.*;

public class Department implements EmployeeGroup
{
    private String name;

    //private Employee[] employees;

    private LinkedList<Employee> employees;

    //static final int DEFAULT_CAPACITY = 8;

    /*public Department(String Name, int capacity)
    {
        this.name = Name;
        //employees = new Employee[capacity];
        //size = 0;
    }*/

    public Department(String name)
    {
        //this(Name, DEFAULT_CAPACITY);
        this(name, null);
    }

    public Department(String name, Employee[] e)
    {
        this.name = name;
        //Employee[] employees = new Employee[e.length];
        //System.arraycopy(e, 0, employees, 0, getElemCount(e));
        employees = new LinkedList<Employee>(e, Employee[].class);
    }


    /* На вход массив сотрудников es, реальное количество работников, исключающее null */
    /*private Employee[] absorbeNulls(Employee[] es, int size)
    {
        Employee[] ret;
        ret = new Employee[size];
        System.arraycopy(es, 0, ret, 0, size);
        return ret;
    }*/

    /*public static Employee[] add(Employee[] es, Employee e)
    {
        int totalCount = getElemCount(es);//Узнаем реальное положение дел
        if(es == null) return null;
        if(totalCount >= es.length) es = expand(es, Employee[].class);
        es[totalCount] = e;
        return es;
    }*/


    public void add(Employee employee)
    {
        if(employee == null) return;
        //if(size >= employees.length) employees = expand(employees, Employee[].class);
        //employees[size] = e;
        //incCount();
        employees.push(employee);
    }

    public boolean remove(Employee employee)
    {
        if(employee == null) return false;
        for (int i = 0; i < employees.length(); i++)
        {
            if(employees.pop_at(i).compareByName(employee))
            {
                //employees[i] = null;
                //System.arraycopy(employees, i+1, employees, i, size-i-1);
                //employees[size] = null;
                //size--;
                return employees.remove(employee);
                //return true;
            }
        }
        return false;
    }

    public boolean remove(String firstName, String lastName)
    {
        Employee current;
        for (int i = 0; i < employees.length(); i++)
        {
            current = employees.pop_at(i);
            if(current.getFirstName().equals(firstName) && current.getLastName().equals(lastName))
            {
                //employees[i] = null;
                //System.arraycopy(employees, i+1, employees, i, size-i-1);
                //employees[size] = null;
                //size--;
                return employees.remove(current);
                //return true;
            }
        }
        return false;
    }

    public int removeAll(JobTitlesEnum jobTitle)
    {
        int removedCounter = 0;
        Employee current;
        for(int i = 0; i < employees.length(); i++)
        {
            current = employees.pop_at(i);
            if(current.jobTitle.equals(jobTitle))
            {
                //System.arraycopy(employees, i+1, employees, i, size-1-i);
                //employees[size] = null;
                //size--;
                employees.remove(current);
                removedCounter++;
                //return true;
            }
        }
        return removedCounter;
    }

    public Employee[] getEmployees()
    {
        return employees.toArray(Employee[].class);
        //return absorbeNulls(employees, size);
    }

    public int getEmployeesQuantityByJob(String jobTitle)
    {
        int counter = 0;
        for(Employee e : employees.toArray(Employee[].class))
            if(e.getJobTitle().equals(jobTitle)) counter++;
        return counter;
    }

    public int getEmployeesQuantityByJob(JobTitlesEnum jobTitle)
    {
        int counter = 0;
        for(Employee e : employees.toArray(Employee[].class))
            if(e.getJobTitle().equals(jobTitle)) counter++;
        return counter;
    }

    public Employee[] getEmployees(String jobTitle)
    {
        //Employee[] sorted = new Employee[getEmployeesQuantityByJob(jobtitle)];
        LinkedList<Employee> sorted = new LinkedList<Employee>();
        Employee current;
        for (int i = 0; i < employees.length(); i++)
        {
            current = employees.pop_at(i);
            if (current.getJobTitle().equals(jobTitle))
                sorted.push(current);
        }
        return sorted.toArray(Employee[].class);
    }

    public Employee[] getEmployees(JobTitlesEnum jobTitle)
    {
        //Employee[] sorted = new Employee[getEmployeesQuantityByJob(jobTitle)];
        LinkedList<Employee> sorted = new LinkedList<Employee>();
        Employee current;
        for (int i = 0; i < employees.length(); i++)
        {
            current = employees.pop_at(i);
            if (current.getJobTitle().equals(jobTitle))
                sorted.push(current);
        }
        return sorted.toArray(Employee[].class);
    }

    public Employee getEmployee(String firstName, String lastName)
    {
        Employee current;
        for(int i = 0; i < employees.length(); i++)
        {
            current = employees.pop_at(i);
            if (current.firstName.equals(firstName) && current.lastName.equals(lastName))
                return current;
        }
        return null;
    }

    public static Employee[] merge(Employee[] a, Employee[] b)
    {
        Employee[] ret = new Employee[a.length + b.length];//Сливаем размер массива, на выходе 2*length
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

    public static Employee[] mergeSort(Employee[] es, int low, int high)
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
        Employee[] ret = mergeSort(employees.toArray(Employee[].class), 0, employees.length());
        fullReverse(ret, ret.length);
        return ret;
    }

    public Employee mostValuableEmployee()//Возвращает сотрудника с наибольшей ЗП
    {
        int max = 0;
        int index = -1;
        Employee current = null;
        Employee employee = null;
        for(int i = 0; i < employees.length(); i++)
        {
            current = employees.pop_at(i);
            if (current.getSalary() > max)
            {
                max = current.getSalary();//Нашли новую макс. ЗП
                employee = current;
            }
        }
        return employee;//Возвращаем сотрудника с макс. ЗП
    }

    public boolean hasEmployee(String firstName, String lastName)
    {
        Employee current;
        for(int i = 0; i < employees.length(); i++)//Перебираем всех сотрудников
        {
            current = employees.pop_at(i);
            if(current.getFirstName().equals(firstName) && current.getLastName().equals(lastName))
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
        for(Employee current : employees.toArray(Employee[].class))
            if(!jobTitles.find(current.jobTitle))
                jobTitles.push(current.jobTitle);
        return jobTitles.toArray(JobTitlesEnum[].class);
    }

    /* Метод возвращает массив сотрудников, которые хотя бы раз были в командировке */
    public Employee[] businessTravellers()
    {
        LinkedList<Employee> employees = new LinkedList<Employee>();
        Employee current;
        BusinessTravel[] travels;
        for(int i = 0; i < this.employees.length(); i++)
        {
            current = this.employees.pop_at(i);
            if (current instanceof StaffEmployee)
            {
                travels = ((StaffEmployee) (current)).getTravels();
                if(travels != null)
                    if (travels.length != 0)
                        employees.push(current);
            }
        }
        return employees.toArray(Employee[].class);
    }

    public int getSize()
    {
        return employees.length();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Department ").append(this.name).append(": ").append(employees.length()).append("\n");
        for(int i = 0; i < employees.length(); i++)
            sb.append(employees.pop_at(i).toString()).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Department)) return false;
        Department incomingDepartment = (Department)o;
        if(!this.name.equals(incomingDepartment.name)) return false;
        if(this.employees.length() != incomingDepartment.employees.length()) return false;
        for(Employee current : this.employees.toArray(Employee[].class))
            if(!incomingDepartment.employees.find(current)) return false;
        return true;
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
        return employees.length();
    }
}