package util;

import java.lang.reflect.Array;

import static util.Util.*;

//TODO СДЕЛАТЬ БЛЯТЬ ВЕЗДЕ ДЖЕНЕРИКИ!
class DuoNode<T>
{
    private DuoNode previous;
    private T value;
    private DuoNode next;

    public DuoNode(DuoNode previous, T value, DuoNode next)
    {
        this.previous = previous;
        this.value = value;
        this.next = next;
    }

    public DuoNode(DuoNode previous, T value)
    {
        this(previous, value, null);
    }

    public DuoNode()
    {

    }

    public DuoNode getPrevious() {
        return previous;
    }

    public void setPrevious(DuoNode previous) {
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

    public void setNext(DuoNode next) {
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
        for (T anArray : array) push(anArray);
    }

    public DoubleLinkedList()
    {

    }

    @Override
    public int length() {
        return length;
    }

    /**
     *
     * @param obj
     * @exception NullPointerException if obj == null
     */
    @Override
    public void push(T obj)
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
    @Override
    public boolean remove(int index)
    {
        if(index >= length || index < 0) throw new ArrayIndexOutOfBoundsException();
        int half = length/2;
        length--;
        if(length == 0)
        {
            head = null;
            end = null;
            return true;
        }
        //todo следующие 2 условия подходят под 3-е условие, и потому в топку их.
        if(index == 0)
        {
            head = head.getNext();
            head.setPrevious(end);
            end.setNext(head);
            return true;
        }
        if(index == length)
        {
            end = end.getPrevious();
            end.setNext(head);
            head.setPrevious(end);
            return true;
        }
        DuoNode<T> current;
        if(index < half)
        {
            current = head;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();
        }
        else
        {
            current = end;
            for(int i = length; i >= index; i--)
                current = current.getPrevious();
        }
        DuoNode<T> prev = current;
        current.setNext(current.getNext().getNext());
        current.setPrevious(prev);
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
    public T pop_at(int index)
    {
        return pop_node_at(index).getValue();
    }
    //todo broken method
    private DuoNode<T> pop_node_at(int index)
    {
        if(index >= length || index < 0) //todo throw ;
        DuoNode<T> current;
        if(index < length/2)
        {
            current = head;
            for(int i = 0; i <= index; i++)
                current = current.getNext();
        }
        else
        {
            current = end;
            for(int i = length-1; i >= index; i--)
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

    //todo equals() hashCode() toString()
}
