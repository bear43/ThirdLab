package io;

public interface FileSource<T> extends Source<T>
{
    void setPath(String path);
    String getPath();
}
