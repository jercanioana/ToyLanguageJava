package Model.Statement;

import Model.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import Model.exp.Exp;
import exception.MyException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class closeRFile implements IStmt{
    private Exp exp;
    public closeRFile(Exp e){
        this.exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<StringValue, BufferedReader> FileTable = state.getFileTable();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value v = exp.eval(symTbl, heap);
        if(v.getType().equals(new StringType())){
            StringValue s = (StringValue) v;
            BufferedReader bufferedReader = FileTable.getVal(s);
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                    FileTable.remove(s);
                }catch(IOException e){
                    throw new MyException("Couldn't close buffer.");
                }
            }else throw new MyException("There is no entry associated to the buffer.");

        }else throw new MyException("Value not a string.");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new closeRFile(exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if(typeExp.equals(new StringType())){
            return typeEnv;
        }
        else
            throw new MyException("Not string type.");
    }

    public String toString(){
        return "CloseFile(" + exp.toString() + ")";
    }
}
