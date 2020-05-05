package Model.Statement;

import Model.PrgState;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import exception.MyException;

public class VarDeclStmt implements IStmt {
    private String name;
    private Type typ;
    public VarDeclStmt(String n, Type t)
    {
        this.name = n;
        this.typ = t;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (!symTbl.isDefined(name)) {
            if (typ instanceof IntType)
                symTbl.add(name, typ.defaultValue());
            else if (typ instanceof BoolType)
                symTbl.add(name, typ.defaultValue());
            else if (typ instanceof StringType)
                symTbl.add(name, typ.defaultValue());
            else if (typ instanceof RefType)
                symTbl.add(name, typ.defaultValue());


        } else throw new MyException("Variable was declared before");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, typ);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name,typ);
        return typeEnv;
    }

    public String toString(){
        return typ.toString() + " " + name;
    }
}
