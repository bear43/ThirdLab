package humanResources;


import java.util.Formatter;

public abstract class Employee
{
    protected String firstName;
    protected String lastName;
    protected JobTitlesEnum jobTitle;
    protected int salary;
    protected static final int DEFAULT_SALARY = 0;

    protected Employee(String firstName, String lastName, JobTitlesEnum jobTitle, Integer salary)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    protected Employee(String firstName, String lastName)
    {
        this(firstName, lastName, JobTitlesEnum.NONE,  DEFAULT_SALARY);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public JobTitlesEnum getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitlesEnum jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


    public boolean compareByName(Employee e)
    {
        if(this.firstName.equals(e.firstName) && this.lastName.equals(e.lastName))
            return true;
        return false;
    }

    public boolean compareByName(String firstName, String secondName)
    {
        if(this.firstName.equals(firstName) && this.lastName.equals(secondName))
            return true;
        return false;
    }

    public abstract Integer getBonus();

    public abstract void setBonus(Integer bonus);

    protected StringBuilder getString() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter();
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
        /* Формат
         * <secondName> <firstName>, <jobTitle>, <salary>р.
         * */
        return getString().toString();
    }

    //TODO сделать во всех остальных equals() так же
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Employee)//Если на вход пришел экземпляр класса Employee
        {
            Employee employee = (Employee)obj;
            return employee.firstName.equals(this.firstName) &&
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
}