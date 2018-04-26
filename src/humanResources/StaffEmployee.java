package humanResources;

import util.DoubleLinkedList;

import java.util.*;

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
            if((beginIn || endIn)||
                    beginDate.getTime() < travel.getBeginDate().getTime().getTime() && endDate.getTime() < travel.getEndDate().getTime().getTime())
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
    public int size()
    {
        return travels.size();
    }

    @Override
    public boolean isEmpty()
    {
        return travels.size() == 0;
    }

    @Override
    public boolean contains(Object o)
    {
        return (o instanceof BusinessTravel) && travels.contains((BusinessTravel)o);
    }

    @Override
    public Iterator<BusinessTravel> iterator() {
        return travels.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return travels.toArray(BusinessTravel[].class);
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return (T[])travels.toArray(BusinessTravel[].class);
    }

    @Override
    public boolean add(BusinessTravel businessTravel)
    {
        if(contains(businessTravel)) return false;
        else
        {
            int travelCountBeforeAdd = travels.size();
            travels.add(businessTravel);
            return travels.size() > travelCountBeforeAdd;
        }
    }

    @Override
    public boolean remove(Object o)
    {
        return (o instanceof BusinessTravel) && travels.remove((BusinessTravel)o);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        for(Object o : c)
            if(!contains(o)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends BusinessTravel> c)
    {
        for(BusinessTravel travel : c)
            if(!add(travel)) return false;
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        int savedCounter = 0;
        DoubleLinkedList<BusinessTravel> newTravels = new DoubleLinkedList<BusinessTravel>();
        for(Object travel : c)
            for(Object obj : travels)
                if(travel.equals(obj))
                {
                    newTravels.add((BusinessTravel)travel);
                    savedCounter++;
                }
        clear();
        for(BusinessTravel travel : newTravels)
            add(travel);
        return savedCounter > 0;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        int removedCounter = 0;
        Iterator<BusinessTravel> iter = travels.iterator();
        for (Object o : c)
        {
            while (iter.hasNext()) {
                if (iter.next().equals(o)) {
                    iter.remove();
                    removedCounter++;
                    break;
                }
            }
            iter = travels.iterator();
        }
            return removedCounter > 0;
    }

    @Override
    public void clear()
    {
        Iterator<BusinessTravel> iter = travels.iterator();
        while(iter.hasNext())
        {
            iter.next();
            iter.remove();
        }
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
