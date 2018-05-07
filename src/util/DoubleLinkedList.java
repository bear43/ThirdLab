package util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static util.Util.getUnique;

class DuoNode<T> implements Serializable
{
    private DuoNode<T> previous;
    private T value;
    private DuoNode<T> next;

    DuoNode(DuoNode<T> previous, T value, DuoNode<T> next)
    {
        this.previous = previous;
        this.value = value;
        this.next = next;
    }

    DuoNode(DuoNode<T> previous, T value)
    {
        this(previous, value, null);
    }

    DuoNode()
    {

    }

    DuoNode<T> getPrevious() {
        return previous;
    }

    void setPrevious(DuoNode<T> previous) {
        this.previous = previous;
    }

    T getValue() {
        return value;
    }

    void setValue(T value) {
        this.value = value;
    }

    DuoNode<T> getNext() {
        return next;
    }

    void setNext(DuoNode<T> next) {
        this.next = next;
    }

    @Override
    public int hashCode()
    {
        return value != null ? value.hashCode() : 0;
    }
}

public class DoubleLinkedList<T> implements List<T>
{

    private int size;
    private DuoNode<T> head;
    private DuoNode<T> end;

    public DoubleLinkedList(T[] array)
    {
        if(array == null) throw new NullPointerException();
        for (T anArray : array) add(anArray);
    }

    public DoubleLinkedList()
    {

    }

    @Override
    public int size() {
        return size;
    }

    /**
     *
     * @param obj Object to add
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
            size++;
        } else {
            end.setNext(new DuoNode<T>(end, obj, head));
            end = end.getNext();
            head.setPrevious(end);
            size++;
        }
    }


    /**
     *
     * @param index Which index remove
     * @exception ArrayIndexOutOfBoundsException if index >= size || index < 0
     */
    @Override
    public boolean remove(int index)
    {
        if(index >= size || index < 0) throw new ArrayIndexOutOfBoundsException();
        int half = size /2;
        DuoNode<T> current = end;
        if(index < half)
            for (int i = 0; i < index; i++)
                current = current.getNext();
        else
            for(int i = size; i > index; i--)
                current = current.getPrevious();
        current.setNext(current.getNext().getNext());
        current.getNext().setPrevious(current);
        if(index == 0) head = current.getNext();
        else if(index == size -1) end = current;
        size--;
        if(size == 0)
        {
            head = null;
            end = null;
        }
        return true;
    }

    /**
     *
     * @param obj Element to remove. Need to override equals method
     * @return true if removed or false if not
     * @exception NullPointerException If @param obj equals null
     */
    @Override
    public boolean remove(Object obj)
    {
        if(obj == null) throw new NullPointerException();
        DuoNode<T> current = head;
        int counter = 0;
        if(obj.equals(head.getValue()) && obj.equals(end.getValue()) && size == 1)
        {
            head = null;
            end = null;
            size--;
        }
        while(current != null && counter < size)
        {
            if(current.getValue().equals(obj))
            {
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
                if(current == head)
                    head = current.getNext();
                else if(current == end)
                    end = current.getPrevious();
                size--;
                return true;
            }
            current = current.getNext();
            counter++;
        }
        return false;
    }

    @Override
    public T at(int index)
    {
        return pop_node_at(index).getValue();
    }
    private DuoNode<T> pop_node_at(int index)
    {
        if(index >= size || index < 0) throw new ArrayIndexOutOfBoundsException();
        DuoNode<T> current = head;
        if(index < size /2)
        {
            for(int i = 0; i < index; i++)
                current = current.getNext();
        }
        else
        {
            current = end;
            for(int i = size -1; i > index; i--)
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
    public int indexOf(Object obj) {
        if(obj == null) return -1;
        DuoNode<T> current = head;
        for(int i = 0; i < size; i++)
        {
            if(current.getValue().equals(obj))
                return i;
            current = current.getNext();
        }
        return -1;
    }

    @Override
    public T[] toArray(Class<T[]> type) {
        if(size == 0) return null;
        T[] array = (T[])Array.newInstance(type.getComponentType(), size);
        DuoNode<T> current = head;
        for(int i = 0; i < size; i++)
        {
            array[i] = current.getValue();
            current = current.getNext();
        }
        return array;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter<T>(0);
    }

    private class Iter<E> implements Iterator<T>
    {
        private DuoNode<T> previous = end;
        private DuoNode<T> current = null;
        private DuoNode<T> next = head;

        public Iter(int index)
        {
            if(index >= size || index < 0) return;
            for(int i = 0; i < index; i++)
            {
                previous = current;
                current = next;
                next = next.getNext();
            }
        }

        public Iter()
        {
            this(0);
        }

        @Override
        public boolean hasNext()
        {
            return current != end;
        }

        @Override
        public T next()
        {
            if(current != end)
            {
                this.previous = this.current;
                this.current = this.next;
                this.next = this.next.getNext();
            }
            return current.getValue();
        }

        @Override
        public void remove()
        {
            if(current == head)
            {
                if(head == end) end = null;
                head = next;
                current = null;
            }
            else if(current == end)
            {
                current = previous;
                current.setNext(head);
                end = current;
                head.setPrevious(end);
            }
            else
            {
                if(head != end)
                {
                    previous.setNext(next);
                    current = null;
                }
                else
                {
                    head = null;
                    end = null;
                    previous = null;
                    current = null;
                    next = null;
                }
            }
            size--;
        }
    }



    @Override
    public String toString()
    {
        if(head == null) throw new NullPointerException();
        StringBuilder sb = new StringBuilder();
        DuoNode<T> current = head;
        for(int i = 0; i < size; i++)
        {
            sb.append(current.getValue().toString());
            current = current.getNext();
        }
        return sb.toString();
    }


    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof DoubleLinkedList)
        {
            DoubleLinkedList<T> comparingList = new DoubleLinkedList<T>();
            getUnique((DoubleLinkedList)obj, comparingList);
            DoubleLinkedList<T> uniqueList = new DoubleLinkedList<T>();
            getUnique(this, uniqueList);
            if(comparingList.size != uniqueList.size) return false;
            for(T element : uniqueList)
                if(!comparingList.contains(element)) return false;
            return true;
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        int hashXOR = 0;
        DuoNode<T> currentElement = this.head;
        DuoNode<T> head = this.head;
        hashXOR ^= currentElement.hashCode();
        currentElement = currentElement.getNext();
        while(currentElement != null && currentElement.getValue() != null && currentElement != head)
        {
            hashXOR ^= currentElement.hashCode();
            currentElement = currentElement.getNext();
        }
        return hashXOR;
    }

}
