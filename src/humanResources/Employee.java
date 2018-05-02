package humanResources;

import io.BinaryView;
import io.FileSource;
import io.Source;
import io.Textable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static util.Util.readUTFFile;

public abstract class Employee implements Comparable<Employee>, Textable<Employee>, BinaryView
{
    private static final String defaultExtension = "emp";
    String firstName;
    String lastName;
    JobTitlesEnum jobTitle;
    int salary;
    static final int DEFAULT_SALARY = 0;

    protected Employee(String firstName, String lastName, JobTitlesEnum jobTitle, Integer salary)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        if(salary >= 0) this.salary = salary;
        else throw new IllegalArgumentException("Salary cannot be negative");
    }

    protected Employee(String firstName, String lastName)
    {
        this(firstName, lastName, JobTitlesEnum.NONE,  DEFAULT_SALARY);
    }

    String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    JobTitlesEnum getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitlesEnum jobTitle) {
        this.jobTitle = jobTitle;
    }

    int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


    boolean compareByName(Employee e)
    {
        return this.firstName.equals(e.firstName) &&
                this.lastName.equals(e.lastName);
    }

    boolean compareByName(String firstName, String secondName)
    {
        return this.firstName.equals(firstName) &&
                this.lastName.equals(secondName);
    }

    public abstract int getBonus();

    public abstract void setBonus(int bonus);

    protected StringBuilder getString()
    {
        StringBuilder sb = new StringBuilder();
        if(this.lastName != null && !this.lastName.isEmpty())
            sb.append(this.lastName).append(" ");
        if(this.firstName != null && !this.firstName.isEmpty())
            sb.append(this.firstName).append(", ");
        if(this.jobTitle != null && this.jobTitle != JobTitlesEnum.NONE)
            sb.append(this.jobTitle).append(", ");
        if(this.salary != 0)
            sb.append(this.salary).append("р.");
        return sb;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s, %s, %dр.", lastName, firstName, jobTitle, salary);
    }


    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Employee)//Если на вход пришел экземпляр класса Employee
        {
            Employee employee = (Employee)obj;
            return  employee.firstName.equals(this.firstName) &&
                    employee.lastName.equals(this.lastName) &&
                    employee.jobTitle.equals(this.jobTitle) &&
                    employee.salary == this.salary;
        }
        else
            return false;
    }

    @Override
    public int hashCode()
    {

        int hash = this.firstName.hashCode();
        hash ^= this.lastName.hashCode();
        hash ^= this.jobTitle.hashCode();
        hash ^= this.salary;
        return hash;
    }

    @Override
    public int compareTo(Employee o)
    {
        return o instanceof StaffEmployee ? salary - o.salary + getBonus() - o.getBonus() : salary - o.salary ;
    }

    public String getFileName()
    {
        return String.format("%s_%s.%s", this.firstName, this.lastName, defaultExtension);
    }

    public static Employee resurectObject(String filename, FileSource source) throws IOException, ParseException
    {
        filename = source.getPath() + "\\" + filename;
        String className = Textable.getClassFromFile(filename);
        if(className.equals(PartTimeEmployee.class.getName()))
        {
            return new PartTimeEmployee(readUTFFile(filename));
        }
        else if(className.equals(StaffEmployee.class.getName()))
        {
            return new StaffEmployee(readUTFFile(filename));
        }
        return null;
    }

    @Override
    public String toText()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.firstName).append(defaultFieldsDelimiter);
        sb.append(this.lastName).append(defaultFieldsDelimiter);
        sb.append(this.jobTitle.ordinal()).append(defaultFieldsDelimiter);
        sb.append(this.salary);
        return sb.toString();
    }
}