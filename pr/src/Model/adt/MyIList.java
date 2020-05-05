package Model.adt;

import java.util.LinkedList;
import java.util.Queue;

public interface MyIList<T> {
    void add(T v);
    T pop();
    Queue<T> getList();
    String toString();

}
