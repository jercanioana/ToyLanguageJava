package Model.Statement;

import Model.PrgState;
import Model.Type.Type;
import Model.adt.MyIDictionary;
import Model.adt.MyIStack;
import exception.MyException;

public class sleepStmt implements IStmt {
    private int number;
    public sleepStmt(int n){
        number = n;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        if(number != 0){
            int newNumber = number-1;
            IStmt newStmt = new sleepStmt(newNumber);
            stk.push(newStmt);
        }
        return null;
    }

    public String toString(){
        return "sleep(" + (Integer.toString(number)) + ")";
    }
    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
