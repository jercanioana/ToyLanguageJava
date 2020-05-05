package Model.adt;

import exception.MyException;

import java.util.HashMap;
import java.util.Map;

public interface MyIHeap<T, T1> {
    boolean isDefined(T id);
    public int getFreeLocation();
    void add(T id, T1 val);
    void update(T id, T1 val);
    void remove(T id);
    T1 getVal(T id);
    HashMap<T,T1> getDictionary();
    T1 lookup(T id) throws MyException;
    void setContent(Map<T,T1> content);
    Map<T,T1> getContent();
}
