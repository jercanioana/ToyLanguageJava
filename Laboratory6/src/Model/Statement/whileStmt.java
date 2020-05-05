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


public class whileStmt implements IStmt {
    private Exp exp;
    private IStmt stmt;

    public whileStmt(Exp e, IStmt s){
        this.exp = e;
        this.stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        if(val.getType().equals(new BoolType())) {
            BoolValue b = (BoolValue) val;
            if (b.getVal() == true){
                stk.push(this);
                stk.push(stmt);

            }
        }else throw new MyException("condition exp is not a boolean");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new whileStmt(exp,stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if(typeExp.equals(new BoolType())){
            stmt.typecheck(typeEnv.clone());
            return typeEnv;
        }else
            throw new MyException("The condition of WHILE has not the type bool");
    }

    @Override
    public String toString() {
        return "(while (" + exp.toString() + " " + stmt.toString() + ")";
    }
}
