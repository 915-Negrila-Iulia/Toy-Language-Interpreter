package app.Model.ToyType;

import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.Value;

public class IntType implements Type{

    /*
        IntType class implements abstract methods of Type interface
     */

    @Override
    public boolean equals(Object object){
        /*
        Check if a given object is instance of class IntType
         */
        if(object instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public Value defaultValue(){
        return new IntValue(0);
    }

    @Override
    public Type deepCopy(){
        return new IntType();
    }

    @Override
    public String toString(){
        return "int";
    }
}
