package app.Model.ToyExpression;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.ToyType.Type;
import app.Model.ToyValue.Value;

public class ValueExpression implements Expression{

    /*
        ValueExpression class implements abstract methods of Expression interface
     */

    private final Value value;

    public ValueExpression(Value value){
        /*
            Parametrised constructor which creates a ValueExpression object
            :param value: value of the expression (Value type)
         */

        this.value = value;
    }

    @Override
    public Expression deepCopy(){
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public String toString(){
        return value.toString();
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Value> heap) {
        /*
            :param table: reference to the SymbolTable (IDictionary)
            :param heap: reference to the HeapTable (IHeap)
            :return: value of the expression (Value type)
         */

        return value;
    };

    @Override
    public Type typecheck(MyDictionary<String,Type> typeEnv) throws Exception{
        /*
            Gets the type of the value
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: type of value
         */

        return value.getType();
    };

}
