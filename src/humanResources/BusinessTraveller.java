package humanResources;

import java.util.Date;
import java.util.Set;

public interface BusinessTraveller extends Set<BusinessTravel>
{
    void addTravel(BusinessTravel travel) throws IllegalDatesException;
    BusinessTravel[] getTravels();
    boolean isTravelling();
    int travellsCountOnDate(Date beginDate, Date endDate);
    boolean wasTravellingOnDate(Date beginDate, Date endDate);//todo boolean DONE
}
