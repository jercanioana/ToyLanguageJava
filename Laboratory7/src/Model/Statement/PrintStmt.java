package Model.Statement;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import Model.adt.MyIList;

import Model.exp.*;
import exception.MyException;

public class PrintStmt implements IStmt {
    private Exp exp;
    public PrintStmt(Exp e){
        this.exp = e;
    }

    public String toString(){
        return "print("+exp.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIList<Value> output = state.getOut();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        output.add(val);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
