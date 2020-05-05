package Model.exp;

import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Model.adt.MyIDictionary;
import Model.adt.MyIHeap;
import exception.MyException;

public class RelationalExpr implements Exp {
    private Exp e1;
    private Exp e2;
    private String op;
    public RelationalExpr(Exp exp1, Exp exp2, String oper){
        e1 = exp1;
        e2 = exp2;
        op = oper;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(tbl, hp);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(op.equals("<"))
                    return new BoolValue(n1 < n2);
                if(op.equals("<="))
                    return new BoolValue(n1 <= n2);
                if(op.equals("=="))
                    return new BoolValue(n1 == n2);
                if(op.equals("!="))
                    return new BoolValue(n1 != n2);
                if(op.equals(">"))
                    return new BoolValue(n1 > n2);
                if(op.equals(">="))
                    return new BoolValue(n1 >= n2);
            }
                throw new MyException("second operand is not an integer");
        }
            throw new MyException("first operand is not an integer");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())){
            if (typ2.equals(new IntType())) {
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
