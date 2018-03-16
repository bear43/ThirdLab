package humanResources;

import util.*;

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


    public void add(Employee employee)
    {
        if(employee == null) return;
        //if(size >= employees.size) employees = expand(employees, Employee[].class);
        //employees[size] = e;
        //incCount();
        employees.add(employee);
    }

    public boolean remove(Employee employee)
    {
        if(employee == null) return false;
        for (int i = 0; i < employees.size(); i++)
        {
            if(employees.at(i).compareByName(employee))
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
        for (int i = 0; i < employees.size(); i++)
        {
            current = employees.at(i);
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
        for(int i = 0; i < employees.size(); i++)
        {
            current = employees.at(i);
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
        for (int i = 0; i < employees.size(); i++)
        {
            current = employees.at(i);
            if (current.getJobTitle().equals(jobTitle))
                sorted.add(current);
        }
        return sorted.toArray(Employee[].class);
    }

    public Employee[] getEmployees(JobTitlesEnum jobTitle)
    {
        //Employee[] sorted = new Employee[getEmployeesQuantityByJob(jobTitle)];
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

    public static Employee[] merge(Employee[] a, Employee[] b)
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
        Employee[] ret = mergeSort(employees.toArray(Employee[].class), 0, employees.size());
        fullReverse(ret, ret.length);
        return ret;
    }

    public Employee mostValuableEmployee()//Возвращает сотрудника с наибольшей ЗП
    {
        int max = 0;
        int index = -1;
        Employee current = null;
        Employee employee = null;
        for(int i = 0; i < employees.size(); i++)
        {
            current = employees.at(i);
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
        for(int i = 0; i < employees.size(); i++)//Перебираем всех сотрудников
        {
            current = employees.at(i);
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
            if(!jobTitles.contains(current.jobTitle))
                jobTitles.add(current.jobTitle);
        return jobTitles.toArray(JobTitlesEnum[].class);
    }

    /* Метод возвращает массив сотрудников, которые хотя бы раз были в командировке */
    public Employee[] businessTravellers()
    {
        LinkedList<Employee> employees = new LinkedList<Employee>();
        Employee current;
        BusinessTravel[] travels;
        for(int i = 0; i < this.employees.size(); i++)
        {
            current = this.employees.at(i);
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