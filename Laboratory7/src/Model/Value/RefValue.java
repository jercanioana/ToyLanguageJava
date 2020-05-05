package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value {

    private int address;
    private Type locationType;

    public RefValue(int a, Type lt){
        address = a;
        locationType = lt;
    }

    public int getAddress(){ return this.address;}
    private Type GetLocationType(){ return this.locationType;}
    public String toString(){
        return "" + address + " " + locationType.toString();
    }


    @Override
    public boolean equals(Object another){
        if(another instanceof RefValue)
            return true;
        return false;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address,locationType);
    }
}
