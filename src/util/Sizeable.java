package util;

import java.lang.reflect.Array;

import static java.lang.reflect.Array.newInstance;

public class Sizeable {
    protected int size = 0;//Общее количество чего либо
    public int getSize()
    {
        return size;
    }//getter
    public void setSize(int c)
    {
        size = c;
    }//setter
    public void incCount()
    {
        size++;
    }//Increment
    public void decCount()
    {
        size--;
    }//Decrement
}
