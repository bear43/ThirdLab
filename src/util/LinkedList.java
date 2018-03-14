package util;

import java.lang.reflect.Array;

import static util.Util.*;

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
    /* Количество элементов в списке */
    private int size;
    /* Инициализирует список из входного массива */
    public LinkedList(T[] array, Class<T[]> type) {
        if (array != null)
        {
            T[] copied = (T[])Array.newInstance(type.getComponentType(), array.length);
            System.arraycopy(array, 0, copied, 0, array.length);
            for (T element : copied)
                if (element != null)
                    push(element);
        }
    }

    public LinkedList() {
    }

    public int length() {
        return size;
    }

    /* Добавляет новый элемент в конец */
    public void push(T obj) {
        if (head == null) {
            head = new Node(obj, null);
            size++;
            return;
        }
        Node<T> current = head;
        while (current != null) {
            if (current.getNext() == null)//Если следующий элемент несуществует
            {
                current.setNext(new Node(obj, null));//Делаем ссылку на новый элемент
                size++;
                return;
            }
            current = current.getNext();//Переходим к следующему элементу
        }
    }

    /* Возвращает узел с данным индексом*/
    private Node<T> popNode_at(int index)
    {
        if (index >= size || head == null) return null;
        Node<T> obj = head;
        for (int i = 0; i < index; i++)
            obj = obj.getNext();
        return obj;
    }

    /* Обращение к определенному индексу в массиве */
    public T pop_at(int index) {
        return popNode_at(index).getData();
    }

    /* возвращает последний элемент */
    public T pop_back() {
        if (head == null) return null;
        Node<T> obj = head;
        while (obj.getNext() != null)
            obj = head.getNext();
        return obj.getData();
    }

    /* Возвращает первый элемент*/
    public T pop_begin() {
        return head.getData();
    }

    public Node<T> getHead() {
        return head;
    }

    /* Удаление элемента по индексу */
    public boolean remove(int index) {
        if (head == null) return false;
        if(index == 0)//Удаляем голову
        {
            head = head.getNext();
            size--;
            return true;
        }
        Node<T> victim = popNode_at(index);
        Node<T> current = head;
        while (current != null)
        {
            if (current.getNext() == victim) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /* Удаляет элемент по ссылке на него*/
    public boolean remove(T obj)
    {
        int removeIndex = indexOf(obj);
        if(removeIndex == -1) return false;
        return remove(removeIndex);
    }

    /* Добавление элемента в начало списка */
    public void push_begin(T obj) {
        head = new Node<T>(obj, head);
        size++;
    }

    /* Добавляет элемент в определенную позицию */
    public void push_at(int index, T obj) {
        if (index > size) return;//индекс больше, чем количество элементов
        Node<T> replaceNode = popNode_at(index);
        if (replaceNode == null) {
            push(obj);
            return;
        }
        replaceNode.setData(obj);
    }

    public T[] toArray(Class<T[]> type)
    {
        if(head == null) return null;
        T[] New = listToArray(head, type);
        fullReverse(New, size);
        return New;
    }

    /* Возвращает массив элементов */
    private T[] listToArray(Node<T> current, Class<T[]> type)
    {
        if (current.getNext() == null) {
            T[] lastElement = (T[]) Array.newInstance(type.getComponentType(), 1);
            lastElement[0] = current.getData();
            return lastElement;
        }
        return add(listToArray(current.getNext(), type), current.getData(), type);
    }

    /* Ищет элемент в списке. */
    public boolean find(T obj)
    {
        if(head == null) return false;
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
        Node<T> current = head;
        for(int i = 0; i < size; i++)
            if(pop_at(i).equals(obj))
                return i;
        return -1;
    }
}
