package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

import java.io.ObjectStreamException;

public class BoolValue implements Value {
    private boolean val;
    public BoolValue(boolean v){
        this.val = v;
    }
    public boolean getVal(){
        return this.val;
    }
    public String toString(){
        return "(" + val + ")";
    }

    @Override
    public boolean equals(Object another){
        if(another instanceof BoolValue)
            return true;
        return false;
    }


    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy(){
        return new BoolValue(val);
    }
}
