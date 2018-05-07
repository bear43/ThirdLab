package util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static util.Util.getUnique;

class Node <T> implements Serializable
{
    private T data;
    private Node<T> next;

    Node(T data, Node<T> next)
    {
        this.data = data;
        this.next = next;
    }

    public Node()
    {
        this(null, null);
    }

    void setData(T data) {
        this.data = data;
    }

    T getData() {
        return data;
    }

    void setNext(Node<T> next)
    {
        this.next = next;
    }

    Node<T> getNext()
    {
        return next;
    }

    @Override
    public int hashCode()
    {
        return (data != null ? data.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof Node) &&
                ((Node) obj).data.equals(data) &&
                ((Node) obj).next.equals(next);
    }
}

public class LinkedList<T> implements List<T>
{
    /* Какой класс является используемым для дженерика.
    * Используется для операции сравнивания */
    /* Ссылка на голову */
    private Node<T> head;
    /* Ссылка на хвост в виде последнего элемента(конца)*/
    private Node<T> tail;
    /* Количество элементов в списке */
    private int size;

    public LinkedList(T[] array, int size)
    {
        if(array != null)
            for(int i = 0; i < size; i++)
                add(array[i]);
    }
    /* Инициализирует список из входного массива */
    public LinkedList(T[] array)
    {
        if (array != null)
        {
            for (T element : array)
                    add(element);
        }
    }

    public LinkedList()
    {

    }

    public int size() {
        return size;
    }

    /* Добавляет новый элемент в конец */
    public void add(T obj)
    {
        if(tail == null)
        {
            head = new Node<T>(obj, null);
            tail = head;
        }
        else
        {
            tail.setNext(new Node<T>(obj, null));
            tail = tail.getNext();
        }
        size++;
    }

    public void add(T[] obj)
    {
        for(T o : obj)
            add(o);
    }

    /* Возвращает узел с данным индексом*/

    /**
     *
     * @exception ArrayIndexOutOfBoundsException opnioj
     */
    private Node<T> popNode_at(int index)
    {
        if (index >= size || index < 0) throw new ArrayIndexOutOfBoundsException();
        Node<T> obj = head;
        for (int i = 0; i < index; i++)
            obj = obj.getNext();
        return obj;
    }

    /* Обращение к определенному индексу в массиве */
    public T at(int index) {
        return popNode_at(index).getData();
    }

    /* возвращает последний элемент */
    public T pop_back()
    {
        return tail.getData();
    }

    /* Возвращает первый элемент*/
    public T pop_begin() {
        return head.getData();
    }

    /* Удаление элемента по индексу */
    public boolean remove(int index) {
        if (head == null) return false;
        if(index < 0 || index >= size) throw new ArrayIndexOutOfBoundsException();
        if(index == 0)//Удаляем голову
        {
            head = head.getNext();
            if(size == 1) tail = null;
            size--;
            return true;
        }
        Node<T> prev = popNode_at(index - 1 );
        prev.setNext(prev.getNext().getNext());
        if(index == size-1) tail = prev;
        size--;
        return true;
    }

    /* Удаляет элемент по ссылке на него*/
    public boolean remove(T obj)
    {
        Node<T> current = head;
        if(obj.equals(head.getData()))
        {
            head = head.getNext();
            if(size == 1) tail = null;
            size--;
            return true;
        }
        else
        {
            for (int i = 0; i < size-1; i++)
            {
                if (current.getNext().getData().equals(obj))
                {
                    if(current.getNext() == tail) tail = current;
                    current.setNext(current.getNext().getNext());
                    size--;
                    return true;
                }
                current = current.getNext();
            }
        }
        return false;
    }

    /* Добавление элемента в начало списка */
    public void push_begin(T obj) {
        push_at(0, obj);
    }

    /* Добавляет элемент в определенную позицию */
    /**
     *
     * @param index Element index to modify/create
     * @exception ArrayIndexOutOfBoundsException if index < 0
     */
    public void push_at(int index, T obj)
    {
        if(index < 0) throw new ArrayIndexOutOfBoundsException();
        if(index >= size)
        {
            if(index == 0)
                add(obj);
            else
            {
                for (int i = size; i < index; i++) {
                    tail.setNext(new Node<T>(null, null));
                    tail = tail.getNext();
                }
                tail.setNext(new Node<T>(obj, null));
                tail = tail.getNext();
                size += index - size + 1;
            }
        }
        else
        {
            Node<T> current = head;
            for(int i = 0; i <= index-1; i++)
                head = head.getNext();
            current.setData(obj);
        }
    }

    /* Возвращает массив элементов */
    public T[] toArray(Class<T[]> type)
    {
        T[] array = (T[]) Array.newInstance(type.getComponentType(), size);
        int index = 0;
        for(T o : this)
        {
            array[index] = o;
            index++;
        }
        return array;
    }

    /* Ищет элемент в списке. */
    public boolean contains(T obj)
    {
        if(size != 0 && head == null) throw new NullPointerException();
        Node<T> current = head;
        while(current != null)
        {
            if(current.getData().equals(obj))
                return true;
            current = current.getNext();
        }
        return false;
    }
    /* Возвращает "индекс" элемента */
    public int indexOf(T obj)
    {
        if(head == null || obj == null) return -1;
        Node<T> current = head;
        for(int i = 0; i < size; i++)
        {
            if(current.equals(obj))
                return i;
            else
                current = current.getNext();
        }
        return -1;
    }
    private class Iter<E> implements Iterator<T>
    {
        private Node<T> previous;
        private Node<T> current;
        private Node<T> next = head;

        Iter(int index) {
            if (index >= size || index < 0) throw new NoSuchElementException();
            for (int i = 0; i < index; i++)
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
            return next != null;
        }

        @Override
        public T next()
        {
            if(next != null)
            {
                previous = current;
                current = next;
                next = next.getNext();
            }
            return current.getData();
        }

        @Override
        public void remove()
        {
            if(current == head)
            {
                if(head == tail) tail = null;
                head = next;
                current.setData(null);
                current = null;
            }
            else if(current == tail)
            {
                current = previous;
                current.setData(null);
                current.setNext(null);
                tail = current;
            }
            else
            {
                previous.setNext(next);
                current = null;
            }
            size--;
        }
    }

    public Iterator<T> iterator()
    {
        return new Iter<T>(0);
    }

    /**
     *
     * @param obj Comparing object
     * @return true if elements are equal and its order isn't important
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof LinkedList)
        {
            LinkedList<T> list = new LinkedList<T>();
            getUnique((LinkedList<T>)obj, list);
            LinkedList<T> uniqueList = new LinkedList<T>();
            getUnique(this, uniqueList);
            if(list.size != uniqueList.size) return false;
            for(T element : uniqueList)
                if(!list.contains(element))
                    return false;
            return true;
        }
        else
            return false;
    }

    /**
     *
     * @exception NullPointerException if head references to null
     */
    @Override
    public int hashCode()
    {
        if(head == null) throw new NullPointerException();
        Node<T> currentElement = head;
        int elementHash = 0;
        for(int i = 0; i < size; i++)
        {
            elementHash ^= currentElement.hashCode();
            currentElement = currentElement.getNext();
        }
        return size ^ elementHash;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Node<T> currentElement = head;
        for(int i = 0; i < size; i++)
        {
            sb.append(currentElement.getData().toString()).append("\n");
            currentElement = currentElement.getNext();
        }
        return sb.toString();
    }
}
