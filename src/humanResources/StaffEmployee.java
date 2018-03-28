package humanResources;

import util.DoubleLinkedList;

import java.util.Calendar;
import java.util.Date;
import static util.Util.dateInRange;
import static util.Util.timeToDays;

public class StaffEmployee extends Employee implements BusinessTraveller
{
    private int bonus;
    private DoubleLinkedList<BusinessTravel> travels;

    public StaffEmployee(String firstName, String lastName, JobTitlesEnum jobTitle, int salary, BusinessTravel[] travels, int bonus)
    {
        super(firstName, lastName, jobTitle, salary);
        if(travels == null) this.travels = new DoubleLinkedList<BusinessTravel>();
        else this.travels = new DoubleLinkedList<BusinessTravel>(travels);
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
    public void addTravel(BusinessTravel travel) throws IllegalDatesException
    {
        for(BusinessTravel t : travels)
            if(dateInRange(t.getBeginDate(), travel.getBeginDate(), travel.getEndDate()) ||
                    dateInRange(t.getEndDate(), travel.getBeginDate(), travel.getEndDate()) ||
                    dateInRange(travel.getBeginDate(), t.getBeginDate(), t.getEndDate()) ||
                    dateInRange(travel.getEndDate(), t.getBeginDate(), t.getEndDate()))
                throw new IllegalDatesException();
        travels.add(travel);
    }

    @Override
    public BusinessTravel[] getTravels()
    {
        return travels.toArray(BusinessTravel[].class);
    }

    @Override
    public boolean isTravelling()
    {
        Calendar currentTime = Calendar.getInstance();
        for(BusinessTravel travel : travels)
            if(dateInRange(currentTime, travel.getBeginDate(), travel.getEndDate()))
                return true;
        return false;
    }

    @Override
    public int travellsCountOnDate(Date beginDate, Date endDate)
    {
        int counter = 0;
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        boolean beginIn;
        boolean endIn;
        for(BusinessTravel travel : travels)
        {
            beginIn = dateInRange(travel.getBeginDate(), begin, end);
            endIn = dateInRange(travel.getEndDate(), begin, end);
            if(beginIn && endIn)
                counter += timeToDays(travel.getEndDate().getTime().getTime() - travel.getBeginDate().getTime().getTime());
            else if(beginIn)
                counter += timeToDays(endDate.getTime() - travel.getBeginDate().getTime().getTime());
            else if(endIn)
                counter += timeToDays(travel.getEndDate().getTime().getTime()-beginDate.getTime());
            else if(beginDate.getTime() < travel.getBeginDate().getTime().getTime() && endDate.getTime() < travel.getEndDate().getTime().getTime())
                counter += timeToDays(endDate.getTime()-beginDate.getTime());
        }
        return counter;
    }

    public boolean wasTravellingOnDate(Date beginDate, Date endDate)
    {
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        boolean beginIn;
        boolean endIn;
        for(BusinessTravel travel : travels)
        {
            beginIn = dateInRange(travel.getBeginDate(), begin, end);
            endIn = dateInRange(travel.getEndDate(), begin, end);
            if((beginIn || endIn) ||
                    (beginDate.getTime() > travel.getBeginDate().getTime().getTime()
                            && endDate.getTime() < travel.getEndDate().getTime().getTime()))
                return true;
        }
        return false;
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
        if(bonus != 0)
            sb.append(", ").append(bonus).append("р.");
        sb.append(travels.toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof StaffEmployee) &&
                (this.bonus == ((StaffEmployee)obj).bonus) &&
                (this.travels.equals(((StaffEmployee)obj).travels));
    }

    @Override
    public int hashCode()
    {
        int hash = bonus;
        hash ^= travels.hashCode();
        return hash;
    }
}
