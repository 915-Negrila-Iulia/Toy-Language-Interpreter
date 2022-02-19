package app.Model.ToyType;

import app.Model.ToyValue.BoolValue;
import app.Model.ToyValue.Value;

public class BoolType implements Type{

    /*
        BoolType class implements abstract methods of Type interface
     */

    @Override
    public boolean equals(Object object){
        /*
        Check if a given object is instance of class BoolType
         */
        if(object instanceof BoolType)
            return true;
        else
            return false;
    }

    @Override
    public Value defaultValue(){
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy(){
        return new BoolType();
    }

    @Override
    public String toString(){
        return "bool";
    }
}
