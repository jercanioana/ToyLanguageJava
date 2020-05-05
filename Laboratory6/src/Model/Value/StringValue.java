package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value {
    private String val;
    public StringValue(String s) {
        val = s;
    }

    public String getVal(){
        return val;
    }

    public String toString(){
        return val;
    }

    @Override
    public boolean equals(Object another){
        if(another instanceof StringValue)
            return true;
        return false;
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }
}
