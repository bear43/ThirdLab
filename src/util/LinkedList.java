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
    //todo чтоб не писать херню с добавлением, добавь работу со следущим полем
    private Node<T> tail;
    /* Количество элементов в списке */
    private int size;
    /* Инициализирует список из входного массива */
    public LinkedList(T[] array) {
        if (array != null)
        {
            for (T element : array)
                    push(element);
        }
    }

    public LinkedList() {
    }

    public int length() {
        return size;
    }

    /* Добавляет новый элемент в конец */
    //todo tail
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
        if (index >= size || head == null) return null; //todo throw
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
    //todo tail
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

    private Node<T> getHead() {
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
        Node<T> victim = popNode_at(index -1); //todo переделай в соответсвии с логикой index-1 (без цикла)
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
    //todo no indexes
    public boolean remove(T obj)
    {
        int removeIndex = indexOf(obj); //
        if(removeIndex == -1) return false;
        return remove(removeIndex);
    }

    /* Добавление элемента в начало списка */
    public void push_begin(T obj) {
        push_at(0, obj);
    }

    /* Добавляет элемент в определенную позицию */
    //todo жесть капец сделай вставку
    public void push_at(int index, T obj) {

    }

    public T[] toArray(Class<T[]> type)
    {
        //todo
    }

    /* Возвращает массив элементов */
    private T[] listToArray(Node<T> current, Class<T[]> type)
    {
        //todo
    }

    /* Ищет элемент в списке. */
    public boolean contains(T obj)
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
            if(pop_at(i).equals(obj)) //todo иди по ссылкам без дибилизма с pop_at
                return i;
        return -1;
    }

    //todo equals() toString() hashcode()
}
