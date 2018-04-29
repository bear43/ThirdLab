package io;


public abstract class GroupsManagerFileSource<T> implements FileSource<T>
{
    protected String path;

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
