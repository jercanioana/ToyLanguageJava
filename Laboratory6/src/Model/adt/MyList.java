package Model.adt;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyList<T> implements MyIList<T> {
    private Queue<T> list;

    public MyList(){
        list = new LinkedList<T>();
    }
    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T pop() {
        return list.poll();
    }

    @Override
    public Queue<T> getList() {
        return list;
    }

    public String toString(){
        String result = "";
        for(T element: list){
            result += element.toString() + "\n";
        }
        return result;
    }
}
