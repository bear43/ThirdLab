import humanResources.*;
import util.DoubleLinkedList;
import util.LinkedList;

import java.util.Iterator;

import static util.Sizeable.*;

public class Main
{
    public static void main(String[] args)
    {
        DepartmentsManager depManager = new DepartmentsManager("Apple Inc.");
        ProjectsManager projManager = new ProjectsManager();
        depManager.add(new Department("Security", new Employee[]{
                new StaffEmployee("John", "Watson", JobTitlesEnum.ANALYST, 1500, new BusinessTravel[]{
                        new BusinessTravel("London", 30, 5000, "Relax"),
                        new BusinessTravel("Moscow", 777, 100500, "Kill Putin"),
                        new BusinessTravel("Samara", 90, 0, "Pass Java")
                }, 100),
                new PartTimeEmployee("Vasya", "Pupkin", JobTitlesEnum.ASSISTANT, 150),
                new PartTimeEmployee("Bill", "Gates", JobTitlesEnum.NONE, 666)
        }));
        Project proj = new Project("iPhone", new Employee[]{
                new PartTimeEmployee("Danila", "Bagrov", JobTitlesEnum.DIRECTOR, 100000),
                new StaffEmployee("James", "Bond", JobTitlesEnum.ENGINEER, 15000, new BusinessTravel[]{
                        new BusinessTravel("Chicago", 15, 100000, "Target elimination")
                }, 2000)
        });
        depManager.add(proj);
        projManager.add(proj);
        System.out.println(depManager.employeesQuantity());
        System.out.println(depManager.mostValuableEmployee());
        System.out.println(depManager.remove("iPhone"));
        System.out.println(depManager.groupsQuantity());
        System.out.println();
    }
}