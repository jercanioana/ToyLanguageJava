package Model.Statement;

import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import Model.exp.Exp;
import exception.MyException;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt {
    private Exp exp;
    private String var_name;
    public readFile(Exp e, String v){
        this.exp = e;
        this.var_name = v;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<StringValue, BufferedReader> FileTable = state.getFileTable();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if(symTbl.isDefined(var_name) && symTbl.getVal(var_name).getType().equals(new IntType())){
            Value v = exp.eval(symTbl,heap);
            if(v.getType().equals(new StringType())){
                BufferedReader bufferedReader = FileTable.getVal((StringValue) v);
                if(bufferedReader != null) {
                    IntValue valueFromFile;
                    try {
                        String result = bufferedReader.readLine();
                        if (result == null) {
                            valueFromFile = new IntValue(0);
                        }else{
                            valueFromFile = new IntValue(Integer.parseInt(result));
                        }
                        symTbl.update(var_name, valueFromFile);
                    }catch(IOException e){
                        throw new MyException("Couldn't read from file.");
                    }

                }else throw new MyException("No buffer associated.");

            }else throw new MyException("Value not of string type.");
        }else throw new MyException("Variable not of type int.");
    return null;
    }

    @Override
    public IStmt deepCopy() {
        return new readFile(exp,var_name);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if(typeExp.equals(new StringType())){
            Type typeVal = typeEnv.lookup(var_name);
            if(typeVal.equals(new IntType())){
                return typeEnv;
            }else
                throw new MyException("Variable not of type int.");
        }
        else
            throw new MyException("Not string type.");
    }

    public String toString(){
        return "ReadFile(" + exp.toString() + " " + var_name + ")";
    }

}
