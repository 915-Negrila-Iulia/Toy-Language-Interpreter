package app.Model.ToyValue;

import app.Model.ToyType.BoolType;
import app.Model.ToyType.Type;

public class BoolValue implements Value{

    /*
        BoolValue class implements abstract methods of Value interface
     */

    private final boolean val;

    public BoolValue(boolean b){
        /*
            Parametrised constructor which creates a BoolValue object
            :param b: value of the BoolValue object (boolean type)
         */
        this.val = b;
    }

    public boolean getValue(){
        return this.val;
    }

    @Override
    public Value deepCopy(){
        return new BoolValue(val);
    }

    @Override
    public String toString(){
        return ""+val;
    }

    @Override
    public Type getType(){
        /*
        Create new instance of type BoolType and return it
         */
        return new BoolType();
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof BoolValue){
            BoolValue objectValue = (BoolValue) object;
            if(objectValue.getValue() == val)
                return true;
            else
                return false;
        }else{
            return false;
        }
    }
}
