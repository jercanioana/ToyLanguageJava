package Model.Statement;

import Model.PrgState;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import Model.exp.Exp;
import exception.MyException;

public class wH implements IStmt {

    private String var_name;
    private Exp exp;
    public wH(String v, Exp e){
        var_name = v;
        exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if(symTbl.isDefined(var_name)) {
            if (symTbl.getVal(var_name).getType() instanceof RefType) {
                RefValue value = (RefValue) symTbl.getVal(var_name);
                int address = value.getAddress();
                if(heap.isDefined(address)){
                    Value v = exp.eval(symTbl, heap);
                    RefType t = (RefType) symTbl.getVal(var_name).getType();
                    if(v.getType().equals(t.getInner())){
                        heap.update(address,v);
                    }else throw new MyException("Incompatible types.");
                }else throw new MyException("Key not defined in heap.");
            }else throw new MyException("Not ref type.");
        }else throw new MyException("Not a valid name.");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new wH(var_name,exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        Type typeVal = typeEnv.lookup(var_name);

        if(typeVal instanceof RefType){
            RefType t = (RefType) typeVal;
            if(typeExp.equals(t.getInner())){
                return typeEnv;
            }else
                throw new MyException("Variable not of type int.");
        }
        else
            throw new MyException("Variable not of ref type.");
    }

    public String toString(){
        return "wH(" + var_name + "," + exp.toString() + ")";
    }
}
