package Model.exp;

import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import exception.MyException;

public class rH implements Exp {
    private Exp exp;
    public rH(Exp e){
        this.exp = e;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl,  MyIHeap<Integer,Value> hp) throws MyException {
        Value v = exp.eval(tbl,hp);
        if(v.getType() instanceof RefType){
            int a = ((RefValue) v).getAddress();
            Value associetedValue = hp.getVal(a);
            if(associetedValue == null)
                throw new MyException("No such key.");
            else
                return associetedValue;
        }throw new MyException("Not ref type");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    public String toString(){
        return "rH(" + exp + ")";
    }
}
