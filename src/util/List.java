package util;

import java.io.Serializable;
import java.util.Iterator;

public interface List<T> extends Iterable<T>, Serializable
{
    int DEFAULT_CAPACITY = 8;
    int size();
    void add(T obj);
    boolean remove(int index);
    boolean remove(T obj);
    T at(int index);
    T pop_back();
    boolean contains(T obj);
    int indexOf(T obj);
    T[] toArray(Class<T[]> type);
    default int removeAll(T obj)
    {
        Iterator<T> iter = iterator();
        int counter = 0;
        while(iter.hasNext())
        {
            if(iter.next().equals(obj))
            {
                iter.remove();
                counter++;
            }
        }
        return counter;
    }
}
