package Model.exp;

import Model.Type.Type;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import exception.MyException;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyException;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;

}
