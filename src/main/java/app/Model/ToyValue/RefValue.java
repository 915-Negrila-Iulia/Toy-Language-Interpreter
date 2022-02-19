package app.Model.ToyValue;

import app.Model.ToyType.RefType;
import app.Model.ToyType.Type;

public class RefValue implements Value{

    /*
        RefValue class implements abstract methods of Value interface
     */

    private final int address;
    private final Type locationType;

    public RefValue(int address, Type locationType){
        /*
            Parametrised constructor which creates a RefValue object
            :param address: heap address (int type)
            :param locationType: Type of the location having the 'address' address ('Type' type)
         */
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress(){
        return this.address;
    }
    public Type getLocationType(){
        return this.locationType;
    }

    @Override
    public Type getType(){
        return new RefType(locationType);
    };

    @Override
    public String toString(){
        return "("+address+","+locationType.toString()+")";
    }

    @Override
    public Value deepCopy(){
        return new RefValue(address,locationType);
    };
}
