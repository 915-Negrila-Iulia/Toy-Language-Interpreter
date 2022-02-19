package app.Model.ToyValue;

import app.Model.ToyType.StringType;
import app.Model.ToyType.Type;

public class StringValue implements Value{

    /*
        StringValue class implements abstract methods of Value interface
     */

    private final String val;

    public StringValue(String v){
        /*
            Parametrised constructor which creates a StringValue object
            :param v: value of the StringValue object (string type)
         */
        this.val = v;
    }

    public String getValue(){
        return this.val;
    }

    @Override
    public Value deepCopy(){
        return new StringValue(val);
    }

    @Override
    public String toString(){
        return val;
    }

    @Override
    public Type getType(){
        /*
        Create new instance of type StringType and return it
         */
        return new StringType();
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof StringValue){
            StringValue objectValue = (StringValue) object;
            if(objectValue.getValue().equals(val))
                return true;
            else
                return false;
        }else{
            return false;
        }
    }
}
