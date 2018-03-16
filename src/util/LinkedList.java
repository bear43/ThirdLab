package util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

class Node <T>
{
    private T data;
    private Node<T> next;

    public Node(T data, Node<T> next)
    {
        this.data = data;
        this.next = next;
    }

    public Node()
    {
        this(null, null);
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setNext(Node<T> next)
    {
        this.next = next;
    }

    public Node<T> getNext()
    {
        return next;
    }
}

public class LinkedList<T> implements List<T>
{
    /* Ссылка на голову */
    private Node<T> head;
    //todo чтоб не писать херню с добавлением, добавь работу со следущим полем ADDED
    /* Ссылка на хвост в виде последнего элемента(конца)*/
    private Node<T> tail;
    /* Количество элементов в списке */
    private int size;
    /* Инициализирует список из входного массива */
    public LinkedList(T[] array) {
        if (array != null)
        {
            for (T element : array)
                    add(element);
        }
    }

    public LinkedList() {
    }

    public int size() {
        return size;
    }

    /* Добавляет новый элемент в конец */
    //todo tail TAILED
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

    /* Возвращает узел с данным индексом*/

    /**
     *
     * @param index
     * @exception ArrayIndexOutOfBoundsException
     */
    private Node<T> popNode_at(int index)
    {
        if (index >= size || index < 0) throw new ArrayIndexOutOfBoundsException(); //todo throw THROWED
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
    //todo tail TAILED
    public T pop_back()
    {
        return tail.getData();
    }

    /* Возвращает первый элемент*/
    public T pop_begin() {
        return head.getData();
    }

    private Node<T> getHead() {
        return head;
    }

    /* Удаление элемента по индексу */
    public boolean remove(int index) {
        if (head == null) return false;
        if(index < 0 || index >= size) throw new ArrayIndexOutOfBoundsException();
        if(index == 0)//Удаляем голову
        {
            head = head.getNext();
            size--;
            return true;
        }
        Node<T> prev = popNode_at(index - 1 );
        prev.setNext(prev.getNext().getNext());
        size--;
        return true;
    }

    /* Удаляет элемент по ссылке на него*/
    //todo no indexes DONE?
    public boolean remove(T obj)
    {
        Node<T> current = head;
        if(obj.equals(head))
        {
            head = head.getNext();
            size--;
            return true;
        }
        else
        {
            for (int i = 0; i < size-1; i++)
            {
                if (current.getNext().getData().equals(obj))
                {
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
    //todo жесть капец сделай вставку ВРОДЕ СДЕЛАЛ, НУ,ТАК СЕБЕ, КОСЯЧНО
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
            for(int i = size; i < index; i++)
            {
                tail.setNext(new Node<T>(null, null));
                tail = tail.getNext();
            }
            tail.setNext(new Node<T>(obj, null));
            size += index-size+1;
        }
        else
        {
            Node<T> current = head;
            for(int i = 0; i <= index-1; i++)
                head = head.getNext();
            current.setData(obj);
            size++;
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
        //todo TODODED
    }

    /* Ищет элемент в списке. */
    public boolean contains(T obj)
    {
        if(head == null) throw new NullPointerException();
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
        if(head == null) return -1;
        int counter = -1;
        for(T o : this)
        {
            counter++;
            if(o.equals(obj))
                return counter;
        }
        return -1;
    }

    //class Iterator implements Iterable
    //todo equals() toString() hashcode()

    private class Iter<E> implements ListIterator<T>
    {
        private Node<T> last = head;
        private Node<T> next;
        private boolean hasNext;
        private int nextIndex;

        public Iter(int index)
        {
            if(index >= size || index < 0) throw new NoSuchElementException();
            for(int i = 0; i < index; i++)
                last = last.getNext();
            next = last;
            hasNext = next != null;
            nextIndex = index++;
        }

        @Override
        public boolean hasNext()
        {
            return this.hasNext;
        }

        @Override
        public T next()
        {
            if(hasNext)
            {
                this.last = this.next;
                this.next = this.next.getNext();
                nextIndex++;
                hasNext = this.next != null;
                return last.getData();
            }
            else throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious()
        {
            return false;
        }

        @Override
        public T previous()
        {
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex-1;
        }

        @Override
        public void remove() {

        }

        @Override
        public void forEachRemaining(Consumer<? super T> action)
        {
            while (nextIndex != size)
            {
                action.accept(next());
            }
        }

        @Override
        public void set(T e) {

        }

        @Override
        public void add(T e) {

        }
    }

    public Iterator<T> iterator()
    {
        return new Iter<T>(0);
    }
}
