package Model.adt;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack (){
        stack = new Stack<T>();
    }
    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Stack<?> clone() {
        return (Stack<?>) this.stack.clone();
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }

    public String toString(){
        String result = " ";
        for(T element: stack){
            result = result + element.toString()+ "\n";
        }
        return result;
    }
}
