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
        travels.add(travel);
    }

    @Override
    public BusinessTravel[] getTravels()
    {
        return travels.toArray(BusinessTravel[].class);
    }

    @Override
    public int getBonus()
    {
        return bonus;
    }

    @Override
    public void setBonus(int bonus)
    {
        this.bonus = bonus;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = getString();
        //todo DONE
        if(bonus != 0)
            sb.append(", ").append(bonus).append("р.");
        sb.append(travels.toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        //todo refactor this DONE
        return (obj instanceof StaffEmployee) && (this.bonus == ((StaffEmployee)obj).bonus) && (this.travels.equals(((StaffEmployee)obj).travels));
    }

    @Override
    public int hashCode()
    {
        //todo refactor this DONE
        int hash = bonus;
        hash ^= travels.hashCode();
        return hash;
    }
}
