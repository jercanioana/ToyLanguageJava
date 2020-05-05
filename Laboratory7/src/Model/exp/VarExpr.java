package Model.exp;

import Model.Type.Type;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import exception.MyException;

public class VarExpr implements Exp {
    String id;
    public VarExpr(String i){
        this.id = i;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl,  MyIHeap<Integer,Value> hp) throws MyException {
        return tbl.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    public String toString(){
        return id;
    }
}
