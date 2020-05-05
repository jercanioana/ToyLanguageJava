package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value {
    private int val;
    public IntValue(int v){
        this.val = v;
    }
    public int getVal(){
        return val;
    }
    public String toString(){
        return "" + val + "";
    }


    @Override
    public boolean equals(Object another){
        if(another instanceof IntValue)
            return true;
        return false;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }
}
