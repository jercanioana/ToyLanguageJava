package Model.exp;

import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import exception.MyException;

public class LogicExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op;//1-and, 2-or
    public LogicExp(Exp exp1, Exp exp2, int operator){
        this.e1 = exp1;
        this.e2 = exp2;
        this.op = operator;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl,hp);
        if(v1.getType().equals(new BoolType())){
            v2 = eval(tbl,hp);
            if(v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(op == 1)
                    return new BoolValue(n1&n2);
                if(op == 2)
                    return new BoolValue(n1|n2);

            }
                throw new MyException("second operand is not a boolean");
        }
            throw new MyException("first operand is not a boolean");

    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())){
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            }
            else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    public String toString(){
        return "(" + e1.toString() + " " + op + " " + e2.toString() + ")";
    }
}
