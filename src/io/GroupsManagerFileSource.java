package io;


public abstract class GroupsManagerFileSource<T> implements FileSource<T>
{
    String path;

    static final String PATH_DELIMITER = "\\";

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
