package util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static util.Util.getUnique;

class Node <T>
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
    //todo чтоб не писать херню с добавлением, добавь работу со следущим полем ADDED
    /* Ссылка на хвост в виде последнего элемента(конца)*/
    private Node<T> tail;
    /* Количество элементов в списке */
    private int size;
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
    //todo no indexes DONE?
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

    //todo equals() toString() hashcode() DONE?

    private class Iter<E> implements ListIterator<T>
    {
        private Node<T> prev = null;
        private Node<T> last = null;
        private Node<T> next = head;
        private boolean hasNext;
        private boolean hasPrev;
        private int nextIndex;

        public Iter(int index)
        {
            if(index >= size || index < 0) throw new NoSuchElementException();
            for(int i = 0; i < index; i++)
                last = last.getNext();
            hasNext = next != null;
            nextIndex = index;
        }

        @Override
        public boolean hasNext()
        {
            return this.hasNext;
        }

        @Override
        public T next()
        {
            if(next != null)
            {
                this.prev = last;
                this.last = this.next;
                this.next = this.next != null ? this.next.getNext() : null;
                nextIndex++;
                hasNext = this.next != null;
                hasPrev = this.prev != null;
                return last.getData();
            }
            else
            {
                hasNext = false;
                return last.getData();
            }
        }

        @Override
        public boolean hasPrevious()
        {
            return hasPrev;
        }

        @Override
        public T previous()
        {
            if(hasPrev)
                return prev.getData();
            else
                throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex-2;
        }

        @Override
        public void remove()
        {
            if(hasPrev && hasNext)
            {
                prev.setNext(next);
                last = next;
                next = next.getNext();
                hasNext = last != null;
            }
            else if(hasPrev)
            {
                tail = last = prev;
                prev.setNext(null);
                next = null;
                hasNext = false;
                hasPrev = prev != null;
            }
            else if(hasNext)
            {
                if(size == 1)
                {
                    head = null;
                    tail = null;
                    hasNext = false;
                    hasPrev = false;
                    last = null;
                    nextIndex++;
                }
                else
                {
                    head = next;
                    prev = null;
                    last = null;
                    next = next.getNext();
                    hasPrev = prev != null;
                    hasNext = next != null;
                }
            }
            nextIndex--;
            size--;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action)
        {
            while (nextIndex != size)
                action.accept(next());
        }

        @Override
        public void set(T e) {

        }

        @Override
        public void add(T e)
        {

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
