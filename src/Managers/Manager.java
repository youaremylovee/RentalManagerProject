package Managers;

import java.util.List;

public interface Manager<T> {
    public Response add(T t);
    public Response update(T t);
    public Response delete(T t);
    T getOne(String ID);
    public List<T> getAll();
}
