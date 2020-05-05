package Model.Statement;

import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIStack;
import exception.MyException;

public class returnStmt implements IStmt {

    public returnStmt(){

    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<MyIDictionary<String, Value>> symTables = state.getSymTables();
        symTables.pop();
        return null;
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
