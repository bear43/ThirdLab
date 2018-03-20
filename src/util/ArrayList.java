package util;

import java.lang.reflect.Array;
import java.util.Iterator;

import static util.Util.*;

public class ArrayList<T> implements List<T>
{
    /* Реальное количество элементов */
    private int size;
    /* Массив, в котором хранятся ссылки на элементы */
    private Object[] array;

    public ArrayList(T[] array, int capacity)
    {
        capacity = 2*capacity;
        this.array = new Object[capacity];
        this.size = shiftNulls(array);
        System.arraycopy(array, 0, this.array, 0, this.size);
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
    public int size() {
        return this.size;
    }

    @Override
    public void add(T obj)
    {
        if(obj == null) return;
        if(size >= array.length) array = expand(this.array, Object[].class);
        array[size] = (Object)obj;
        size++;
    }

    @Override
    public boolean remove(int index)
    {
        if(array == null || index >= size || index < 0) return false;
        System.arraycopy(array, index+1, array, index, size -1-index);
        array[size -1] = null;
        size--;
        return true;
    }

    @Override
    public boolean remove(T obj)
    {
        return remove(indexOf(obj));
    }

    @Override
    public T at(int index) {
        if(index >= size || index < 0) return null;
        return (T)array[index];
    }

    @Override
    public T pop_back() {
        return (T)array[size -1];
    }

    @Override
    public boolean contains(T obj) {
        return (indexOf(obj) != -1);
    }

    @Override
    public int indexOf(T obj) {
        for(int i = 0; i < size; i++)
            if(array[i].equals(obj)) return i;
        return -1;
    }

    @Override
    public T[] toArray(Class<T[]> type) {
        T[] array = (T[])Array.newInstance(type.getComponentType(), size);
        System.arraycopy(this.array, 0, array, 0, size);
        return array;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Arraylist of ").append(array.getClass().getCanonicalName()).append(".\n")
                .append("size: ").append(size).append("\n")
                .append("capacity: ").append(array.length).append("\n");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
