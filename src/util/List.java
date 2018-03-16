package util;

public interface List<T> extends Iterable<T>
{
    int DEFAULT_CAPACITY = 8;
    int size();
    void add(T obj);
    boolean remove(int index);
    boolean remove(T obj);
    T at(int index);
    T pop_back();
    boolean contains(T obj);
    int indexOf(T obj);
    T[] toArray(Class<T[]> type);
}
