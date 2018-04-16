import humanResources.*;
import util.DoubleLinkedList;
import util.LinkedList;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import static util.Sizeable.*;
import static util.Util.daysToTime;
import static util.Util.timeToDays;

public class Main
{
    public static void main(String[] args)
    {
        java.time.LocalDate ld = LocalDate.of(2018, 3, 28);
        StaffEmployee employee = new StaffEmployee("Test", "Tester", JobTitlesEnum.ASSISTANT, 2000);
        try
        {
            employee.addTravel(new BusinessTravel("London", 1020, "Test",
                    Calendar.getInstance(), Calendar.getInstance()));
        }
        catch(IllegalDatesException ex)
        {
            ex.printStackTrace(System.out);
        }
        System.out.println(employee.travellsCountOnDate(Calendar.getInstance().getTime(), new Date(Calendar.getInstance().getTime().getTime() + daysToTime(90))));
    }
}