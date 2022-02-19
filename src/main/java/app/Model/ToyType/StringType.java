package app.Model.ToyType;

import app.Model.ToyValue.StringValue;
import app.Model.ToyValue.Value;

public class StringType implements Type{

    /*
        StringType class implements abstract methods of Type interface
     */

    @Override
    public boolean equals(Object object){
        /*
        Check if a given object is instance of class StringType
         */
        if(object instanceof StringType)
            return true;
        else
            return false;
    };

    @Override
    public Value defaultValue(){
        return new StringValue("");
    };

    @Override
    public Type deepCopy(){
        return new StringType();
    }

    @Override
    public String toString(){
        return "string";
    };
}
