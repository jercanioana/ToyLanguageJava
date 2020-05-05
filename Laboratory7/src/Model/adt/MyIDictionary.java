package Model.adt;

import Model.Value.Value;
import exception.MyException;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public interface MyIDictionary<T, T1> {
    boolean isDefined(T id);
    void add(T id, T1 val);
    void update(T id, T1 val);
    void remove(T id);
    T1 getVal(T id);
    HashMap<T,T1> getDictionary();
    T1 lookup(T id) throws MyException;
    MyIDictionary<T, T1> clone();

    Map<T, T1> getContent();
}
