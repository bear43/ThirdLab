package util;

import java.lang.reflect.Array;
import static util.Util.*;

public class ArrayList<T> implements List<T>
{
    /* Реальное количество элементов */
    private int length;
    /* Массив, в котором хранятся ссылки на элементы */
    private Object[] array;

    public ArrayList(T[] array, int capacity)
    {
        capacity = 2*capacity;
        this.array = new Object[capacity];
        this.length = shiftNulls(array);
        System.arraycopy(array, 0, this.array, 0, this.length);
    }

    public ArrayList(T[] array)
    {
        this(array, array.length);
    }

    public ArrayList(int capacity)
    {
        this((T[])new Object[capacity], capacity);
    }

    public ArrayList()
    {
        this((T[])new Object[DEFAULT_CAPACITY], DEFAULT_CAPACITY);
    }
    @Override
    public int length() {
        return this.length;
    }

    @Override
    public void push(T obj)
    {
        if(obj == null) return;
        if(length >= array.length) array = expand(this.array, Object[].class);
        array[length] = (Object)obj;
        length++;
    }

    @Override
    public boolean remove(int index)
    {
        if(array == null || index >= length || index < 0) return false;
        System.arraycopy(array, index+1, array, index, length-1-index);
        array[length-1] = null;
        length--;
        return true;
    }

    @Override
    public boolean remove(T obj)
    {
        return remove(indexOf(obj));
    }

    @Override
    public T pop_at(int index) {
        if(index >= length || index < 0) return null;
        return (T)array[index];
    }

    @Override
    public T pop_back() {
        return (T)array[length-1];
    }

    @Override
    public boolean find(T obj) {
        if(indexOf(obj) != -1) return true;
        return false;
    }

    @Override
    public int indexOf(T obj) {
        for(int i = 0; i < length; i++)
            if(array[i].equals(obj)) return i;
        return -1;
    }

    @Override
    public T[] toArray(Class<T[]> type) {
        T[] array = (T[])Array.newInstance(type.getComponentType(), length);
        System.arraycopy(this.array, 0, array, 0, length);
        return array;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Arraylist of ").append(array.getClass().getCanonicalName()).append(".\n")
                .append("length: ").append(length).append("\n")
                .append("capacity: ").append(array.length).append("\n");
        return sb.toString();
    }
}
