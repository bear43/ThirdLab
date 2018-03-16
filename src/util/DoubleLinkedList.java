package util;

import java.lang.reflect.Array;
import java.util.Iterator;

//TODO СДЕЛАТЬ БЛЯТЬ ВЕЗДЕ ДЖЕНЕРИКИ! Задженерекил
class DuoNode<T>
{
    private DuoNode<T> previous;
    private T value;
    private DuoNode<T> next;

    public DuoNode(DuoNode<T> previous, T value, DuoNode<T> next)
    {
        this.previous = previous;
        this.value = value;
        this.next = next;
    }

    public DuoNode(DuoNode<T> previous, T value)
    {
        this(previous, value, null);
    }

    public DuoNode()
    {

    }

    public DuoNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DuoNode<T> previous) {
        this.previous = previous;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public DuoNode<T> getNext() {
        return next;
    }

    public void setNext(DuoNode<T> next) {
        this.next = next;
    }
}

public class DoubleLinkedList<T> implements List<T>
{

    private int length;
    private DuoNode<T> head;
    private DuoNode<T> end;

    public DoubleLinkedList(T[] array, Class<T[]> type)
    {
        for (T anArray : array) add(anArray);
    }

    public DoubleLinkedList()
    {

    }

    @Override
    public int size() {
        return length;
    }

    /**
     *
     * @param obj
     * @exception NullPointerException if obj == null
     */
    @Override
    public void add(T obj)
    {
        if(obj == null) throw new NullPointerException();
        if(head == null)
        {
            head = new DuoNode<T>(null, obj, null);
            head.setNext(head);
            end = head;
            head.setPrevious(end);
            length++;
        } else {
            end.setNext(new DuoNode<T>(end, obj, head));
            end = end.getNext();
            head.setPrevious(end);
            length++;
        }
    }

    //ToDO подправь логику работы с prev

    /**
     *
     * @param index
     * @exception ArrayIndexOutOfBoundsException if index >= length || index < 0
     */
    @Override
    public boolean remove(int index)
    {
        if(index >= length || index < 0) throw new ArrayIndexOutOfBoundsException();
        int half = length/2;
        //todo следующие 2 условия подходят под 3-е условие, и потому в топку их. EDITED
        DuoNode<T> current = end;
        if(index < half)
            for (int i = 0; i < index; i++)
                current = current.getNext();
        else
            for(int i = length; i >= index; i--)
                current = current.getPrevious();
        DuoNode<T> prev = current;
        current.setNext(current.getNext().getNext());
        current.getNext().setPrevious(prev);
        if(index == 0) head = current.getNext();
        if(index == length-1) end = current.getNext();
        return true;
    }

    @Override
    public boolean remove(T obj)
    {
        if(obj == null) return false;
        int index = indexOf(obj);
        if(index == -1) return false;
        return remove(index);
    }

    @Override
    public T at(int index)
    {
        return pop_node_at(index).getValue();
    }
    //todo broken method REPAIRED
    private DuoNode<T> pop_node_at(int index)
    {
        if(index >= length || index < 0) throw new ArrayIndexOutOfBoundsException();//todo THROWED
        DuoNode<T> current = head;
        if(index < length/2)
        {
            for(int i = 0; i < index; i++)
                current = current.getNext();
        }
        else
        {
            current = end;
            for(int i = length-1; i > index; i--)
                current = current.getPrevious();
        }
        return current;
    }

    @Override
    public T pop_back() {
        return end.getValue();
    }

    @Override
    public boolean contains(T obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public int indexOf(T obj) {
        if(obj == null) return -1;
        DuoNode<T> current = head;
        for(int i = 0; i < length; i++)
        {
            if(current.getValue().equals(obj))
                return i;
            current = current.getNext();
        }
        return -1;
    }

    @Override
    public T[] toArray(Class<T[]> type) {
        if(length == 0) return null;
        T[] array = (T[])Array.newInstance(type.getComponentType(), length);
        DuoNode<T> current = head;
        for(int i = 0; i < length; i++)
        {
            array[i] = current.getValue();
            current = current.getNext();
        }
        return array;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    //todo equals() hashCode() toString()
}
