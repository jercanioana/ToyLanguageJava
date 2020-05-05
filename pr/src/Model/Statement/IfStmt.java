package Model.Statement;

import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import Model.adt.MyIStack;
import Model.exp.Exp;
import exception.MyException;

public class IfStmt implements IStmt{
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el){
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        if(val.getType().equals(new BoolType())) {
            BoolValue b = (BoolValue) val;
            if (b.getVal() == true)
                stk.push(thenS);
            else
                stk.push(elseS);
        } else throw new MyException("Invalid");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp, thenS, elseS);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");

    }

    public String toString(){
        return "IF("+exp.toString()+") THEN("+thenS.toString()+
                ")ELSE("+elseS.toString()+")";
    }
}
