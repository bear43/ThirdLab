package util;

import java.lang.reflect.Array;

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
        return counter+1;//Возвращаем реальное количество элементов
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
}
