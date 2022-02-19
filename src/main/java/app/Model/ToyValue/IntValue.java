package app.Model.ToyValue;

import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;

public class IntValue implements Value{

    /*
        IntValue class implements abstract methods of Value interface
     */

    private final int val;

    public IntValue(int v){
        /*
            Parametrised constructor which creates an IntValue object
            :param v: value of the IntValue object (int type)
         */
        this.val = v;
    }

    public int getValue(){
        return this.val;
    }

    @Override
    public Value deepCopy(){
        return new IntValue(val);
    }

    @Override
    public String toString(){
        return ""+val;
    }

    @Override
    public Type getType(){
        /*
        Create new instance of type IntType and return it
         */
        return new IntType();
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof IntValue){
            IntValue objectValue = (IntValue) object;
            if(objectValue.getValue() == val)
                return true;
            else
                return false;
        }else{
            return false;
        }
    }
}
