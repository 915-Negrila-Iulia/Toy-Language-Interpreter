package app.Model.ToyExpression;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotBoolException;
import app.Model.ToyType.BoolType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.BoolValue;
import app.Model.ToyValue.Value;

public class NotExpression implements Expression{

    /*
        NotExpression class implements abstract methods of Expression interface
     */

    private final Expression expr;

    public NotExpression(Expression expr){
        /*
            Parametrised constructor which creates a NotExpression object
            :param expr: expression to be negated (Expression type)
         */

        this.expr = expr;
    }

    @Override
    public Value eval(IDictionary<String,Value> table, IHeap<Value> heap) throws Exception{
        /*
            Gets the value of the expression
            If the value obtained is not boolean a custom Expression is thrown
            :param table: reference to the SymbolTable (IDictionary)
            :param heap: reference to the HeapTable (IHeap)
            :return: negated value of an expression (Value type)
         */

        Value value = expr.eval(table,heap);
        if (value.getType().equals(new BoolType())) {
            BoolValue value_bool = (BoolValue) value;
            return new BoolValue(!value_bool.getValue());
        }
        else
            throw new NotBoolException(expr.toString()+" is not bool");
    };

    @Override
    public Type typecheck(MyDictionary<String,Type> typeEnv) throws Exception{
        /*
            Checks if expression is of type BoolType
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: new instance of BoolType
         */

        Type type1;
        type1 = expr.typecheck(typeEnv);

        if(type1.equals(new BoolType()))
            return new BoolType();
        else
            throw new NotBoolException(expr.toString()+" is not boolean");
    };

    @Override
    public Expression deepCopy(){
        return new NotExpression(expr.deepCopy());
    };

    @Override
    public String toString(){
        return "!("+expr.toString()+")";
    }
}
