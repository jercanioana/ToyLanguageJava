package Model.Statement;
import Model.PrgState;
import Model.Type.Type;
import Model.adt.MyIDictionary;
import exception.MyException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
    MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
