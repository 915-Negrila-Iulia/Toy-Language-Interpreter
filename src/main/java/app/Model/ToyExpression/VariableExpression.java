package app.Model.ToyExpression;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ToyType.Type;
import app.Model.ToyValue.Value;

public class VariableExpression implements Expression{

    /*
        VariableExpression class implements abstract methods of Expression interface
     */

    private final String id;

    public VariableExpression(String id){
        /*
            Parametrised constructor which creates a VariableExpression object
            :param id: name of the variable (String type)
         */

        this.id = id;
    }

    @Override
    public Expression deepCopy(){
        return new VariableExpression(id);
    }

    @Override
    public String toString(){
        return id;
    }

    @Override
    public Value eval(IDictionary<String,Value> table, IHeap<Value> heap) throws VariableNotDefinedException {
        /*
            Searches for the value of a variable in the SymbolTable by its name
            If variable is not defined a custom Expression is thrown
            :param table: reference to the SymbolTable (IDictionary)
            :param heap: reference to the HeapTable (IHeap)
            :return: value of a variable expression (Value type)
         */

        try {
            return table.lookUp(id);
        }catch (Exception e){
            throw new VariableNotDefinedException(e.getMessage(),e);
        }
    };

    @Override
    public Type typecheck(MyDictionary<String,Type> typeEnv) throws Exception{
        /*
            Searches for the type of the variable in the TypeEnvironment
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: Type of the variable
         */

        return typeEnv.lookUp(id);
    };

}
