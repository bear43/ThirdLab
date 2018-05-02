package util;

import humanResources.Employee;
import humanResources.PartTimeEmployee;
import humanResources.StaffEmployee;
import io.BinaryView;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;

import static java.lang.reflect.Array.newInstance;

public class Util
{
    public static Integer getElemCount(Object[] obj)//Elements size without nulls
    {
        Integer counter = 0;
        for(Object o : obj)
            if(o != null)
                counter++;
        return counter;
    }
    public static void fullReverse(Object[] obj, int realCount)
    {
        if(obj.length == 1) return;
        Object tmp;
        for(int i = realCount-1; i != 1; i--)
        {
            tmp = obj[i];
            obj[i] = obj[realCount-i-1];
            obj[realCount-i-1] = tmp;
        }
    }
    /* Возвращает реальный размер массива, без учета null */
    public static int shiftNulls(Object[] obj)
    {
        if(obj == null) return 0;
        Integer counter = 0;//Счетчик, который показывает реальный размер
        boolean firstTime = true;//Это первый раз, когда мы нашли null? Если да, то необходимо зануллить последний элемент, дабы не было дублирования ссылки на один и тот же объект. Верно ли это?
        for(int i = 0; i < obj.length-1; i++)
        {
            if (obj[i] == null) //Нашли null
            {
                if (obj[i+1] == null) continue;//Если следующий элемент null, то пропускаем текущий и переходим к следующему
                System.arraycopy(obj, i + 1, obj, i, obj.length - i - 1);//Копируем интересующую нас часть
                if(firstTime)
                {
                    obj[obj.length-1] = null;//Занулили последний элемент, он и так может быть зануленным, ну да и черт с ним
                    firstTime = false;//Больше не зануляем последний элемент при нахождении null'a
                }
                i--;//Уменьшаем счетчик, так как необходимо ещё раз проверить новые данные
                continue;//Проверяем дальше, при этом не увеличивая счетчик ненулевых элементов
            }
            counter++;//Этот элемент не null, увеличиваем счетчик
        }
        return counter;//Возвращаем реальное количество элементов
    }
    public static <T> T[] expand(T[] obj, Class<? extends T[]> type)
    {
        T[] New = (T[]) newInstance(type.getComponentType(), 2 * obj.length);
        System.arraycopy(obj, 0, New, 0, obj.length);
        return New;
    }
    /* Добавляет элемент в массив */
    public static <T> T[] add(T[] array, T element, Class<? extends T[]> type)
    {
        T[] united = (T[]) Array.newInstance(type.getComponentType(), array.length+1);
        System.arraycopy(array, 0, united, 0, array.length);
        united[united.length-1] = element;
        return united;
    }

    static <T> void getUnique(List<T> toUnique, List<T> out)
    {
        if(toUnique == null || out == null) throw new NullPointerException();
        for(T element : toUnique)
            if(out.size() != 0)
            {
                if (!out.contains(element))
                    out.add(element);
            }
            else
                out.add(element);
    }
    public static boolean dateInRange(Date toCheck, Date begin, Date end)
    {
        return toCheck.getTime() >= begin.getTime()
                && toCheck.getTime() <= end.getTime();
    }

    public static boolean dateInRange(Calendar toCheck, Calendar begin, Calendar end)
    {
        return toCheck.after(begin)
                && toCheck.before(end);
    }

    public static long timeToDays(long time)
    {
        return (long)Math.ceil((double)time/(double)86400000);
    }

    public static long daysToTime(long days)
    {
        return days*86400000;
    }

    public static int getStaffEmployeeCount(Iterable<Employee> employees)
    {
        int counter = 0;
        for(Employee e : employees)
            if(e instanceof StaffEmployee) counter++;
        return counter;
    }

    public static int getPartTimeEmployeeCount(Iterable<Employee> employees)
    {
        int counter = 0;
        for(Employee e : employees)
            if(e instanceof PartTimeEmployee) counter++;
        return counter;
    }

    public static int getStaffEmployeeCount(Employee[] employees, int size)
    {
        int counter = 0;
        for(int i = 0; i < size; i++)
            if(employees[i] instanceof StaffEmployee) counter++;
        return counter;
    }

    public static int getPartTimeEmployeeCount(Employee[] employees, int size)
    {
        int counter = 0;
        for(int i = 0; i < size; i++)
            if(employees[i] instanceof PartTimeEmployee) counter++;
        return counter;
    }

    public static int getTravellingEmployeeQuantity(List<Employee> employees)
    {
        int counter = 0;
        for(Employee e : employees)
            if(e instanceof StaffEmployee && ((StaffEmployee) e).isTravelling())
                counter++;
        return counter;
    }

    public static int getTravellingEmployeeQuantity(Employee[] employees, int size)
    {
        int counter = 0;
        for(int i = 0; i < size; i++)
            if(employees[i] instanceof StaffEmployee && ((StaffEmployee)(employees[i])).isTravelling())
                counter++;
        return counter;
    }

    public static Employee[] getTravellingEmployeeOnDate(LinkedList<Employee> employees, Date beginDate, Date endDate)
    {
        util.LinkedList<Employee> travellingEmployee = new util.LinkedList<Employee>();
        for(Employee e : employees)
            if(e instanceof StaffEmployee && ((StaffEmployee) e).wasTravellingOnDate(beginDate, endDate))
                travellingEmployee.add(e);
        return travellingEmployee.toArray(Employee[].class);
    }

    public static Employee[] getTravellingEmployeeOnDate(Employee[] employees, int size, Date beginDate, Date endDate)
    {
        LinkedList<Employee> list = new LinkedList<Employee>(employees, size);
        return getTravellingEmployeeOnDate(list, beginDate, endDate);
    }

    public static String readUTFLine(BufferedReader br) throws IOException
    {
        return br.readLine();
    }

    public static String readUTFLine(String filename) throws IOException
    {
        return readUTFLine(new BufferedReader(new FileReader(filename)));
    }

    public static String readUTFFile(String filename) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String buffer = br.readLine();
        while(buffer != null)
        {
            sb.append(buffer).append("\n");
            buffer = br.readLine();
        }
        return sb.toString();
    }

    public static void writeUTFFile(String filename, String data) throws IOException
    {
        File f = new File(filename);
        f.createNewFile();
        PrintWriter pw = new PrintWriter(new FileWriter(filename));
        pw.print(data);
        pw.flush();
        pw.close();
    }

    public static void writeBinaryFile(String filename, byte[] bytes) throws IOException
    {
        File f = new File(filename);
        f.createNewFile();
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename));
        dos.write(bytes);
        dos.flush();
        dos.close();
    }
    public static byte[] readBinaryFile(String filename) throws IOException
    {
        DataInputStream dis = new DataInputStream(new FileInputStream(filename));
        byte[] readedBytes = new byte[dis.available()];
        int index = 0;
        while(dis.available() > 0)
        {
            readedBytes[index] = dis.readByte();
            index++;
        }
        dis.close();
        return readedBytes;
    }

    public static byte[] appendToByteArray(byte[] first, byte[] second)
    {
        byte[] unitedArray = new byte[first.length + second.length];
        System.arraycopy(first, 0, unitedArray, 0, first.length);
        System.arraycopy(second, 0, unitedArray, first.length, second.length);
        return unitedArray;
    }

    public static byte[] appendByteToByteArray(byte[] array, byte b)
    {
        byte[] newArray = new byte[array.length+1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = b;
        return newArray;
    }

    public static byte[] appendIntToByteArray(byte[] array, int i)
    {
        byte[] newArray = new byte[array.length+BinaryView.AMOUNT_OF_BYTES_TO_INT_TYPE];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = (byte)(i & 0xFF000000);//hi part
        newArray[array.length+1] = (byte)(i&0x00FF0000);//hi2 part
        newArray[array.length+2] = (byte)(i&0x0000FF00);//low2 part
        newArray[array.length+3] = (byte)(i&0x000000FF);//low part
        return newArray;
    }

    public static byte[] appendBoolToByteArray(byte[] array, boolean b)
    {
        return appendIntToByteArray(array, b ? 1 : 0);
    }
}
