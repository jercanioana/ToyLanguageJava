package Model.adt;

import Model.Value.Value;
import exception.MyException;

import java.util.*;

public class MyDictionary<T, T1> implements MyIDictionary<T,T1> {
    private HashMap<T, T1> dictionary;
    public MyDictionary(){
        dictionary = new HashMap<T, T1>();
    }
    @Override
    public void add(T k, T1 v){
        dictionary.put(k, v);
    }
    @Override
    public void update(T k, T1 v){
        dictionary.put(k, v);
    }

    @Override
    public void remove(T id) {
        dictionary.remove(id);
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
    public MyIDictionary<T, T1> clone() {
        MyIDictionary<T, T1> clone = new MyDictionary<T, T1>();
        for (T key:dictionary.keySet()){
            try{
                clone.add(key, dictionary.get(key));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return clone;
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
