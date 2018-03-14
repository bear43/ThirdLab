package util;

public interface List<T>
{
    int DEFAULT_CAPACITY = 8;
    int length();
    void push(T obj);
    boolean remove(int index);
    boolean remove(T obj);
    T pop_at(int index);
    T pop_back();
    boolean find(T obj);
    int indexOf(T obj);
    T[] toArray(Class<T[]> type);
}
