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

import java.io.FileNotFoundException;
import java.io.FileReader;

public class openRFile implements IStmt {
    private Exp file_name;
    public openRFile(Exp e1){
        file_name = e1;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<StringValue, BufferedReader> FileTable = state.getFileTable();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value v = file_name.eval(symTbl, heap);
        if(v.getType().equals(new StringType())){
            StringValue s = (StringValue) v;
            if(!FileTable.isDefined(s)){
                try {
                    BufferedReader buffer = new BufferedReader(new FileReader(s.getVal()));
                    FileTable.add(s, buffer);

                }catch(FileNotFoundException e){
                    throw new MyException("Couldn't create buffer.");
                }
            }else throw new MyException("Key already in the table.");
        }else throw new MyException("Expression is not a string");
    return null;
    }

    @Override
    public IStmt deepCopy() {
        return new openRFile(file_name);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = file_name.typecheck(typeEnv);
        if(typeExp.equals(new StringType())){
            return typeEnv;
        }
        else
            throw new MyException("Not string type.");
    }

    public String toString(){
        return "Openfile(" + file_name.toString() + ")";
    }
}
