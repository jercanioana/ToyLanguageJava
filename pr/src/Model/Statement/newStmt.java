package Model.Statement;

import Model.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import Model.exp.Exp;
import exception.MyException;

public class newStmt implements IStmt {
    private String var_name;
    private Exp exp;
    public newStmt(String v, Exp e){
        var_name = v;
        exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if(symTbl.isDefined(var_name)){
            if(symTbl.getVal(var_name).getType() instanceof RefType){
                RefValue val= (RefValue) symTbl.getVal(var_name);
                RefType type = (RefType) val.getType();
                Value v = exp.eval(symTbl, heap);
                if(v.getType().equals(type.getInner())){
                    int location = heap.getFreeLocation();
                    heap.add(location, v);
                    RefValue value = new RefValue(location, type.getInner());
                    symTbl.update(var_name, value);
                }else throw new MyException("Types not equal.");

            }else throw new MyException("Type not RefType");
        } else throw new MyException("Variable already defined");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new newStmt(var_name,exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    public String toString(){
        return "new(" + var_name + "," + exp.toString() + ")";
    }
}
