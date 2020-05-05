package Model.Statement;

import Model.PrgState;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.adt.*;
import exception.MyException;
import java.io.BufferedReader;
import java.util.Map;

public class forkStmt implements IStmt {
    private IStmt stmt;
    public forkStmt(IStmt s){
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        MyIList<Value> out = state.getOut();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIStack<IStmt> newstk = new MyStack<IStmt>();
        MyIDictionary<String, Value> newSymTbl = new MyDictionary<>();

        for(Map.Entry<String, Value> entry: symTbl.getContent().entrySet()){
            newSymTbl.update(entry.getKey(), entry.getValue().deepCopy());
        }

        PrgState prg = new PrgState(newstk, newSymTbl, out, fileTbl, heap, stmt);
        return prg;
    }

    @Override
    public IStmt deepCopy() {
        return new forkStmt(stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString(){
        return "fork( " + stmt.toString() + " )";
    }
}
