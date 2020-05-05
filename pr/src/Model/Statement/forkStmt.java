package Model.Statement;

import Model.PrgState;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.adt.*;
import exception.MyException;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;
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
        IProcTable<String, Pair<List<String>, IStmt>> procTable = state.getProcTable();
        MyIList<Value> out = state.getOut();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIStack<IStmt> newstk = new MyStack<IStmt>();
        MyIDictionary<String, Value> newSymTbl = new MyDictionary<>();
        MyIStack<MyIDictionary<String, Value>> newsymtables = new MyStack<>();
        MyIStack<MyIDictionary<String, Value>> aux = new MyStack<>();
        MyIStack<MyIDictionary<String, Value>> symtables = state.getSymTables();
//        for(Map.Entry<String, Value> entry: symTbl.getContent().entrySet()){
//            newSymTbl.update(entry.getKey(), entry.getValue().deepCopy());
//        }
        while(!symtables.isEmpty()){
            MyIDictionary<String, Value> symtbl = symtables.pop();
            aux.push(symTbl.clone());
        }
        while(!aux.isEmpty()){
            MyIDictionary<String, Value> symtbl = aux.pop();
            symtables.push(symTbl);
            newsymtables.push(symTbl);

        }
        PrgState prg = new PrgState(newstk, newsymtables, out, fileTbl, heap,procTable, stmt);
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
