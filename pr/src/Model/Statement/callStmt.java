package Model.Statement;

import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;
import Model.adt.*;
import Model.exp.Exp;
import exception.MyException;
import javafx.util.Pair;

import java.util.List;

public class callStmt implements IStmt{

    private String name;
    private List<Exp> expr;

    public callStmt(String n, List<Exp> e){
        name = n;
        expr = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIStack<IStmt> exestack = state.getStk();
        IProcTable<String, Pair<List<String>, IStmt>> procTable = state.getProcTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIDictionary<String, Value> newsymtbl = new MyDictionary<String,Value>();
        MyIStack<MyIDictionary<String, Value>> symTables = state.getSymTables();
        if(procTable.isDefined(name)){
            List<String> variables = procTable.getVal(name).getKey();
            IStmt statement = procTable.getVal(name).getValue();
            int i = 0;
            for(Exp exp:expr){
                Value val;
                val = exp.eval(symtbl, heap);
                newsymtbl.add(variables.get(i), val);
                i++;
            }
            symTables.push(newsymtbl);
            IStmt newstmt = new returnStmt();
            exestack.push(newstmt);
            exestack.push(statement);


        }else
            throw new MyException("No such function.");
        return null;
    }

    public String toString(){
        return "call " + name + "(" + expr.toString() + ")";
    }
    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
