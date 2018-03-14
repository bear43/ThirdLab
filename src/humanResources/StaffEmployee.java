package humanResources;

import util.DoubleLinkedList;

public class StaffEmployee extends Employee implements BusinessTraveller
{
    private int bonus;
    private DoubleLinkedList<BusinessTravel> travels;

    public StaffEmployee(String firstName, String lastName, JobTitlesEnum jobTitle, int salary, BusinessTravel[] travels, int bonus)
    {
        super(firstName, lastName, jobTitle, salary);
        this.travels = new DoubleLinkedList<BusinessTravel>(travels, BusinessTravel[].class);
        this.bonus = bonus;
    }

    public StaffEmployee(String firstName, String lastName, JobTitlesEnum jobTitle, int salary)
    {
        this(firstName, lastName, jobTitle, salary, null, 0);
    }

    public StaffEmployee(String firstName, String lastName)
    {
        this(firstName, lastName, JobTitlesEnum.NONE, 0);
    }

    @Override
    public void addTravel(BusinessTravel travel)
    {
        travels.push(travel);
    }

    @Override
    public BusinessTravel[] getTravels()
    {
        return travels.toArray(BusinessTravel[].class);
    }

    @Override
    public Integer getBonus()
    {
        return bonus;
    }

    @Override
    public void setBonus(Integer bonus)
    {
        this.bonus = bonus;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if(this.lastName != null && !this.lastName.isEmpty())
            sb.append(this.lastName).append(" ");
        if(this.firstName != null && !this.firstName.isEmpty())
            sb.append(this.firstName).append(", ");
        if(this.jobTitle != JobTitlesEnum.NONE)
            sb.append(this.jobTitle).append(", ");
        if((this.salary != null && this.salary != 0) || this.bonus != 0)
            sb.append(this.salary).append("р.,");
        if(this.bonus != 0)
            sb.append(this.bonus).append("р.");
        if(this.travels.length() != 0)
        {
            sb.append("\nКомандировки:");
            for (BusinessTravel travel : this.travels.toArray(BusinessTravel[].class))
                sb.append("\n").append(travel.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof StaffEmployee)
        {
            StaffEmployee employee = (StaffEmployee)obj;
            if(this.bonus != employee.bonus) return false;
            if(this.travels.length() != employee.travels.length()) return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = new Integer(bonus).hashCode();
        hash ^= travels.hashCode();
        return hash;
    }
}
