package app.Model.ToyType;

import app.Model.ToyValue.RefValue;
import app.Model.ToyValue.Value;

public class RefType implements Type{

    /*
        RefType class implements abstract methods of Type interface
     */

    private final Type inner;

    public RefType(Type inner){
        /*
            Parametrised constructor which creates a RefType object
            :param inner: Type object which is referenced by the current object
         */
        this.inner = inner;
    }

    public Type getInner(){
        return this.inner;
    }

    @Override
    public Value defaultValue(){
        return new RefValue(0,inner);
    };

    @Override
    public boolean equals(Object object){
        if(object instanceof RefType)
            return inner.equals(((RefType) object).getInner());
        else
            return false;
    };

    @Override
    public String toString(){
        return "Ref("+inner.toString()+")";
    };

    @Override
    public Type deepCopy(){
        return new RefType(inner);
    };
}
