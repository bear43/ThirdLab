import humanResources.*;
import io.ControlledDepartment;
import io.ControlledDepartmentManager;
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
        ControlledDepartment dep = new ControlledDepartment("TestDep");
        dep.add(new StaffEmployee("Mor", "For", JobTitlesEnum.AGENT, 1200, new BusinessTravel[]{
                new BusinessTravel()
        }, 100));
        dep.add(new PartTimeEmployee("John", "Small", JobTitlesEnum.ADMINISTRATOR, 10000));
        ControlledDepartment fep = new ControlledDepartment("FineDep");
        fep.add(new PartTimeEmployee("Lel", "Pel", JobTitlesEnum.ENGINEER, 100));
        ControlledDepartmentManager cdm = new ControlledDepartmentManager("Org", new EmployeeGroup[]{
                dep, fep
        });
        ControlledDepartmentManager fdm = new ControlledDepartmentManager("Org");
        try {
            cdm.store();
            fdm.restore();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}