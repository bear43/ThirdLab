package humanResources;

public class PartTimeEmployee extends Employee
{
    @Override
    public int getBonus() {
        return 0;
    }

    @Override
    public void setBonus(int bonus)
    {
    }

    public PartTimeEmployee(String firstName, String lastName, JobTitlesEnum jobTitle, Integer salary)
    {
        super(firstName, lastName, jobTitle, salary);
    }

    public PartTimeEmployee(String firstName, String lastName)
    {
        super(firstName, lastName, JobTitlesEnum.NONE,  DEFAULT_SALARY);
    }

    @Override
    public String toString()
    {
        //todo String.format independetly StringBuilder DONE
        return String.format("%s %s, %s(Внешний совместитель), %dр.", lastName, firstName, jobTitle, salary);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof PartTimeEmployee)
        {
            Employee employee = (Employee) obj;
            return super.equals(employee);
        }
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
