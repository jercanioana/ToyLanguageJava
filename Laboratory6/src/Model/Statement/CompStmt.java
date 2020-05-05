package Model.Statement;
import Model.Type.Type;
import Model.adt.*;
import Model.PrgState;
import exception.MyException;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt snd;
    public CompStmt(IStmt first, IStmt snd){
        this.first = first;
        this.snd = snd;
    }
    public String toString(){
        return  "(" + first.toString() + ";" + snd.toString() + ")";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(),snd.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        // MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        // return typEnv2;
        return snd.typecheck(first.typecheck(typeEnv));
    }
}
