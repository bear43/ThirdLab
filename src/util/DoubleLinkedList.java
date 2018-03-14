package util;

import java.lang.reflect.Array;

import static util.Util.*;

class DuoNode<T>
{
    private DuoNode previously;
    private T value;
    private DuoNode next;

    public DuoNode(DuoNode previously, T value, DuoNode next)
    {
        this.previously = previously;
        this.value = value;
        this.next = next;
    }

    public DuoNode(DuoNode previously, T value)
    {
        this(previously, value, null);
    }

    public DuoNode()
    {

    }

    public DuoNode getPreviously() {
        return previously;
    }

    public void setPreviously(DuoNode previously) {
        this.previously = previously;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public DuoNode getNext() {
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
        if(array == null) return;
        T[] copy = (T[])Array.newInstance(type.getComponentType(), shiftNulls(array));
        System.arraycopy(array, 0, copy, 0, copy.length);
        for(int i = 0; i < copy.length; i++)
            push(copy[i]);
    }

    public DoubleLinkedList()
    {

    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public void push(T obj)
    {
        if(obj == null) return;
        if(head == null)
        {
            head = new DuoNode(null, obj, null);
            head.setNext(head);
            end = head;
            head.setPreviously(end);
            length++;
            return;
        }
        end.setNext(new DuoNode(end, obj, head));
        end = end.getNext();
        head.setPreviously(end);
        length++;
    }

    @Override
    public boolean remove(int index)
    {
        if(index >= length || index < 0) return false;
        int half = length/2;
        length--;
        if(length == 0)
        {
            head = null;
            end = null;
            return true;
        }
        if(index == 0)
        {
            head = head.getNext();
            head.setPreviously(end);
            end.setNext(head);
            return true;
        }
        if(index == length)
        {
            end = end.getPreviously();
            end.setNext(head);
            head.setPreviously(end);
            return true;
        }
        DuoNode<T> current;
        if(index < half)
        {
            current = head;
            for (int i = 0; i < index-1; i++)
                current = current.getNext();
        }
        else
        {
            current = end;
            for(int i = length; i >= index; i--)
                current = current.getPreviously();
        }
        DuoNode<T> prev = current;
        current.setNext(current.getNext().getNext());
        current.setPreviously(prev);
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

    public DuoNode<T> pop_node_at(int index)
    {
        if(index >= length || index < 0) return null;
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
                current = current.getPreviously();
        }
        return current;
    }

    @Override
    public T pop_back() {
        return end.getValue();
    }

    @Override
    public boolean find(T obj) {
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
}
