package humanResources;

public class PartTimeEmployee extends Employee
{
    @Override
    public Integer getBonus() {
        return 0;
    }

    @Override
    public void setBonus(Integer bonus) {

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
        StringBuilder sb = getString();
        //todo
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj) && obj instanceof PartTimeEmployee;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
