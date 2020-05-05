package Model.Statement;

import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import Model.adt.MyIStack;
import Model.exp.Exp;
import exception.MyException;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;
    public AssignStmt(String id, Exp e){
        this.id = id;
        this.exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        if (symTbl.isDefined(id)) {
            Type typId = (symTbl.getVal(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else throw new MyException("declared type of variable" + id + " and type of" +
                    "the assigned expression do not match");
        } else throw new MyException("the used variable" + id + "was not declared before");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    public String toString(){
        return "(" + id + "=" + exp.toString() + ")";
    }

    }

