package humanResources;

import java.util.Date;

public interface BusinessTraveller
{
    void addTravel(BusinessTravel travel) throws IllegalDatesException;
    BusinessTravel[] getTravels();
    boolean isTravelling();
    int travellsCountOnDate(Date beginDate, Date endDate);
}
