package Model.adt;

import exception.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<T, T1> implements MyIHeap<T, T1> {
    private HashMap<T, T1> dictionary;
    private int freeLocation;
    public MyHeap(){
        dictionary = new HashMap<T, T1>();
        freeLocation = 1;
    }

    public int getFreeLocation(){
        return this.freeLocation;
    }

    @Override
    public void add(T k, T1 v){
        dictionary.put(k, v);
        freeLocation++;
    }
    @Override
    public void update(T k, T1 v){
        dictionary.put(k, v);
    }

    @Override
    public void remove(T id) {
        dictionary.remove(id);
        freeLocation = (int) id;
    }

    @Override
    public T1 getVal(T id) {
        return dictionary.get(id);
    }

    @Override
    public HashMap<T, T1> getDictionary() {
        return dictionary;
    }

    @Override
    public T1 lookup(T id) throws MyException {
        if (dictionary.get(id) != null)
            return dictionary.get(id);
        throw new MyException("No such key");
    }

    @Override
    public void setContent(Map<T, T1> content) {
        this.dictionary = (HashMap<T, T1>) content;
    }

    @Override
    public Map<T, T1> getContent() {
        return this.dictionary;
    }

    @Override
    public boolean isDefined(T k){
        if(dictionary.get(k) != null)
            return true;
        return false;
    }

    public String toString(){
        String result = " ";
        Set<T> keys = dictionary.keySet();
        for(T key: keys){
            result += key.toString() + ":" + dictionary.get(key).toString()+ "\n";
        }
        return result;
    }
}
