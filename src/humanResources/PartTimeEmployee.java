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
        StringBuilder sb = new StringBuilder();
        if(this.lastName != null && !this.lastName.isEmpty())
            sb.append(this.lastName).append(" ");
        if(this.firstName != null && !this.firstName.isEmpty())
            sb.append(this.firstName).append(", ");
        if(this.jobTitle != null && this.jobTitle != JobTitlesEnum.NONE)
            sb.append(this.jobTitle).append(" (Внешний совместитель), ");
        if(this.salary != null && this.salary != 0)
            sb.append(this.salary).append("р.");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof PartTimeEmployee)
        {
            Employee e = (Employee) obj;
            return super.equals(obj);
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
