package humanResources;

import io.FileSource;
import io.Source;
import util.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import static util.Util.*;

public class Department implements EmployeeGroup
{
    private static final String defaultExtension = "dep";
    private String name;


    private LinkedList<Employee> employees;

    public Department(String name)
    {
        this(name, null);
    }

    public Department(String name, Employee[] e)
    {
        this.name = name;
        employees = new LinkedList<>(e);
    }

    public LinkedList<Employee> getEmployeeList()
    {
        return employees;
    }


    @Override
    public int size() {
        return employees.size();
    }

    public void add(Employee employee)
    {
        try {
            if (employee == null) return;
            if (employees.size() != 0 && employees.contains(employee)) throw new AlreadyAddedException();
            employees.add(employee);
        }
        catch(AlreadyAddedException ex)
        {
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public boolean remove(int index) {
        return employees.remove(index);
    }

    public boolean remove(Employee employee)
    {
        return employees.remove(employee);
    }

    @Override
    public Employee at(int index) {
        return employees.at(index);
    }

    @Override
    public Employee pop_back() {
        return employees.pop_back();
    }

    @Override
    public boolean contains(Employee obj) {
        return employees.contains(obj);
    }

    @Override
    public int indexOf(Employee obj) {
        return employees.indexOf(obj);
    }

    @Override
    public Employee[] toArray(Class<Employee[]> type) {
        return employees.toArray(Employee[].class);
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

    @Override
    public int removeAll(Employee obj)
    {
        int counter = 0;
        Iterator<Employee> iter = employees.iterator();
        while(iter.hasNext())
        {
            if(iter.next().equals(obj))
            {
                iter.remove();
                counter++;
            }
        }
        return counter;
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
        LinkedList<Employee> sorted = new LinkedList<>();
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
        LinkedList<Employee> sorted = new LinkedList<>();
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
        while(a_pointer <= a.length && b_pointer <= b.length)
        {
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
        //Employee[] ret = mergeSort(employees.toArray(Employee[].class), 0, employees.size());
        //fullReverse(ret, ret.length);
        Employee[] ret = employees.toArray(Employee[].class);
        Arrays.sort(ret);
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
        LinkedList<JobTitlesEnum> jobTitles = new LinkedList<>();
        for(Employee current : employees)
            if(!jobTitles.contains(current.jobTitle))
                jobTitles.add(current.jobTitle);
        return jobTitles.toArray(JobTitlesEnum[].class);
    }

    /* Метод возвращает массив сотрудников, которые хотя бы раз были в командировке */
    public Employee[] businessTravellers()
    {
        LinkedList<Employee> employees = new LinkedList<>();
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
            sb.append(employees.at(i).toString()).append(defaultFieldsDelimiter);
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

    @Override
    public Iterator<Employee> iterator() {
        return employees.iterator();
    }

    @Override
    public String toText() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(defaultFieldsDelimiter).//Имя класса
                append(this.getName());//Имя объекта
        for(Employee e : this.getEmployeeList())
            sb.append(defaultFieldsDelimiter).append(e.getFileName()).append(defaultFieldsDelimiter);
        return sb.toString();
    }

    public String toText(Source source) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(defaultFieldsDelimiter).//Имя класса
                append(this.getName());//Имя объекта
        for(Employee e : this.getEmployeeList())
        {
            sb.append(defaultFieldsDelimiter).
            append(e.getFileName());
        }
        return sb.toString();
    }

    @Override
    public EmployeeGroup fromText(String text) throws IOException, ParseException {
        StringTokenizer st = new StringTokenizer(text, defaultFieldsDelimiter);
        if(st.nextToken().equals(this.getClass().getName()))
        {
            this.setName(st.nextToken());
            while(st.hasMoreTokens())
                this.add(Employee.resurrectObject(st.nextToken(), null));
        }
        return this;
    }

    @Override
    public EmployeeGroup fromText(String text, FileSource source) throws IOException, ParseException {
        return null;
    }

    @Override
    public String getFileName()
    {
        return this.name;
    }

    @Override
    public byte[] toBinary(Source source) throws IOException {
        return new byte[0];
    }

    @Override
    public void fromBinary(byte[] rawBytes, FileSource source) throws IOException, ParseException
    {

    }

    @Override
    public int getBytesAmount() {
        return 0;
    }
}