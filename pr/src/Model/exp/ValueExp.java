package Model.exp;

import Model.Type.Type;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import exception.MyException;

public class ValueExp implements Exp {

    private Value e;
    public ValueExp(Value v){
        this.e = v;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl,  MyIHeap<Integer,Value> hp) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    public String toString(){
        return "( " + e.toString() + " )";
    }

}
